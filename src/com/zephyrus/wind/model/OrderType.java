package com.zephyrus.wind.model;

/**
 * The persistent class for the ORDER_TYPE database table.
 */

public class OrderType {

	private Integer id;
	private String orderType;

	public OrderType() { }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}