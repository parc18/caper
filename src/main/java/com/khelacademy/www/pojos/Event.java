package com.khelacademy.www.pojos;

import java.util.Date;

public class Event {
    private Integer eventId;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String eventName;
    private Integer eventType;
    private Integer eventIdValue;
    private Date date;
    private Integer status;
    private String description;
    private String eventVenue;
    private String imgUrl;
    private String[] sponsers;
    private String[] organizers;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;

    public String[] getSponsers() {
        return sponsers;
    }

    public void setSponsers(String[] sponsers) {
        this.sponsers = sponsers;
    }

    public String[] getOrganizers() {
        return organizers;
    }

    public void setOrganizers(String[] organizers) {
        this.organizers = organizers;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private Integer price;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventIdValue() {
        return eventIdValue;
    }

    public void setEventIdValue(Integer eventIdValue) {
        this.eventIdValue = eventIdValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
