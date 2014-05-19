package com.zephyrus.wind.model;

/**
 * The persistent class for the DEVICES database table.
 */
public class Device {

	private Integer id;
	private String serialNum;

	public Device() { }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

}