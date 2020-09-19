package com.khelacademy.www.pojos;

import java.util.List;

public class MyTeams {
	private List<TeamDetailResponse> myTeams;
	private List<TeamDetailResponse> joinedTeams;
	public List<TeamDetailResponse> getMyTeams() {
		return myTeams;
	}
	public void setMyTeams(List<TeamDetailResponse> myTeams) {
		this.myTeams = myTeams;
	}
	public List<TeamDetailResponse> getJoinedTeams() {
		return joinedTeams;
	}
	public void setJoinedTeams(List<TeamDetailResponse> joinedTeams) {
		this.joinedTeams = joinedTeams;
	}

}
