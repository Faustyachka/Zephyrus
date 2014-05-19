package com.zephyrus.wind.model;

/**
 * The persistent class for the CIRCUITS database table.
 */
public class Circuit {
	
	private Integer id;
	private Port port;
	private String config;

	public Circuit() {
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

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
}