package com.zephyrus.wind.reports.rows;
/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		
public class ProfitabilityByMonthRow implements IRow {
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
