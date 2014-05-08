package com.zephyrus.wind.reports.rows;
/**
 * This class needed to save data for reports row
 * @author Kostya Trukhan
 */		
public class RouterUtilRow implements IRow {
	private String routerSN;
	private int capacity;
	private double routerUtil;

	public String getRouterSN() {
		return routerSN;
	}

	public void setRouterSN(String routerSN) {
		this.routerSN = routerSN;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getRouterUtil() {
		return routerUtil;
	}

	public void setRouterUtil(double routerUtil) {
		this.routerUtil = routerUtil;
	}
}
