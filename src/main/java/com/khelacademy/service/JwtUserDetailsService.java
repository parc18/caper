package com.khelacademy.service;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//import com.khelacademy.dao.BasicUserDetailRespository;
//import com.khelacademy.dao.UserDao;
//import com.khelacademy.dto.UserDto;
//import com.khelacademy.model.BasicUserDetails;


@Component
public class JwtUserDetailsService implements UserDetailsService {

	//@Autowired
	//private BasicUserDetailRespository basicUserDetailRespository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
//	public BasicUserDetails save(UserDto user) {
//		BasicUserDetails newUser = new BasicUserDetails();
//		newUser.setUsername(user.getUsername());
//		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//		//return basicUserDetailRespository.save(newUser);
//		return null;
//	}
}