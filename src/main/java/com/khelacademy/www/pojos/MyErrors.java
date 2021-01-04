package com.khelacademy.www.pojos;

import java.io.Serializable;

public class MyErrors implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String genericMsg = null;
	public MyErrors (String s){
		this.genericMsg = s;
	}
	public String getErrorMsg() {
		return genericMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.genericMsg = errorMsg;
	}
}
