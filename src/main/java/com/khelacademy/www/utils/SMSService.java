package com.khelacademy.www.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.khelacademy.dao.UserDao;
import com.khelacademy.daoImpl.UserDaoImpl;
import com.khelacademy.www.pojos.SMSResponse;

public class SMSService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	final String uri = "https://2factor.in/API/V1/b991d218-3b42-11e8-a895-0200cd936042/SMS/";
	public String sendSMS(String phone)
	{
		try{
		    RestTemplate restTemplate = new RestTemplate();
		    String result = restTemplate.getForObject(uri+phone+"/AUTOGEN", String.class);
		    Gson g = new Gson(); 
		    SMSResponse p = g.fromJson(result, SMSResponse.class);
		    if(p.getStatus().equals("Success")){
		    	return p.getDetails();
		    }else{
		    	LOGGER.error("ERROR in sending OTP to "+ phone);
		    }
		    return "ERROR";
		}catch(Exception e){
			return e.getMessage();
		}

	}
	public String verifyOTP(String SessionDetail, String OTP, String phone){
		try{
		    RestTemplate restTemplate = new RestTemplate();
		    String result = restTemplate.getForObject(uri+"VERIFY/"+SessionDetail+"/"+OTP, String.class);
		    Gson g = new Gson(); 
		    SMSResponse p = g.fromJson(result, SMSResponse.class);
		    if(p.getStatus().equals("Success")){
		    	UserDao userStatus = new UserDaoImpl();
		    	userStatus.updateStatus(phone, 12);
		    	return p.getDetails();
		    }else{
		    	LOGGER.error("ERROR in verifying OTP of "+phone);
		    }
		    return "ERROR";
		}catch(Exception e){
			LOGGER.error("ERROR in verifying OTP of "+phone + "with ERROR" +e.getMessage());
			return "ERROR";
		}
	}
    
}
