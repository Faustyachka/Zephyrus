package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.Date;


/**
 * The persistent class for the " V_SUPPORT_INSTANCE" database table.
 * 
 */

public class VSupportInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer price;


	private String service;


	private Integer siId;



	private Date siStardDate;


	private String siStatus;


	private Integer userId;

	public VSupportInstance() {
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getSiId() {
		return this.siId;
	}

	public void setSiId(Integer siId) {
		this.siId = siId;
	}

	public Date getSiStardDate() {
		return this.siStardDate;
	}

	public void setSiStardDate(Date siStardDate) {
		this.siStardDate = siStardDate;
	}

	public String getSiStatus() {
		return this.siStatus;
	}

	public void setSiStatus(String siStatus) {
		this.siStatus = siStatus;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}