package com.khelacademy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "basic_user_detail")
public class BasicUserDetails {
	public BasicUserDetails(String email, String passWord) {
		this.email = email;
		this.passWord = passWord;
	}
	BasicUserDetails(){}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "username")
	private String userName;
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
	@JsonIgnore
	private String stauts;
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
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
	

}
