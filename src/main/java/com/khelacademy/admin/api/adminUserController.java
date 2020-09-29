package com.khelacademy.admin.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khelacademy.dao.MatchDao;
import com.khelacademy.dto.MatchDto;
import com.khelacademy.model.Match;
import com.khelacademy.www.api.TeamController;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;
@RestController
@CrossOrigin
public class adminUserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(adminUserController.class);
	
	@Autowired
	MatchDao matchDao;
	
	@RequestMapping(value = "match/start", method = RequestMethod.POST)
	public ResponseEntity<?> startMatch(@RequestBody MatchDto matchDto) {
		SecurityContext context = SecurityContextHolder.getContext();
		LOGGER.info("lols" + context.getAuthentication().getAuthorities().toString());
		Boolean process = false;
	    for (GrantedAuthority auth : context.getAuthentication().getAuthorities()) {
	        if ("ROLE_admin".equalsIgnoreCase(auth.getAuthority()))
	            process = true;
	    }
	    if(!process) {
	    	MyErrors error = new MyErrors("You are not authorized");
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	    }
		String userName = context.getAuthentication().getName();
		matchDto.setUpdatedByUserName(userName);
		try {
			Match match = matchDao.startMatch(matchDto);
			ApiFormatter<Match> success = ServiceUtil.convertToSuccessResponse(match);
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}
	@RequestMapping(value = "match/update-score", method = RequestMethod.POST)
	public ResponseEntity<?> updateMatchScore(@RequestBody MatchDto matchDto) {
		SecurityContext context = SecurityContextHolder.getContext();
		LOGGER.info("lols" + context.getAuthentication().getAuthorities().toString());
		Boolean process = false;
	    for (GrantedAuthority auth : context.getAuthentication().getAuthorities()) {
	        if ("ROLE_admin".equalsIgnoreCase(auth.getAuthority()))
	            process = true;
	    }
	    if(!process) {
	    	MyErrors error = new MyErrors("You are not authorized");
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	    }
		String userName = context.getAuthentication().getName();
		matchDto.setUpdatedByUserName(userName);
		try {
			Match match = matchDao.updateScore(matchDto);
			ApiFormatter<Match> success = ServiceUtil.convertToSuccessResponse(match);
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}
	@RequestMapping(value = "match/end", method = RequestMethod.POST)
	public ResponseEntity<?> concludeMatchScore(@RequestBody MatchDto matchDto) {
		SecurityContext context = SecurityContextHolder.getContext();
		LOGGER.info("lols" + context.getAuthentication().getAuthorities().toString());
		Boolean process = false;
	    for (GrantedAuthority auth : context.getAuthentication().getAuthorities()) {
	        if ("ROLE_admin".equalsIgnoreCase(auth.getAuthority()))
	            process = true;
	    }
	    if(!process) {
	    	MyErrors error = new MyErrors("You are not authorized");
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	    }
	    
		String userName = context.getAuthentication().getName();
		matchDto.setUpdatedByUserName(userName);
		try {
			Match match = matchDao.concludeMatch(matchDto);
			ApiFormatter<Match> success = ServiceUtil.convertToSuccessResponse(match);
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
		
	}
}
