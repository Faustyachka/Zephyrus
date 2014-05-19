package com.zephyrus.wind.model;

/**
 * The persistent class for the PORTS database table.										
 */
public class Port {
	
	private Integer id;
	private Device device;
	private Integer portNumber;
	private PortStatus portStatus;

	public Port() { }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public PortStatus getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(PortStatus portStatus) {
		this.portStatus = portStatus;
	}
}
