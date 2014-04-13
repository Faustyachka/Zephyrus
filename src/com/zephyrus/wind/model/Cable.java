package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the CABLES database table.
 * 
 */

public class Cable implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;


	private java.math.BigDecimal portId;

	private java.math.BigDecimal serviceLocationId;

	public Cable() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.math.BigDecimal getPortId() {
		return this.portId;
	}

	public void setPortId(java.math.BigDecimal portId) {
		this.portId = portId;
	}

	public java.math.BigDecimal getServiceLocationId() {
		return this.serviceLocationId;
	}

	public void setServiceLocationId(java.math.BigDecimal serviceLocationId) {
		this.serviceLocationId = serviceLocationId;
	}

}