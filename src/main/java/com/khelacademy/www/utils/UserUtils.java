package com.khelacademy.www.utils;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

public class UserUtils {
	
	@Value("${jwt.basicAuth}")
	private static String basicAuthToken;
	
    public static String getSaltString(Integer length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    public static boolean validateBasicAuth(String auth) {
    	return true;
    	//return auth.equals(basicAuthToken) ? true : false;
    }
    
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
     }
    
}
