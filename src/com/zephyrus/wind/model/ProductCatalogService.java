package com.zephyrus.wind.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the PRODUCT_CATALOG and SERVICE_TYPE database table (PRODUCT_CATALOG.ID, PRODUCT_CATALOG.PRICE, SERVICE_TYPE.NAME)
 */

public class ProductCatalogService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private long id;

	private BigDecimal price;

	private String serviceName;
	
	public ProductCatalogService(){
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	

}
