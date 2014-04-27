package com.zephyrus.wind.reports.rowObjects;
/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		
public class MostProfitableRouterRow {
	private String routerSN;
	private Long profit;
	public String getRouterSN() {
		return routerSN;
	}
	public void setRouterSN(String routerSN) {
		this.routerSN = routerSN;
	}
	public Long getProfit() {
		return profit;
	}
	public void setProfit(Long profit) {
		this.profit = profit;
	}
}
