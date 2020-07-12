package com.khelacademy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_update")
public class UserUpdate {
	UserUpdate(){}
	
	public UserUpdate(String userName, long id2, String email2, String notVerified) {
		this.userId = id2;
		this.email = email2;
		this.status = notVerified;
		this.userName = userName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "user_id")
    private Long userId;
	
	@Column(name = "password")
	@JsonIgnore
	private String passWord;
	@Column(name = "email", unique = true)
	@JsonIgnore
	private String email;
	@Column(name = "phone")
	@JsonIgnore
	private String phone;
	@Column(name = "status")
	private String status;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
