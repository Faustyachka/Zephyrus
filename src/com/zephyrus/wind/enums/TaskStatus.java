package com.zephyrus.wind.enums;

public enum TaskStatus {
	NEW(1),
	PROCESSING(2),
	COMPLETE(3),
	SUSPEND(4);

	int id;
	
	TaskStatus(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
