package com.khelacademy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class RazorPayConfig {
	
	@Value("${razorpay.key}")
	private String key;

	@Value("${razorpay.secret}")
	private String secret;
	
	@Bean
	public RazorpayClient razorPayclient() throws RazorpayException {
		return new RazorpayClient(key, secret);
	}

}
