package com.zephyrus.wind.model;

import java.io.Serializable;
import java.math.BigDecimal;														// REVIEW: unused import
																					// REVIEW: front documentation formatting
/**
 * The persistent class for the PRODUCT_CATALOG and SERVICE_TYPE database table (PRODUCT_CATALOG.ID, PRODUCT_CATALOG.PRICE, SERVICE_TYPE.NAME)
 */																					// REVIEW: author expected

public class ProductCatalogService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private Integer id;

	private Integer price;

	private String serviceName;
	
	public ProductCatalogService(){
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	

}
