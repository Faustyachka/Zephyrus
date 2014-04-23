package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the CIRCUITS database table.
 * 
 */

public class Circuit implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;


	private Port port;


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




	

}