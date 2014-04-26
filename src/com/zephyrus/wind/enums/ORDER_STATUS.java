package com.zephyrus.wind.enums;
																							// REVIEW: documentation expected
public enum ORDER_STATUS {
	ENTERING(1),
	PROCESSING(2),
	COMPLETED(3),
	CANCELLED(4);

	int id;
	
	ORDER_STATUS(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
