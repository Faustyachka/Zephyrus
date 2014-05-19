package com.zephyrus.wind.model;

/**
 * The persistent class for the ORDER_STATUS database table.
 */
public class OrderStatus {

	private Integer id;
	private String orderStatusValue;

	public OrderStatus() { }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderStatusValue() {
		return this.orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}
}