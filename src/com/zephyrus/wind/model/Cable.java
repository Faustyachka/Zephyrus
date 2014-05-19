package com.zephyrus.wind.model;

/**
 * The persistent class for the CABLES database table.
 */
public class Cable {
	
	private Integer id;
	private Port port;
	private ServiceLocation serviceLocation;
	private String cableType;

	public Cable() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public ServiceLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ServiceLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getCableType() {
		return cableType;
	}

	public void setCableType(String cableType) {
		this.cableType = cableType;
	}
}