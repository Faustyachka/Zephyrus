package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the PORTS database table.
 * 
 */

public class Port implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	private Integer deviceId;

	private Integer portNumber;

	public Port() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

}