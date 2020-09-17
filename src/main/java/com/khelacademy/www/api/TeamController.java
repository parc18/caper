package com.khelacademy.www.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khelacademy.dao.TeamDao;
import com.khelacademy.dao.UserDao;
import com.khelacademy.dto.TeamDto;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;

@RestController
@CrossOrigin
public class TeamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	UserDao userDao;
	
	@Autowired
	TeamDao teamDao;
	
	@RequestMapping(value = "team", method = RequestMethod.POST)
	public ResponseEntity<?> createTeam(@RequestBody TeamDto teamRequest) {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		teamRequest.setUserId(userDao.getUserIdByUserName(userName));
		try {
			teamDao.createTeam(teamRequest);
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}
	
	@RequestMapping(value = "team", method = RequestMethod.PUT)
	public ResponseEntity<?> renameTeam(@RequestBody TeamDto teamRequest) {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		teamRequest.setUserId(userDao.getUserIdByUserName(userName));
		try {
			teamDao.renameTeam(teamRequest);
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}		
	}
	@RequestMapping(value = "team/invite", method = RequestMethod.POST)
	public ResponseEntity<?> invitetoTeam(@RequestBody TeamDto teamRequest) {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		teamRequest.setUserId(userDao.getUserIdByUserName(userName));
		try {
			teamDao.inviteToTeam(teamRequest);
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}
	@RequestMapping(value = "team/action-on-invite", method = RequestMethod.POST)
	public ResponseEntity<?> actionOnIvite(@RequestBody TeamDto teamRequest) {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		teamRequest.setUserId(userDao.getUserIdByUserName(userName));
		try {
			teamDao.takeActionOnInvite(teamRequest);
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}
}
