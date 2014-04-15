package com.zephyrus.wind.model;

import java.io.Serializable;
import java.util.Date;



/**
 * The persistent class for the SERVICE_INSTANCES database table.
 * 
 */

public class ServiceInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;


	private java.math.BigDecimal circuitId;

	private java.math.BigDecimal productCatalogId;
	
	private Date startDate;

	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	private java.math.BigDecimal servInstanceStatusId;


	private java.math.BigDecimal userId;

	public ServiceInstance() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.math.BigDecimal getCircuitId() {
		return this.circuitId;
	}

	public void setCircuitId(java.math.BigDecimal circuitId) {
		this.circuitId = circuitId;
	}

	public java.math.BigDecimal getProductCatalogId() {
		return this.productCatalogId;
	}

	public void setProductCatalogId(java.math.BigDecimal productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public java.math.BigDecimal getServInstanceStatusId() {
		return this.servInstanceStatusId;
	}

	public void setServInstanceStatusId(java.math.BigDecimal servInstanceStatusId) {
		this.servInstanceStatusId = servInstanceStatusId;
	}

	public java.math.BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(java.math.BigDecimal userId) {
		this.userId = userId;
	}

}