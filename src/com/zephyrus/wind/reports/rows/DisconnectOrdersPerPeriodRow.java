package com.zephyrus.wind.reports.rows;

/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		

public class DisconnectOrdersPerPeriodRow implements IRow {
	private String username;
	private int orderID;
	private String orderStatus;
	private String productName;
	private String providerLocation;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProviderLocation() {
		return providerLocation;
	}

	public void setProviderLocation(String providerLocation) {
		this.providerLocation = providerLocation;
	}
}
