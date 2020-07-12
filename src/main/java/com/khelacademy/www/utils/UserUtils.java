package com.khelacademy.www.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

	private static String basicAuthToken;
	
	 static Character[] charArray;
	 
	 static Map<Character, Integer> charMap;
	 
	 static Integer divisor = 36;
	 
	 static {
		 
		 charMap = new HashMap<Character, Integer>(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			 put('A', 1);
			 put('B', 2);
			 put('C', 3);
			 put('D', 4);
			 put('E', 5);
			 put('F', 6);
			 put('G', 7);
			 put('H', 8);
			 put('I', 9);
			 put('J', 10);
			 put('K', 11);
			 put('L', 12);
			 put('M', 13);
			 put('N', 14);
			 put('O', 15);
			 put('P', 16);
			 put('Q', 17);
			 put('R', 18);
			 put('S', 19);
			 put('T', 20);
			 put('U', 21);
			 put('V', 22);
			 put('W', 23);
			 put('X', 24);
			 put('Y', 25);
			 put('Z', 26);
			 put('0', 27);
			 put('1', 28);
			 put('2', 29);
			 put('3', 30);
			 put('4', 31);
			 put('5', 32);
			 put('6', 33);
			 put('7', 34);
			 put('8', 35);
			 put('9', 36);

			 
		 }};
		 
		 
		 
		 
		 
		 charArray = new Character[] {
				new Character('A'),
				new Character('B'),
				new Character('C'),
				new Character('D'),
				new Character('E'),
				new Character('F'),
				new Character('G'),
				new Character('H'),
				new Character('I'),
				new Character('J'),
				new Character('K'),
				new Character('L'),
				new Character('M'),
				new Character('N'),
				new Character('O'),
				new Character('P'),
				new Character('Q'),
				new Character('R'),
				new Character('S'),
				new Character('T'),
				new Character('U'),
				new Character('V'),
				new Character('W'),
				new Character('X'),
				new Character('Y'),
				new Character('Z'),
				new Character('0'),
				new Character('1'),
				new Character('2'),
				new Character('3'),
				new Character('4'),
				new Character('5'),
				new Character('6'),
				new Character('7'),
				new Character('8'),
				new Character('9')
		 };
	 }

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
		// return true;
		basicAuthToken = loadProperties("jwt.basicAuth");
		return auth.equals(basicAuthToken) ? true : false;
	}

	public static boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String getEncryptedPass(String passWord) {
		String md5Hex = DigestUtils.md5Hex(passWord).toUpperCase();
		return md5Hex;
	}

	public static Integer getRandomNumber(Integer x) {
		Random rand = new Random();
		return rand.nextInt(x);
	}

	public static String loadProperties(String key) {
		try {
			Properties configuration = new Properties();
			InputStream inputStream = UserUtils.class.getClassLoader().getResourceAsStream("application.properties");
			configuration.load(inputStream);
			inputStream.close();
			return configuration.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isValid10DigitPhone(String phone) {
		if (phone.length() != 10)
			return false;
		try {
			Integer.parseInt(phone);
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception e2) {
			return false;
		}

		return true;
	}
	public static String getAlphaNumericNextUserNameForGivenLastUserName(String userName) {
		if(userName==null || userName.equals(""))
			return Character.toString(charArray[0]);
		boolean carry = false;
		char[] charUserName = userName.toCharArray();
		for(int i=charUserName.length-1; i>=0; i--) {
			if(carry) {
				if(charMap.get(charUserName[i]) == 36){
					carry = true;
					charUserName[i] = charArray[0];
				}else {
					charUserName[i] = charArray[charMap.get(charUserName[i])];
					carry = false;
					break;
				}
			}else if(charMap.get(charUserName[i]) == 36) {
				carry =true;
				charUserName[i] = charArray[0];
			}else {
				charUserName[i] = charArray[charMap.get(charUserName[i])];
				carry = false;
				break;
			}
		}
		if(carry)
			return (Character.toString(charArray[0])+String.valueOf(charUserName));
		return String.valueOf(charUserName);
	}
}
