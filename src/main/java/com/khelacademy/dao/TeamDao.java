package com.khelacademy.dao;

import com.khelacademy.dto.TeamDto;
import com.khelacademy.model.Team;
import com.khelacademy.www.pojos.TeamDetailResponse;

public interface TeamDao {
	public void createTeam(TeamDto teamDto) throws Exception;
	public Team createSoloTeam(TeamDto teamDto) throws Exception;
	public void renameTeam(TeamDto teamDto) throws Exception;
	public void inviteToTeam(TeamDto teamDto) throws Exception;
	public TeamDetailResponse getTeamDetail(Integer teamId) throws Exception;
	public void takeActionOnInvite(TeamDto teamDto) throws Exception;
}
