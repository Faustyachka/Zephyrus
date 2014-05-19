package com.zephyrus.wind.model;

/**
 * The persistent class for the SERVICE_LOCATIONS database table.
 */
public class ServiceLocation {

	private Integer id;
	private String serviceLocationCoord;
	private String address;
	private User user;

	public ServiceLocation() { }

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