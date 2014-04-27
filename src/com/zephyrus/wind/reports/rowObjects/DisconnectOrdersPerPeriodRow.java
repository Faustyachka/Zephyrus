package com.zephyrus.wind.reports.rowObjects;
import java.sql.Date;

/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		

public class DisconnectOrdersPerPeriodRow {
	private String username;
	private String orderID;
	private String orderStatus;
	private String productName;
	private String providerLocation;
	private Date startPeriod;
	private Date endPeriod;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
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

	public Date getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Date startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}
}
