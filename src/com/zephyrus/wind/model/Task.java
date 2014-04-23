package com.zephyrus.wind.model;

import java.io.Serializable;


/**
 * The persistent class for the TASKS database table.
 * 
 */

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	
	private Integer roleId;


	private Integer serviceOrderId;

	
	private Integer taskStatusId;

	private String taskValue;


	private Integer userId;

	public Task() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public Integer getTaskStatusId() {
		return taskStatusId;
	}

	public void setTaskStatusId(Integer taskStatusId) {
		this.taskStatusId = taskStatusId;
	}

	public String getTaskValue() {
		return taskValue;
	}

	public void setTaskValue(String taskValue) {
		this.taskValue = taskValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	

}