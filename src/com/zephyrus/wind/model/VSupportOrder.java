package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.Date;


/**
 * The persistent class for the V_SUPPORT_ORDER database table.
 * 
 */

public class VSupportOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String service;


	private String slCoord;

	private Date soDate;

	private BigDecimal soId;

	private String soValue;

	private BigDecimal userId;

	public VSupportOrder() {
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSlCoord() {
		return this.slCoord;
	}

	public void setSlCoord(String slCoord) {
		this.slCoord = slCoord;
	}

	public Date getSoDate() {
		return this.soDate;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public BigDecimal getSoId() {
		return this.soId;
	}

	public void setSoId(BigDecimal soId) {
		this.soId = soId;
	}

	public String getSoValue() {
		return this.soValue;
	}

	public void setSoValue(String soValue) {
		this.soValue = soValue;
	}

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}