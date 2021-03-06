package com.zephyrus.wind.model;

/**
 * The persistent class for the SERVICE_INSTANCE_STATUS database table.
 */
public class ServiceInstanceStatus {
	
	private Integer id;
	private String servInstanceStatusValue;

	public ServiceInstanceStatus() { }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServInstanceStatusValue() {
		return this.servInstanceStatusValue;
	}

	public void setServInstanceStatusValue(String servInstanceStatusValue) {
		this.servInstanceStatusValue = servInstanceStatusValue;
	}
}