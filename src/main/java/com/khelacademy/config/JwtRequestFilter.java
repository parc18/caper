package com.khelacademy.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.khelacademy.service.JwtUserDetailsService;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private static final int MAX_REQUESTS_PER_SECOND = 3;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
//	     response.addHeader("Access-Control-Allow-Origin", "*");
//	        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
//	        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//	        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
//	        response.addHeader("Access-Control-Allow-Credentials", "true");
//	        response.addIntHeader("Access-Control-Max-Age", 10);
		if (isMaximumRequestsPerSecondExceeded(request)) {
			CustomResponse(HttpStatus.TOO_MANY_REQUESTS, response);
			return;
		}
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		String role = null;
// JWT Token is in the form "Bearer token". Remove Bearer word and get
// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				Claims claim = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
				role =  claim.get("ROLE", String.class);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
// if token is valid configure Spring Security to manually set
// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		        list.add(new SimpleGrantedAuthority("ROLE_" + role));
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, list);
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
// After setting the Authentication in the context, we specify
// that the current user is authenticated. So it passes the
// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

	private boolean isMaximumRequestsPerSecondExceeded(HttpServletRequest httpServletRequest) {
		String clientIpAddress = getClientIP((HttpServletRequest) httpServletRequest);
		int requests = 0;
		try {
			requests = requestCountsPerIpAddress.get(clientIpAddress);
			if (requests >= MAX_REQUESTS_PER_SECOND && (httpServletRequest.getRequestURL().toString().contains("user/signup") || httpServletRequest.getRequestURL().toString().contains("user/login")) )
				return true;
		} catch (Exception e) {
			requests = 0;
		}

		if (httpServletRequest.getRequestURL().toString().contains("user/signup") || httpServletRequest.getRequestURL().toString().contains("user/login")) {
			requests++;
			requestCountsPerIpAddress.put(clientIpAddress, requests);
		}
		return false;
	}
	public String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
	private LoadingCache<String, Integer> requestCountsPerIpAddress;
	public void CustomResponse(HttpStatus status, HttpServletResponse response) {

		response.setStatus(status.value());
		response.setContentType("application/json");
		MyErrors error = new MyErrors(status.toString());
		ApiFormatter<MyErrors> exp = ServiceUtil.convertToFailureResponse(error, "true", 500);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(mapper.writeValueAsString(exp));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JwtRequestFilter() {
		super();
		requestCountsPerIpAddress = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}
}