package com.khelacademy.www.api;

import java.sql.SQLException;

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

import com.khelacademy.dao.ChallengeDao;
import com.khelacademy.dto.ChallengeDto;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;

@RestController
@CrossOrigin
public class ChallengeController {
	@Autowired
	ChallengeDao challengeDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @RequestMapping(value = "/challenge", method = RequestMethod.POST)
    public ResponseEntity<?> challange(@RequestBody ChallengeDto challengeDto) throws SQLException {
    	
    	SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		challengeDto.setChallegerUserName(userName);
		try {
			challengeDao.sendChallenge(challengeDto);
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
    }
    @RequestMapping(value = "/challenge/action", method = RequestMethod.POST)
    public ResponseEntity<?> actionOnChallange(@RequestBody ChallengeDto challengeDto) throws SQLException {
    	
    	SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		challengeDto.setChallegeeUserName(userName);
		try {
			challengeDao.actionOnChallenge(challengeDto);
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
