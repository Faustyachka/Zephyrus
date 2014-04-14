package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the TASK_STATUS database table.
 * 
 */

public class TaskStatus implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;

	private String taskStatusValue;

	public TaskStatus() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskStatusValue() {
		return this.taskStatusValue;
	}

	public void setTaskStatusValue(String taskStatusValue) {
		this.taskStatusValue = taskStatusValue;
	}

}