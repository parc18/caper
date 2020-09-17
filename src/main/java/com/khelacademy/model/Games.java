package com.khelacademy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Games {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private Integer gameId;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "google_name")
	private String googleName;

	@Column(name = "max_player")
	private Integer maxPlayer;

	@Column(name = "min_player")
	private Integer minPlayer;

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	@Column(name = "status")
	private String status;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGoogleName() {
		return googleName;
	}

	public void setGoogleName(String googleName) {
		this.googleName = googleName;
	}

	public Integer getMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(Integer maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public Integer getMinPlayer() {
		return minPlayer;
	}

	public void setMinPlayer(Integer minPlayer) {
		this.minPlayer = minPlayer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
