package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the DEVICES database table.
 * 
 */

public class Device implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;


	private String serialNum;

	public Device() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

}