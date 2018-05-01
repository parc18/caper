package com.khelacademy.dao;

import javax.ws.rs.core.Response;

public interface EventDao {
    Response getAllEvents();
    Response getEventByCityId(Integer city);
    Response getEventPrice(Integer eventId);

}
