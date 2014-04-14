package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the SERVICE_LOCATIONS database table.
 * 
 */

public class ServiceLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String serviceLocationCoord;

	private java.math.BigDecimal userId;

	public ServiceLocation() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServiceLocationCoord() {
		return this.serviceLocationCoord;
	}

	public void setServiceLocationCoord(String serviceLocationCoord) {
		this.serviceLocationCoord = serviceLocationCoord;
	}

	public java.math.BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(java.math.BigDecimal userId) {
		this.userId = userId;
	}

}