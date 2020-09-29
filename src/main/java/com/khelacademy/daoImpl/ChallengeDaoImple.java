package com.khelacademy.daoImpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khelacademy.dao.ChallengeDao;
import com.khelacademy.dao.TeamDao;
import com.khelacademy.dao.UserDao;
import com.khelacademy.dto.ChallengeDto;
import com.khelacademy.dto.TeamDto;
import com.khelacademy.model.Challenge;
import com.khelacademy.model.Team;
import com.khelacademy.www.services.ChallengeStatus;
import com.khelacademy.www.services.TeamStatus;

@Component
@Transactional
public class ChallengeDaoImple implements ChallengeDao {
	@Autowired
	TeamDao teamDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void sendChallenge(ChallengeDto challengeDto) throws Exception {
		if(challengeDto.getChallegerTeamId() == null && challengeDto.getChallengeeTeamId()==null)
			soloChallenge(challengeDto);
		else
			teamChallege(challengeDto);
	}

	private void teamChallege(ChallengeDto challengeDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		checkTeamIdBelongsToTheCurrentUser(challengeDto);
		Challenge challengeFound = checkChallengeFeasibility(challengeDto.getChallegerTeamId(), challengeDto.getChallengeeTeamId());
		if(challengeFound != null) {
			if(challengeFound.getStatus().equals(ChallengeStatus.CHALLENGED.toString()))
				throw new Exception("Already challenged waiting response from opponent", new Exception());
			if(challengeFound.getStatus().equals(ChallengeStatus.CHALLENGE_DECLINED.toString())) {
				challengeFound.setStatus(ChallengeStatus.CHALLENGED.toString());
				session.update(challengeFound);
				return;
			}
		}
		Challenge ch = new Challenge();
		long timeNow = Calendar.getInstance().getTimeInMillis();
		ch.setCreatedAt(new Timestamp(timeNow));
		ch.setModifiedAt(new Timestamp(timeNow));
		ch.setStatus(ChallengeStatus.CHALLENGED.toString());
		ch.setTeamId1(challengeDto.getChallegerTeamId());
		ch.setTeamId2(challengeDto.getChallengeeTeamId());
		session.save(ch);
		
	}

	private void checkTeamIdBelongsToTheCurrentUser(ChallengeDto challengeDto) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.id =:id AND E.userId =:userId";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setInteger("id", challengeDto.getChallegerTeamId());
		query.setLong("userId", userDao.getUserIdByUserName(challengeDto.getChallegerUserName()));
		List<Team> results = query.list();
		if(results.size() != 1)
			throw new Exception("Internal Development problem , Please report", new Exception());
	
		
	}

	private void soloChallenge(ChallengeDto challengeDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		TeamDto teamDto = new TeamDto();
		teamDto.setUserId(userDao.getUserIdByUserName(challengeDto.getChallegerUserName()));
		teamDto.setGameId(challengeDto.getGameId());
		Team t1 = teamDao.createSoloTeam(teamDto);
		teamDto.setUserId(userDao.getUserIdByUserName(challengeDto.getChallegeeUserName()));
		Team t2 = teamDao.createSoloTeam(teamDto);
		Challenge challengeFound = checkChallengeFeasibility(t1.getId(), t2.getId());
		if(challengeFound != null) {
			if(challengeFound.getStatus().equals(ChallengeStatus.CHALLENGED.toString()))
				throw new Exception("Already challenged waiting response from opponent", new Exception());
			if(challengeFound.getStatus().equals(ChallengeStatus.CHALLENGE_DECLINED.toString())) {
				challengeFound.setStatus(ChallengeStatus.CHALLENGED.toString());
				session.update(challengeFound);
				return;
			}
		}
		Challenge ch = new Challenge();
		long timeNow = Calendar.getInstance().getTimeInMillis();
		ch.setCreatedAt(new Timestamp(timeNow));
		ch.setModifiedAt(new Timestamp(timeNow));
		ch.setStatus(ChallengeStatus.CHALLENGED.toString());
		ch.setTeamId1(t1.getId());
		ch.setTeamId2(t2.getId());
		session.save(ch);
		
	}
	private Challenge checkChallengeFeasibility(Integer challegerTeamId, Integer challengeeTeamId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Challenge E WHERE E.teamId1 =:t1 AND E.teamId2=:t2 AND (E.status =:s1 OR E.status =:s2)";
		@SuppressWarnings("unchecked")
		Query<Challenge> query = session.createQuery(hql);
		query.setInteger("t1", challegerTeamId);
		query.setInteger("t2", challengeeTeamId);
		query.setString("s1", ChallengeStatus.CHALLENGED.toString());
		query.setString("s2", ChallengeStatus.CHALLENGE_DECLINED.toString());
		List<Challenge> results = query.list();
		if(results.size() == 1)
			return results.get(0);
		else if (results.size() > 1)
			throw new Exception("Internal Devlopment error, please report !!", new Exception());
		else {
			return null;
		}

		
	}
	@Override
	public void actionOnChallenge(ChallengeDto challengeDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Challenge E WHERE E.id =:cId AND (E.status =:s1 OR E.status =:s2)";
		@SuppressWarnings("unchecked")
		Query<Challenge> query = session.createQuery(hql);
		query.setInteger("cId", challengeDto.getChallengeId());
		query.setString("s1", ChallengeStatus.CHALLENGED.toString());
		query.setString("s2", ChallengeStatus.CHALLENGE_DECLINED.toString());
		List<Challenge> results = query.list();
		if(results.size() == 1) {
			if(!EnumUtils.isValidEnum(ChallengeStatus.class, challengeDto.getStatus())) {
				 throw new Exception("Internal Development Enum problem , Please report", new Exception());
			}
			checkIfChallengeBelongsToThisUser(results.get(0), challengeDto.getChallegeeUserName());
			results.get(0).setStatus(challengeDto.getStatus());
		}else {
			throw new Exception("No challenge found", new Exception());
		}
	}

	private void checkIfChallengeBelongsToThisUser(Challenge challenge, String userName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		Query countQuery = session.createQuery(
		        "select count(*) FROM Team E inner join BasicUserDetails U on E.userId = U.id WHERE U.userName =:userName AND E.id =:teamId");
		countQuery.setInteger("teamId", challenge.getTeamId2());
		countQuery.setString("userName",userName);
		Long count = (Long)countQuery.uniqueResult();
		if(count !=1 ) {
			throw new Exception("No challenge found", new Exception());
		}
		
	}

}
