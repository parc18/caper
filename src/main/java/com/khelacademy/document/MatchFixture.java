package com.khelacademy.document;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.khelacademy.www.pojos.Fixtures;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchFixture implements Cloneable {
	@Id
	private String id;
	private Integer eventId;
	private Integer round;
	private Integer totalRound;
	private Map<String, ConcurrentHashMap<String, List<List<UserVersus>>>> categoryWise;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getTotalRound() {
		return totalRound;
	}

	public void setTotalRound(Integer totalRound) {
		this.totalRound = totalRound;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Map<String, ConcurrentHashMap<String, List<List<UserVersus>>>> getCategoryWise() {
		return categoryWise;
	}

	public void setCategoryWise(Map<String, ConcurrentHashMap<String, List<List<UserVersus>>>> categoryWise) {
		this.categoryWise = categoryWise;
	}
}
