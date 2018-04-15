package com.khelacademy.www.pojos;

import java.util.List;

public class EventPriceResponse {
	private Integer eventId;
	private List<EventPrice> priceDetails;
	public Integer getEventId() {
		return eventId;
	}
	public List<EventPrice> getPriceDetails() {
		return priceDetails;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public void setPriceDetails(List<EventPrice> priceDetails) {
		this.priceDetails = priceDetails;
	}
}
