package com.khelacademy.www.api;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.khelacademy.dao.UserDao;
import com.khelacademy.dto.UserDto;
import com.khelacademy.service.JwtUserDetailsService;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.UserUtils;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "user/signup", method = RequestMethod.POST)
	public ResponseEntity<?> userSignUp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.FirstTimeRegistration(userRequest);
		} else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public ResponseEntity<?> userLogin(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> userPasswordReset(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/verify-eotp", method = RequestMethod.POST)
	public ResponseEntity<?> userVerificationViaEmailOtp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userVerifyOtp(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/send-eotp", method = RequestMethod.POST)
	public ResponseEntity<?> sendEotp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
}
