package com.khelacademy.document;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.khelacademy.www.pojos.Fixtures;

@Document
public class MatchFixture {
	@Id
	private String id;
	private Integer eventId;
	private Map<String, Map<String, List<UserVersus>>> categoryWise;
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Map<String, Map<String, List<UserVersus>>> getCategoryWise() {
		return categoryWise;
	}
	public void setCategoryWise(Map<String, Map<String, List<UserVersus>>> categoryWise) {
		this.categoryWise = categoryWise;
	}

}
