package com.zephyrus.wind.enums;
																						
public enum USER_STATUS {
	ACTIVE(0),
	BLOCKED(1);
	
	int status;
	
	USER_STATUS(int status) {
		this.status = status;
	}
	
	public int getValue(){
		return status;
	}
}
