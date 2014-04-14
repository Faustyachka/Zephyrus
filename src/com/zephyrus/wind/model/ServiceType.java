package com.zephyrus.wind.model;

import java.io.Serializable;


/**
 * The persistent class for the SERVICE_TYPE database table.
 * 
 */

public class ServiceType implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;


	private String serviceType;

	public ServiceType() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}