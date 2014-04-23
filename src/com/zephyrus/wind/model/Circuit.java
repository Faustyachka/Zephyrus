package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the CIRCUITS database table.
 * 
 */

public class Circuit implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;


	private Integer portId;


	public Circuit() {
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

	

}