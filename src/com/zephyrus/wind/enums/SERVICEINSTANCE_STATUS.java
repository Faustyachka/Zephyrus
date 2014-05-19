package com.zephyrus.wind.enums;
																								
public enum SERVICEINSTANCE_STATUS {
	PLANNED(1),
	ACTIVE(2),
	DISCONNECTED(3),
	PENDINGDISCONNECTION(4),
	PENDINGMODIFICATION(5);

	int id;
	
	SERVICEINSTANCE_STATUS(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
