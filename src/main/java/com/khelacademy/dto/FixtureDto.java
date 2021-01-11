package com.khelacademy.dto;

import java.io.Serializable;

public class FixtureDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer eventId;
	
	private Integer round;
	
	private String poolName;
	
	private String winnerUserName;
	
	private String looserUserName;
	
	private Integer winnerUserId;
	
	private Integer loosetUserId;
	
	private String catName;
	
	private Integer position;
	
	private String administratedBy;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public Integer getWinnerUserId() {
		return winnerUserId;
	}

	public void setWinnerUserId(Integer winnerUserId) {
		this.winnerUserId = winnerUserId;
	}

	public Integer getLoosetUserId() {
		return loosetUserId;
	}

	public void setLoosetUserId(Integer loosetUserId) {
		this.loosetUserId = loosetUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getAdministratedBy() {
		return administratedBy;
	}

	public void setAdministratedBy(String administratedBy) {
		this.administratedBy = administratedBy;
	}

	public String getWinnerUserName() {
		return winnerUserName;
	}

	public void setWinnerUserName(String winnerUserName) {
		this.winnerUserName = winnerUserName;
	}

	public String getLooserUserName() {
		return looserUserName;
	}

	public void setLooserUserName(String looserUserName) {
		this.looserUserName = looserUserName;
	}
}
