package com.khelacademy.dto;

public class MatchDto {
	private Integer matchId;
	private Integer challengeId;
	private Integer matchTId1;
	private Integer matchTId2;
	private Integer scoreT1;
	private Integer scoreT2;
	private Integer maxScore;
	private String updatedByUserName;
	public Integer getMatchTId1() {
		return matchTId1;
	}
	public void setMatchTId1(Integer matchTId1) {
		this.matchTId1 = matchTId1;
	}
	public Integer getMatchTId2() {
		return matchTId2;
	}
	public void setMatchTId2(Integer matchTId2) {
		this.matchTId2 = matchTId2;
	}
	public Integer getScoreT1() {
		return scoreT1;
	}
	public void setScoreT1(Integer scoreT1) {
		this.scoreT1 = scoreT1;
	}
	public Integer getScoreT2() {
		return scoreT2;
	}
	public void setScoreT2(Integer scoreT2) {
		this.scoreT2 = scoreT2;
	}
	public Integer getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	public Integer getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}
	public String getUpdatedByUserName() {
		return updatedByUserName;
	}
	public void setUpdatedByUserName(String updatedByUserName) {
		this.updatedByUserName = updatedByUserName;
	}
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
}
