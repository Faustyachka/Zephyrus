package com.zephyrus.wind.enums;

public enum PORT_STATUS {
	FREE(1),
	BUSY(2),
	DEFFECTIVE(3);

	int id;
	
	PORT_STATUS(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}

}
