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
@Table(name = "team")
public class Team {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(Integer playerCount) {
		this.playerCount = playerCount;
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

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp timestamp) {
		this.createdAt = timestamp;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String teamName;

	@Column(name = "player_count")
	private Integer playerCount;

	@Column(name = "min_player_count")
	private Integer minPlayer;

	@Column(name = "max_player_count")
	private Integer maxPlayer;

	@Column(name = "game_id")
	private Integer gameId;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "status")
	private String status;

	@Column(name = "user_id")
	private Long userId;
}
