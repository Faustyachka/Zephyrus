package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the CABLES database table.
 * 
 */

public class Cable implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;


	private Integer portId;

	private Integer serviceLocationId;

	public Cable() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPortId() {
		return portId;
	}

	public void setPortId(Integer portId) {
		this.portId = portId;
	}

	public Integer getServiceLocationId() {
		return serviceLocationId;
	}

	public void setServiceLocationId(Integer serviceLocationId) {
		this.serviceLocationId = serviceLocationId;
	}

	

}