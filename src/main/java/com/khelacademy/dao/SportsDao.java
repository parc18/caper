package com.khelacademy.dao;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface SportsDao {
	Response getAllSports();
}
