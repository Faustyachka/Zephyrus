package com.zephyrus.wind.model;

/**
 * The persistent class for the TASK_STATUS database table.
 */
public class TaskStatus {

	private Integer id;
	private String taskStatusValue;

	public TaskStatus() { }

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