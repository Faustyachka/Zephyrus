package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The persistent class for the PORTS database table.
 * 
 */

public class Port implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;

	private BigDecimal deviceId;

	private BigDecimal portOn;
	public BigDecimal getPortOn() {
		return portOn;
	}

	public void setPortOn(BigDecimal portOn) {
		this.portOn = portOn;
	}

	private BigDecimal portNumber;

	public Port() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(BigDecimal deviceId) {
		this.deviceId = deviceId;
	}

	public BigDecimal getPortNumber() {
		return this.portNumber;
	}

	public void setPortNumber(BigDecimal portNumber) {
		this.portNumber = portNumber;
	}

}