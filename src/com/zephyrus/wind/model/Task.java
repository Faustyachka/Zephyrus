package com.zephyrus.wind.model;

import java.io.Serializable;


/**
 * The persistent class for the TASKS database table.
 * 
 */

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;

	
	private java.math.BigDecimal roleId;


	private java.math.BigDecimal serviceOrderId;

	
	private java.math.BigDecimal taskStatusId;

	private String taskValue;


	private java.math.BigDecimal userId;

	public Task() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.math.BigDecimal getRoleId() {
		return this.roleId;
	}

	public void setRoleId(java.math.BigDecimal roleId) {
		this.roleId = roleId;
	}

	public java.math.BigDecimal getServiceOrderId() {
		return this.serviceOrderId;
	}

	public void setServiceOrderId(java.math.BigDecimal serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public java.math.BigDecimal getTaskStatusId() {
		return this.taskStatusId;
	}

	public void setTaskStatusId(java.math.BigDecimal taskStatusId) {
		this.taskStatusId = taskStatusId;
	}

	public String getTaskValue() {
		return this.taskValue;
	}

	public void setTaskValue(String taskValue) {
		this.taskValue = taskValue;
	}

	public java.math.BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(java.math.BigDecimal userId) {
		this.userId = userId;
	}

}