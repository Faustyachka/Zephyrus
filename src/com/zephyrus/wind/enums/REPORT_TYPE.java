package com.zephyrus.wind.enums;

public enum REPORT_TYPE {
	DISCONNECT_ORDERS_PER_PERIOD("Disconnect orders per period", 1),
	MOST_PROFITABLE_ROUTER("Most profitable router", 2),
	NEW_ORDERS_PER_PERIOD("New orders per period", 3),
	PROFITABILITY_BY_MONTH("Profitability by month", 4),
	ROUTER_UTILIZATION("Router utilization and capacity", 5);
	
	String name;
	int id;
	
	REPORT_TYPE(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
}
