package com.zephyrus.wind.reports.rowObjects;
/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		
public class ProfitabilityByMonthRow {
	private String providerLocation;
	private Long profit;

	public String getProviderLocation() {
		return providerLocation;
	}

	public void setProviderLocation(String providerLocation) {
		this.providerLocation = providerLocation;
	}

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

}
