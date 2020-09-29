package com.khelacademy.daoImpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khelacademy.dao.MatchDao;
import com.khelacademy.dto.MatchDto;
import com.khelacademy.model.Challenge;
import com.khelacademy.model.Match;
import com.khelacademy.www.services.CalculationConst;
import com.khelacademy.www.services.ChallengeStatus;
import com.khelacademy.www.services.MatchStatus;

@Component
@Transactional
public class MatchDaoImpl implements MatchDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Match startMatch(MatchDto matchDto) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Challenge E WHERE E.id =:id";
		@SuppressWarnings("unchecked")
		Query<Challenge> query = session.createQuery(hql);
		query.setInteger("id", matchDto.getChallengeId());

		List<Challenge> results = query.list();
		if (results.size() == 1) {
			if (results.get(0).getMatchId() == null) {
				Match m = new Match();
				long timeNow = Calendar.getInstance().getTimeInMillis();
				m.setMatchStartTime(new Timestamp(timeNow));
				m.setStatus(MatchStatus.STARTED.toString());
				m.setTeamId1(results.get(0).getTeamId1());
				m.setTeamId2(results.get(0).getTeamId2());
				m.setScore1(0);
				m.setScore2(0);
				m.setUpdatedBy(matchDto.getUpdatedByUserName());
				session.save(m);
				results.get(0).setMatchId(m.getId());
				results.get(0).setStatus(ChallengeStatus.CHALLENGE_INPROGESS.toString());
				session.update(results.get(0));
				return m;
			} else {
				hql = "FROM Match E WHERE E.id =:id";
				@SuppressWarnings("unchecked")
				Query<Match> query1 = session.createQuery(hql);
				query1.setInteger("id", results.get(0).getMatchId());

				List<Match> results1 = query1.list();
				if (results1.size() == 1) {
					return results1.get(0);
				} else {
					throw new Exception("Internal Devlopment error, please report !!", new Exception());
				}
			}
		} else {
			throw new Exception("Internal Devlopment error, please report !!", new Exception());
		}

//		Session session = this.sessionFactory.getCurrentSession();
//		String hql = "FROM Challenge E WHERE (E.teamId1 =:t1 OR E.teamId1 =:t12)  AND (E.teamId2=:t2 OR E.teamId1 =:t22)  AND E.status =:s1 )";
//		@SuppressWarnings("unchecked")
//		Query<Challenge> query = session.createQuery(hql);
//		query.setInteger("t1", matchDto.getMatchTId1());
//		query.setInteger("t12", matchDto.getMatchTId2());
//		query.setInteger("t2", matchDto.getMatchTId2());
//		query.setInteger("t22", matchDto.getMatchTId1());
//		query.setString("s1", ChallengeStatus.CHALLENGE_ACCEPTED.toString());
//		List<Challenge> results = query.list();
//		if(results.size() == 1) {
//			Match
//		}
//			return results.get(0);
//		else if (results.size() > 1)
//			throw new Exception("Internal Devlopment error, please report !!", new Exception());
//		else {
//			return null;
//		}

	}

	@Override
	public Match updateScore(MatchDto matchDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String hql = "FROM Match E WHERE E.id =:id";
		@SuppressWarnings("unchecked")
		Query<Match> query1 = session.createQuery(hql);
		query1.setInteger("id", matchDto.getMatchId());

		List<Match> results1 = query1.list();
		if (results1.size() == 1) {
			if(matchDto.getMatchTId1() != null && matchDto.getScoreT1() != null && results1.get(0).getTeamId1().intValue() == matchDto.getMatchTId1().intValue()) {
				results1.get(0).setScore1(results1.get(0).getScore1() == null ? matchDto.getScoreT1() : results1.get(0).getScore1()+matchDto.getScoreT1());
			}else if(matchDto.getMatchTId2() != null && matchDto.getScoreT2() != null && results1.get(0).getTeamId2().intValue() == matchDto.getMatchTId2().intValue()) {
				results1.get(0).setScore2(results1.get(0).getScore2() == null ? matchDto.getScoreT2() : results1.get(0).getScore2()+matchDto.getScoreT2());
			}else {
				throw new Exception("Internal Devlopment error, please report !!", new Exception());
			}
			session.update(results1.get(0));
			return results1.get(0);
		} else {
			throw new Exception("Internal Devlopment error, please report !!", new Exception());
		}
	}

	@Override
	public Match concludeMatch(MatchDto matchDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String hql = "FROM Match E WHERE E.id =:id";
		@SuppressWarnings("unchecked")
		Query<Match> query1 = session.createQuery(hql);
		query1.setInteger("id", matchDto.getMatchId());
		List<Match> results1 = query1.list();
		if(results1.get(0).getScore1() != results1.get(0).getScore2() && (results1.get(0).getScore1() > 10 || results1.get(0).getScore2() > 10)) {
			long timeNow = Calendar.getInstance().getTimeInMillis();
			results1.get(0).setMatchEndTime(new Timestamp(timeNow));
			if(results1.get(0).getScore1() > results1.get(0).getScore2()) {
				results1.get(0).setStatus(MatchStatus.TEAM_1_WON.toString());
			}else {
				results1.get(0).setStatus(MatchStatus.TEAM_2_WON.toString());
			}
			session.update(results1.get(0));
			calculateScores(results1.get(0));
			return results1.get(0);
		}else {
			throw new Exception("Internal Devlopment error, please report !!", new Exception());
		}
	}

	private void calculateScores(Match match) {
		Double teamOneAmortizedScore = 0.0;
		Double teamTwoAmortizedScore = 0.0;
		Integer pointDiff = Math.abs(match.getScore1() - match.getScore2());
		Double timeDiff = (double) (Math.abs(match.getMatchStartTime().getSeconds() - match.getMatchEndTime().getSeconds())/5);
		if(match.getStatus().equalsIgnoreCase(MatchStatus.TEAM_1_WON.toString())) {
			teamOneAmortizedScore = amortizedScore(CalculationConst.WON_POINT + pointDiff + timeDiff);
			teamTwoAmortizedScore = amortizedScore(CalculationConst.LOST_POINT - pointDiff + timeDiff);
		}else {
			teamTwoAmortizedScore = amortizedScore(CalculationConst.WON_POINT + pointDiff + timeDiff);
			teamOneAmortizedScore = amortizedScore(CalculationConst.LOST_POINT - pointDiff + timeDiff);
		}
		// TODO Auto-generated method stub
		
	}
	private static double amortizedScore(double logNumber) {
	    return Math.log(logNumber) / Math.log(CalculationConst.LOG_BASE);
	}

}
