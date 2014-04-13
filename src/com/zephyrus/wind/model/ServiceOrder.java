package com.zephyrus.wind.model;

import java.io.Serializable;

import java.util.Date;


/**
 * The persistent class for the SERVICE_ORDERS database table.
 * 
 */

public class ServiceOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private Date orderDate;


	private java.math.BigDecimal orderStatusId;

	private java.math.BigDecimal orderTypeId;

	
	private java.math.BigDecimal productCatalogId;


	private java.math.BigDecimal serviceInstanceId;

	
	private java.math.BigDecimal serviceLocationId;

	public ServiceOrder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public java.math.BigDecimal getOrderStatusId() {
		return this.orderStatusId;
	}

	public void setOrderStatusId(java.math.BigDecimal orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public java.math.BigDecimal getOrderTypeId() {
		return this.orderTypeId;
	}

	public void setOrderTypeId(java.math.BigDecimal orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public java.math.BigDecimal getProductCatalogId() {
		return this.productCatalogId;
	}

	public void setProductCatalogId(java.math.BigDecimal productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public java.math.BigDecimal getServiceInstanceId() {
		return this.serviceInstanceId;
	}

	public void setServiceInstanceId(java.math.BigDecimal serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public java.math.BigDecimal getServiceLocationId() {
		return this.serviceLocationId;
	}

	public void setServiceLocationId(java.math.BigDecimal serviceLocationId) {
		this.serviceLocationId = serviceLocationId;
	}

}