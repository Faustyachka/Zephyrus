package com.zephyrus.wind.model;

import java.io.Serializable;


/**
 * The persistent class for the TASKS database table.
 * 																									// REVIEW: author expected
 */

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	
	private UserRole role;


	private ServiceOrder serviceOrder;

	
	private TaskStatus taskStatus;

	private String taskValue;


	private User user;

	public Task() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getTaskValue() {
		return taskValue;
	}

	public void setTaskValue(String taskValue) {
		this.taskValue = taskValue;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	

}