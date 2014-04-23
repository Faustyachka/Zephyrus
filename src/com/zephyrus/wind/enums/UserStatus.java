package com.zephyrus.wind.enums;

public enum UserStatus {
	ACTIVE(0),
	BLOCKED(1);
	
	int status;
	
	UserStatus(int status) {
		this.status = status;
	}
	
	public int geValue(){
		return status;
	}
}
