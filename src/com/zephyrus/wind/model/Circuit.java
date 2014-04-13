package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The persistent class for the CIRCUITS database table.
 * 
 */

public class Circuit implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;


	private BigDecimal portId;

	private BigDecimal status;

	public Circuit() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPortId() {
		return this.portId;
	}

	public void setPortId(BigDecimal portId) {
		this.portId = portId;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

}