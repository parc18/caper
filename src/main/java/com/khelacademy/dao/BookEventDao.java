package com.khelacademy.dao;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Response;

import com.khelacademy.www.pojos.BookingRequestObject;

public interface BookEventDao {
	Response bookSingleTicket(BookingRequestObject bookingRequestObject, boolean b) throws UnsupportedEncodingException;
	Response bookMultipleTicket(BookingRequestObject bookingRequestObject);
}