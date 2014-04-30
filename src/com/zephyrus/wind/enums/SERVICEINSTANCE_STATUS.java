package com.zephyrus.wind.enums;
																								// REVIEW: documentation expected
public enum SERVICEINSTANCE_STATUS {
	PLANNED(1),
	ACTIVE(2),
	DISCONNECTED(3),
	PENDINGDISCONNECTION(4);

	int id;
	
	SERVICEINSTANCE_STATUS(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
