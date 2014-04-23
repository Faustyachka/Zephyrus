package com.zephyrus.wind.model;

import java.io.Serializable;
import java.util.Date;



/**
 * The persistent class for the SERVICE_INSTANCES database table.
 * 
 */

public class ServiceInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer circuitId;

	private Integer productCatalogId;
	
	private Date startDate;

	private Integer servInstanceStatusId;

	private Integer userId;

	public ServiceInstance() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCircuitId() {
		return circuitId;
	}

	public void setCircuitId(Integer circuitId) {
		this.circuitId = circuitId;
	}

	public Integer getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(Integer productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getServInstanceStatusId() {
		return servInstanceStatusId;
	}

	public void setServInstanceStatusId(Integer servInstanceStatusId) {
		this.servInstanceStatusId = servInstanceStatusId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}