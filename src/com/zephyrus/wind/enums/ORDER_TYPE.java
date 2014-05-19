package com.zephyrus.wind.enums;
																							
public enum ORDER_TYPE {
	NEW(1),
	MODIFY(2),
	DISCONNECT(3);

	int id;
	
	ORDER_TYPE(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
