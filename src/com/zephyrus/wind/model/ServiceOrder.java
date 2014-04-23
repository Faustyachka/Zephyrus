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


	private OrderStatus orderStatus;

	private OrderType orderType;

	
	private ProductCatalog productCatalog;


	private ServiceInstance serviceInstance;

	
	private ServiceLocation serviceLocation;

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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	public ServiceInstance getServiceInstance() {
		return serviceInstance;
	}

	public void setServiceInstance(ServiceInstance serviceInstance) {
		this.serviceInstance = serviceInstance;
	}

	public ServiceLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ServiceLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	
	

}