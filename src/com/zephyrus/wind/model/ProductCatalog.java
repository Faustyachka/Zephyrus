package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The persistent class for the PRODUCT_CATALOG database table.
 * 
 */

public class ProductCatalog implements Serializable {
	private static final long serialVersionUID = 1L;


	private long id;

	private BigDecimal price;

	private String productName;

	
	private BigDecimal providerLocId;

	private BigDecimal serviceTypeId;

	public ProductCatalog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProviderLocId() {
		return this.providerLocId;
	}

	public void setProviderLocId(BigDecimal providerLocId) {
		this.providerLocId = providerLocId;
	}

	public BigDecimal getServiceTypeId() {
		return this.serviceTypeId;
	}

	public void setServiceTypeId(BigDecimal serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

}