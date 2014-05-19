package com.zephyrus.wind.model;

/**
 * The persistent class for the SERVICE_TYPE database table.
 */
public class ServiceType {

	private Integer id;
	private String serviceType;

	public ServiceType() { }

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