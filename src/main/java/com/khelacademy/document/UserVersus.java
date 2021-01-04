package com.khelacademy.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.khelacademy.www.pojos.User;

public class UserVersus {
	private User user1;
	private User user2;
	private User winner;
	private Integer round;
	private Integer totalRound;
	private Date startTime;
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public User getWinner() {
		return winner;
	}
	public void setWinner(User winner) {
		this.winner = winner;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public Integer getTotalRound() {
		return totalRound;
	}
	public void setTotalRound(Integer totalRound) {
		this.totalRound = totalRound;
	}
	

}
