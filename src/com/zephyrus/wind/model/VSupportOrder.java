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

	private Integer soId;

	private String soValue;

	private Integer userId;

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

	public Integer getSoId() {
		return this.soId;
	}

	public void setSoId(Integer soId) {
		this.soId = soId;
	}

	public String getSoValue() {
		return this.soValue;
	}

	public void setSoValue(String soValue) {
		this.soValue = soValue;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}