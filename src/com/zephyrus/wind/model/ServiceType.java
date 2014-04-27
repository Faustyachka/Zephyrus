package com.zephyrus.wind.model;

import java.io.Serializable;


/**
 * The persistent class for the SERVICE_TYPE database table.
 * 																								// REVIEW: author expected
 */

public class ServiceType implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;


	private String serviceType;

	public ServiceType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}