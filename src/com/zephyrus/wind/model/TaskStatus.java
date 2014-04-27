package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the TASK_STATUS database table.
 * 																									// REVIEW: author expected
 */

public class TaskStatus implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	private String taskStatusValue;

	public TaskStatus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskStatusValue() {
		return this.taskStatusValue;
	}

	public void setTaskStatusValue(String taskStatusValue) {
		this.taskStatusValue = taskStatusValue;
	}

}