package com.zephyrus.wind.reports.rows;
/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		
public class MostProfitableRouterRow implements IRow {
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
