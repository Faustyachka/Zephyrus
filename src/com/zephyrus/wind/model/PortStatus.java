package com.zephyrus.wind.model;


/**
 * The persistent class for the PORT_STATUS database table.
 * 														
 * @author Miroshnychenko Nataliya											
 */

public class PortStatus {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String portStatusValue;

	public PortStatus() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPortStatusValue() {
		return portStatusValue;
	}

	public void setPortStatusValue(String portStatusValue) {
		this.portStatusValue = portStatusValue;
	}
	
	

}
