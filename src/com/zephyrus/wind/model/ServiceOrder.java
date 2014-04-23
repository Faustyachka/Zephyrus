package com.zephyrus.wind.model;

import java.io.Serializable;

import java.util.Date;


/**
 * The persistent class for the SERVICE_ORDERS database table.
 * 
 */

public class ServiceOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date orderDate;


	private Integer orderStatusId;

	private Integer orderTypeId;

	
	private Integer productCatalogId;


	private Integer serviceInstanceId;

	
	private Integer serviceLocationId;

	public ServiceOrder() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Integer getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(Integer productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public Integer getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(Integer serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public Integer getServiceLocationId() {
		return serviceLocationId;
	}

	public void setServiceLocationId(Integer serviceLocationId) {
		this.serviceLocationId = serviceLocationId;
	}

	

}