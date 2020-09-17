package com.khelacademy.www.pojos;

import java.util.Map;

public class TeamDetailResponse {
	private Integer teamId;
	private Integer minPlayer;
	private Integer maxPlayer;
	private Integer currentPlayerCount;
	private Map<String, String> playerInfo;
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getMinPlayer() {
		return minPlayer;
	}
	public void setMinPlayer(Integer minPlayer) {
		this.minPlayer = minPlayer;
	}
	public Integer getMaxPlayer() {
		return maxPlayer;
	}
	public void setMaxPlayer(Integer maxPlayer) {
		this.maxPlayer = maxPlayer;
	}
	public Integer getCurrentPlayerCount() {
		return currentPlayerCount;
	}
	public void setCurrentPlayerCount(Integer currentPlayerCount) {
		this.currentPlayerCount = currentPlayerCount;
	}
	public Map<String, String> getPlayerInfo() {
		return playerInfo;
	}
	public void setPlayerInfo(Map<String, String> playerInfo) {
		this.playerInfo = playerInfo;
	}
}
