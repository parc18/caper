package com.khelacademy.www.api;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.khelacademy.dao.UserDao;
import com.khelacademy.dto.UserDto;
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
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.FirstTimeRegistration(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}

}
