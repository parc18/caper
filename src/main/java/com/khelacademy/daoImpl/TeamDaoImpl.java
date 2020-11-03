package com.khelacademy.daoImpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.sql.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khelacademy.dao.GamesDao;
import com.khelacademy.dao.TeamDao;
import com.khelacademy.dto.TeamDto;
import com.khelacademy.model.BasicUserDetails;
import com.khelacademy.model.Games;
import com.khelacademy.model.Team;
import com.khelacademy.model.TeamDetail;
import com.khelacademy.www.api.TeamController;
import com.khelacademy.www.pojos.TeamDetailResponse;
import com.khelacademy.www.services.TeamStatus;
import com.khelacademy.www.utils.UserConstants;

@Component
@Transactional
public class TeamDaoImpl implements TeamDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private GamesDao gameDao;
	@Override
	public List<Team> createTeam(TeamDto teamDto) throws Exception {
		if(teamDto.getTeamName() == null)
			throw new Exception("Please provide a TEAM name", new Exception());
		validateTeamName(teamDto);
		validateTeamCount(teamDto);
		Games game = gameDao.getGameById(teamDto.getGameId());
		
		Team team = new Team();
		long timeNow = Calendar.getInstance().getTimeInMillis();
		team.setCreatedAt(new Timestamp(timeNow));
		team.setGameId(teamDto.getGameId());
		team.setTeamName(teamDto.getTeamName());
		team.setUserId(teamDto.getUserId());
		team.setMaxPlayer(game.getMaxPlayer());
		team.setMinPlayer(game.getMinPlayer());
		team.setStatus("ACTIVE");
		team.setUserId(teamDto.getUserId());
		Session session = this.sessionFactory.getCurrentSession();
		session.save(team);
		String hql = "FROM Team E WHERE E.userId=:userId";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setLong("userId", teamDto.getUserId());
		List<Team> results = query.list();
		return results;
		
	}

	private void validateTeamCount(TeamDto teamDto) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Query countQuery = session.createQuery(
		        "select count(*) from Team E where E.userId=:userId");
		countQuery.setLong("userId", teamDto.getUserId());
		Long count = (Long)countQuery.uniqueResult();
		LOGGER.info("Total Team count : " + count + " for userId : " + teamDto.getUserId());
		if(count > 50)
			throw new Exception("Team limit reached!! Please contact Administrator", new Exception());
	
	}

	private void validateTeamName(TeamDto teamDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.teamName =:name AND E.userId=:userId";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setString("name", teamDto.getTeamName());
		query.setLong("userId", teamDto.getUserId());
		List<Team> results = query.list();
		if(results.size() > 0)
			throw new Exception("Team name aleady exists!! Please choose a new TEAM NAME", new Exception());
	}

	@Override
	public TeamDetailResponse getTeamDetail(Integer teamId) throws Exception {
//		TeamDetailResponse teamDetail = new TeamDetailResponse();
//		Session session = this.sessionFactory.getCurrentSession();
//		String hql = "FROM Team E WHERE E.id =:id";
//		@SuppressWarnings("unchecked")
//		Query<Team> query = session.createQuery(hql);
//		query.setInteger("id", teamId);
//		List<Team> results = query.list();
//		if(results.size()==1) {
//			teamDetail.setTeamId(teamId);
//			teamDetail.setMaxPlayer(results.get(0).getMaxPlayer());
//			teamDetail.setMinPlayer(results.get(0).getMinPlayer());
//			Map<Integer, Integer> players = new HashMap<>();
//			List<Long> playerIds = new ArrayList<Long>();
//			playerIds.add(results.get(0).getUserId());
//			hql = "FROM TeamDetail E WHERE E.teamId =:id";
//			@SuppressWarnings("unchecked")
//			Query<TeamDetail> query1 = session.createQuery(hql);
//			query1.setInteger("id", teamId);
//			List<TeamDetail> results1 = query1.list();
//		}else {
//			throw new Exception("Internal Development problem , Please report", new Exception());
//		}
//		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inviteToTeam(TeamDto teamDto) throws Exception {
		if(teamDto.getInviteeUserId() == teamDto.getUserId())
			throw new Exception("You can not invite yourself !!", new Exception());
		validateEntryPosibility(teamDto);
		validateAlreadyInvited(teamDto);
		TeamDetail teamDetail = new TeamDetail();
		teamDetail.setStatus(TeamStatus.INVITED.toString());
		teamDetail.setTeam_id(teamDto.getTeamId());
		teamDetail.setUserId(teamDto.getInviteeUserId());
		long timeNow = Calendar.getInstance().getTimeInMillis();
		teamDetail.setCreatedAt(new Timestamp(timeNow));
		Session session = this.sessionFactory.getCurrentSession();
		session.save(teamDetail);
		
	}

	private void validateAlreadyInvited(TeamDto teamDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM TeamDetail E WHERE E.teamId =:tId AND E.userId =:userId AND ( E.status =:status OR E.status =:status1 )";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setInteger("tId", teamDto.getTeamId());
		query.setLong("userId", teamDto.getInviteeUserId());
		query.setString("status", TeamStatus.INVITED.toString());
		query.setString("status1", TeamStatus.ACCEPTED.toString());
		List<Team> results = query.list();
		if(results.size() != 0 ) {
			throw new Exception("You are already INVITED or JOINED the team.", new Exception());
		}

	}

	private Team validateEntryPosibility(TeamDto teamDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.id =:id";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setInteger("id", teamDto.getTeamId());
		List<Team> results = query.list();
		if(results.size() == 1) {
			if(results.get(0).getTeamName() == null)
				throw new Exception("Sorry!! Unable to join this team choose another team.", new Exception());
			if(results.get(0).getMaxPlayer() - 1 <= 0)
				throw new Exception("Sorry!! Team size is FULL.", new Exception());
			else {
				 return results.get(0);
			}
		}else {
			throw new Exception("Internal Development problem , Please report", new Exception());
		}
	}

	@Override
	public void takeActionOnInvite(TeamDto teamDto) throws Exception {
		Team t = validateEntryPosibility(teamDto);
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM TeamDetail E WHERE E.teamId =:tId AND E.userId =:userId AND E.status =:status";
		@SuppressWarnings("unchecked")
		Query<TeamDetail> query = session.createQuery(hql);
		query.setInteger("tId", teamDto.getTeamId());
		query.setLong("userId", teamDto.getUserId());
		query.setString("status", TeamStatus.INVITED.toString());
		List<TeamDetail> results = query.list();
		if(results.size() == 1) {
			if(!EnumUtils.isValidEnum(TeamStatus.class, teamDto.getStatus())) {
				 throw new Exception("Internal Development Enum problem , Please report", new Exception());
			}
			
			results.get(0).setStatus(teamDto.getStatus());
			session.update(results.get(0));
			if(t.getPlayerCount() == null) {
				t.setPlayerCount(1);
			}else {
				t.setPlayerCount(t.getPlayerCount()+1);
			}
			session.update(t);
		}else {
			session.flush();
			throw new Exception("Internal Development problem , Please report", new Exception());
		}
	}

	@Override
	public void renameTeam(TeamDto teamDto) throws Exception {
		validateTeamName(teamDto);
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.id =:id";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setInteger("id", teamDto.getTeamId());
		List<Team> results = query.list();
		if (results != null && results.size() == 1) {
				results.get(0).setTeamName(teamDto.getTeamName());;
				session.update(results.get(0));
		}
	}

	@Override
	public Team createSoloTeam(TeamDto teamDto) throws Exception {
		Team foundTeam = validateSoloTeamName(teamDto);
		if(foundTeam != null)
			return foundTeam;
		//validateTeamCount(teamDto);
		Games game = gameDao.getGameById(teamDto.getGameId());
		
		Team team = new Team();
		long timeNow = Calendar.getInstance().getTimeInMillis();
		team.setCreatedAt(new Timestamp(timeNow));
		team.setGameId(teamDto.getGameId());
		team.setTeamName(null);
		team.setUserId(teamDto.getUserId());
		team.setMaxPlayer(game.getMaxPlayer());
		team.setMinPlayer(game.getMinPlayer());
		team.setStatus("ACTIVE");
		team.setUserId(teamDto.getUserId());
		Session session = this.sessionFactory.getCurrentSession();
		session.save(team);
		return team;
	}

	private Team validateSoloTeamName(TeamDto teamDto) throws Exception {
		if(teamDto.getGameId() < 1)
			throw new Exception("Game id is missing !!", new Exception());
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.teamName is NULL AND E.userId=:userId AND E.gameId =:gameId";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		//query.setString("name", null);
		query.setLong("userId", teamDto.getUserId());
		query.setInteger("gameId", teamDto.getGameId());
		List<Team> results = query.list();
		if(results.size() == 1)
			return results.get(0);
		return null;
	}

	@Override
	public List<Team> myTeam(TeamDto teamDto) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Team E WHERE E.userId=:userId";
		@SuppressWarnings("unchecked")
		Query<Team> query = session.createQuery(hql);
		query.setLong("userId", teamDto.getUserId());
		List<Team> results = query.list();
		return results;
	}

}
