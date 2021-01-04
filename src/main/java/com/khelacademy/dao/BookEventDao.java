package com.khelacademy.dao;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;

import com.khelacademy.www.pojos.BookingRequestObject;

public interface BookEventDao {
	ResponseEntity<?>  bookSingleTicket(BookingRequestObject bookingRequestObject, boolean b) throws UnsupportedEncodingException;
	ResponseEntity<?>  bookMultipleTicket(BookingRequestObject bookingRequestObject);
	boolean UpdateStatusFromWbhook(String id, String status);
	// NOT IN USE
	String checkBookingStatus(String paymentId);
    Integer getLastUserGameId();
    Integer getCategoryId(Integer priceId);
}
