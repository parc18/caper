package com.khelacademy.dto;

public class ChallengeDto {
	private Integer challengeId;
	private Integer challegerTeamId;
	private Long challegerUserId;
	private String challegerUserName;
	private String challegeeUserName;
	private Integer challengeeTeamId;
	private Integer challengeeUserId;
	private Integer gameId;
	private String status;
	public Integer getChallegerTeamId() {
		return challegerTeamId;
	}
	public void setChallegerTeamId(Integer challegerTeamId) {
		this.challegerTeamId = challegerTeamId;
	}
	public Long getChallegerUserId() {
		return challegerUserId;
	}
	public void setChallegerUserId(Long challegerUserId) {
		this.challegerUserId = challegerUserId;
	}
	public Integer getChallengeeTeamId() {
		return challengeeTeamId;
	}
	public void setChallengeeTeamId(Integer challengeeTeamId) {
		this.challengeeTeamId = challengeeTeamId;
	}
	public Integer getChallengeeUserId() {
		return challengeeUserId;
	}
	public void setChallengeeUserId(Integer challengeeUserId) {
		this.challengeeUserId = challengeeUserId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getChallegerUserName() {
		return challegerUserName;
	}
	public void setChallegerUserName(String challegerUserName) {
		this.challegerUserName = challegerUserName;
	}
	public String getChallegeeUserName() {
		return challegeeUserName;
	}
	public void setChallegeeUserName(String challegeeUserName) {
		this.challegeeUserName = challegeeUserName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}
}
