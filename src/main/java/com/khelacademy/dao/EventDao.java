package com.khelacademy.dao;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;

public interface EventDao {
    Response getAllEvents();
    ResponseEntity<?> getEventByCityId(Integer city, Integer gameId);
    Response getEventPrice(Integer eventId);

}
