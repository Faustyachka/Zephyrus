package com.zephyrus.wind.enums;

public enum TASK_STATUS {
	NEW(1),
	PROCESSING(2),
	COMPLETE(3),
	SUSPEND(4);

	int id;
	
	TASK_STATUS(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
