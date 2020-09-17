package com.khelacademy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team_detail")
public class TeamDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "status")
	private String status;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "team_id")
	private int teamId;

	public int getId() {
		return id;
	}

	@Column(name = "created_at")
	private Timestamp createdAt;

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getTeam_id() {
		return teamId;
	}

	public void setTeam_id(int teamId) {
		this.teamId = teamId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
