package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The persistent class for the PRODUCT_CATALOG database table.
 * 
 */

public class ProductCatalog implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	private Integer price;

	private String productName;

	
	private Integer providerLocId;

	private Integer serviceTypeId;

	public ProductCatalog() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProviderLocId() {
		return providerLocId;
	}

	public void setProviderLocId(Integer providerLocId) {
		this.providerLocId = providerLocId;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	

}