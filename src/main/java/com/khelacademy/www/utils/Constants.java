package com.khelacademy.www.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    public static final int SUCCESS_RESPONSE_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final String SUCCESS_RESPONSE_VALUE = "SUCCESS!!";
    public static final String FAILURE_RESPONSE_VALUE = "FAILURE!!";
    public static final String REDIRECT_URL = "http://www.khelacademy.com/thankyou";
    public static final String WEBHOOK_URL = "https://capers-dev.herokuapp.com/api/instamojo_webhook/";
    
}
