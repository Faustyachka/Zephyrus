package com.zephyrus.wind.enums;

public enum OrderStatus {
	ENTERING(1),
	PROCESSING(2),
	COMPLETED(3),
	CANCELLED(4);

	int id;
	
	OrderStatus(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
