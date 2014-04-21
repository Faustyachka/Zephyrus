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

	private BigDecimal price;


	private String service;


	private BigDecimal siId;



	private Date siStardDate;


	private String siStatus;


	private BigDecimal userId;

	public VSupportInstance() {
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public BigDecimal getSiId() {
		return this.siId;
	}

	public void setSiId(BigDecimal siId) {
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

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}