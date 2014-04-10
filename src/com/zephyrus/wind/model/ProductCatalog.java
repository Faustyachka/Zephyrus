package com.zephyrus.wind.model;

public class ProductCatalog {
	private int id;
	private String productName;
	private double price;
	private ProviderLocation providerLocation;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ProviderLocation getProviderLocation() {
		return providerLocation;
	}
	public void setProviderLocation(ProviderLocation providerLocation) {
		this.providerLocation = providerLocation;
	}
	
	

}
