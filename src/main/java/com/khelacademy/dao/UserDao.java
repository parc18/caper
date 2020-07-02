package com.khelacademy.dao;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;

import com.khelacademy.dto.UserDto;
import com.khelacademy.model.BasicUserDetails;
import com.khelacademy.www.pojos.BookingRequestObject;
import com.khelacademy.www.pojos.User;

public interface UserDao {
	Response getUserById(Integer userId) throws SQLException;

	Response getUserByEmailId(Integer emailId);

	String registerUser(User userDetails) throws SQLException;

	String recordTempUsers(BookingRequestObject requestObject) throws SQLException;

	boolean updateStatus(String phone, Integer status);

	BasicUserDetails getJwt(String username, String password);
	
	public ResponseEntity<?> FirstTimeRegistration(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> userLogin(UserDto userReq) throws Exception;
}
