package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the CABLES database table.
 * 																						// REVIEW: author expected
 */

public class Cable implements Serializable {
	private static final long serialVersionUID = 1L;


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