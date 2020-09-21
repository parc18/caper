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
@Table(name = "user_rank")
public class UserRank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cat_id")
	private Integer catId;
	
	//girls/boys/double/mixed
	@Column(name = "cat_name")
	@JsonIgnore
	private String catName;
	
	@Column(name = "cat_score")
	private Integer catScore;

	@Column(name = "cat_rank")
	private Integer catRank;

	@Column(name = "game_id")
	private Integer gameId;

	@Column(name = "user_id")
	private Long userId;
	
	
	@Column(name = "created_at")
	@JsonIgnore
	private Timestamp createdAt;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCatId() {
		return catId;
	}


	public void setCatId(Integer catId) {
		this.catId = catId;
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}


	public Integer getCatScore() {
		return catScore;
	}


	public void setCatScore(Integer catScore) {
		this.catScore = catScore;
	}


	public Integer getCatRank() {
		return catRank;
	}


	public void setCatRank(Integer catRank) {
		this.catRank = catRank;
	}


	public Integer getGameId() {
		return gameId;
	}


	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
