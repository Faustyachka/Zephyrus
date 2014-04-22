package com.zephyrus.wind.enums;

public enum ServiceInstanceStatus {
	PLANNED(1),
	ACTIVE(2),
	DISCONNECTED(3);

	int id;
	
	ServiceInstanceStatus(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
