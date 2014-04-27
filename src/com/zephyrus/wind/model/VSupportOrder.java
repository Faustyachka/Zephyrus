package com.zephyrus.wind.model;

import java.io.Serializable;

import java.sql.Date;


/**
 * The persistent class for the V_SUPPORT_ORDER database table.
 * 																										// REVIEW: author expected
 */

public class VSupportOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String service;

	private String slCoord;

	private Date soDate;

	private ServiceOrder so;

	private String soValue;

	private User user;

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



	public ServiceOrder getSo() {
		return so;
	}

	public void setSo(ServiceOrder so) {
		this.so = so;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSoValue() {
		return this.soValue;
	}

	public void setSoValue(String soValue) {
		this.soValue = soValue;
	}


}