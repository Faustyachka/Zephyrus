package com.zephyrus.wind.enums;

public enum OrderType {
	NEW(1),
	MODIFY(2),
	DISCONNECT(3);

	int id;
	
	OrderType(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
