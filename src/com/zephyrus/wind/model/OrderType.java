package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the ORDER_TYPE database table.
 * 																									// REVIEW: author expected
 */

public class OrderType implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;


	private String orderType;

	public OrderType() {
	}

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