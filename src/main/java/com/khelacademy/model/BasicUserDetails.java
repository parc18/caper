package com.khelacademy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "basic_user_detail")
public class BasicUserDetails implements UserDetails{
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}
	public Integer getPhoneVerified() {
		return phoneVerified;
	}
	public void setPhoneVerified(Integer phoneVerified) {
		this.phoneVerified = phoneVerified;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BasicUserDetails(String email, String passWord, String status) {
		this.email = email;
		this.passWord = passWord;
		this.status = status;
	}
	BasicUserDetails(){}
	public BasicUserDetails(String phone2, String userOtpVerified) {
		this.phone = phone2;
		this.status = userOtpVerified;
	}
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
	private String status;
	@Column(name = "otp")
	@JsonIgnore
	private Integer otp;
	@Column(name = "otp_expire")
	@JsonIgnore
	private Timestamp otpExpire;
	
	@Column(name = "user_type")
	@JsonIgnore
	private String userType;	
	
	@Column(name = "email_verified")
	@JsonIgnore
	private Integer emailVerified;

	@Column(name = "phone_verified")
	@JsonIgnore
	private Integer  phoneVerified;

	public Timestamp getOtpExpire() {
		return otpExpire;
	}
	public void setOtpExpire(Timestamp otpExpire) {
		this.otpExpire = otpExpire;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	public String getStatus() {
		return status;
	}
	public String getstatus() {
		return status;
	}
	public void setStatus(String stauts) {
		this.status = stauts;
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
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_" + userType));
        return list;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
