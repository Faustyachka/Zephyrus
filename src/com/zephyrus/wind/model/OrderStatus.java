package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the ORDER_STATUS database table.
 * 																							// REVIEW: author expected
 */

public class OrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;


	private String orderStatusValue;

	public OrderStatus() {
	}

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