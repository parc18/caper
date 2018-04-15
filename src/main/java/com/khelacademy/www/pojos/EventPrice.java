package com.khelacademy.www.pojos;

public class EventPrice {
	private Integer priceId;
	private Integer priceAmount;
	private String desc;
	private String currency;
	
	public Integer getPriceId() {
		return priceId;
	}
	public Integer getPriceAmount() {
		return priceAmount;
	}
	public String getDesc() {
		return desc;
	}
	public String getCurrency() {
		return currency;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public void setPriceAmount(Integer priceAmount) {
		this.priceAmount = priceAmount;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
