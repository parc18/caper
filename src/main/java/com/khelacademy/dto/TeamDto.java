package com.khelacademy.dto;

import java.io.Serializable;

public class TeamDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String teamName;
	private int gameId;
	private int teamId;
	private Long inviteeUserId;
	private String status;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public Long getInviteeUserId() {
		return inviteeUserId;
	}
	public void setInviteeUserId(Long inviteeUserId) {
		this.inviteeUserId = inviteeUserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
