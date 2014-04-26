package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the SERVICE_LOCATIONS database table.
 * 																								// REVIEW: author expected
 */

public class ServiceLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String serviceLocationCoord;
	
	private String address;
	
	private User user;

	public ServiceLocation() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getServiceLocationCoord() {
		return serviceLocationCoord;
	}


	public void setServiceLocationCoord(String serviceLocationCoord) {
		this.serviceLocationCoord = serviceLocationCoord;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	

}