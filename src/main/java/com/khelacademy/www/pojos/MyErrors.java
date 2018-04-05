package com.khelacademy.www.pojos;

public class MyErrors {
	private String errorMsg = null;
	public MyErrors (String s){
		this.errorMsg = s;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
