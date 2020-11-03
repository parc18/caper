package com.khelacademy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "max_score")
	private Integer maxScore;
	
	@Column(name = "status")
	private String status;

	@Column(name = "team_id_1")
	private Integer teamId1;
	
	@Column(name = "team_id_2")
	private Integer teamId2;
	
	@Column(name = "score_t1")
	private Integer score1;
	
	@Column(name = "score_t2")
	private Integer score2;
	
	@Column(name = "amortize_score_t1")
	private Double amortizeScore1;

	@Column(name = "amortize_score_t2")
	private Double amortizeScore2;
	
	@Column(name = "match_start_at")
	private Timestamp matchStartTime;
	
	@Column(name = "match_end_at")
	private Timestamp matchEndTime;
	
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "updatedBy")
	private String updatedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTeamId1() {
		return teamId1;
	}

	public void setTeamId1(Integer teamId1) {
		this.teamId1 = teamId1;
	}

	public Integer getTeamId2() {
		return teamId2;
	}

	public void setTeamId2(Integer teamId2) {
		this.teamId2 = teamId2;
	}

	public Integer getScore1() {
		return score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Integer getScore2() {
		return score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	public Timestamp getMatchStartTime() {
		return matchStartTime;
	}

	public void setMatchStartTime(Timestamp matchStartTime) {
		this.matchStartTime = matchStartTime;
	}

	public Timestamp getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(Timestamp matchEndTime) {
		this.matchEndTime = matchEndTime;
	}

	public Double getAmortizeScore1() {
		return amortizeScore1;
	}

	public void setAmortizeScore1(Double amortizeScore1) {
		this.amortizeScore1 = amortizeScore1;
	}

	public Double getAmortizeScore2() {
		return amortizeScore2;
	}

	public void setAmortizeScore2(Double amortizeScore2) {
		this.amortizeScore2 = amortizeScore2;
	}

}