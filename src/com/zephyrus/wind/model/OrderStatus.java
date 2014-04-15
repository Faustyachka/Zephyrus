package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the ORDER_STATUS database table.
 * 
 */

public class OrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;


	private String orderStatusValue;

	public OrderStatus() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderStatusValue() {
		return this.orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

}