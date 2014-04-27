package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;																		// REVIEW: unused import


/**
 * The persistent class for the PRODUCT_CATALOG database table.
 * 																									// REVIEW: author expected
 */

public class ProductCatalog implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;

	private Integer price;

	private ProviderLocation providerLoc;

	private ServiceType serviceType;

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

	public ProviderLocation getProviderLoc() {
		return providerLoc;
	}

	public void setProviderLoc(ProviderLocation providerLoc) {
		this.providerLoc = providerLoc;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	
	

}