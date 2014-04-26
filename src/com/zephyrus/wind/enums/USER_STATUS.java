package com.zephyrus.wind.enums;
																						// REVIEW: documentation expected
public enum USER_STATUS {
	ACTIVE(0),
	BLOCKED(1);
	
	int status;
	
	USER_STATUS(int status) {
		this.status = status;
	}
	
	public int geValue(){
		return status;
	}
}
