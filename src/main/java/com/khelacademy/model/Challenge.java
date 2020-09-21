package com.khelacademy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "challenge")
public class Challenge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "match_id")
	private Integer matchId;

	@Column(name = "team_id_1")
	private Integer teamId1;

	@Column(name = "team_id_2")
	private Integer teamId2;	

	@Column(name = "cat_id")
	private Integer catId;

	@Column(name = "status")
	@JsonIgnore
	private String status;

	@Column(name = "created_at")
	@JsonIgnore
	private Timestamp createdAt;
	
	@Column(name = "modified_at")
	@JsonIgnore
	private Timestamp modifiedAt;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
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

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

}
