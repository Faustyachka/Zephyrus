package com.zephyrus.wind.model;

import java.io.Serializable;

import java.sql.Date;


/**
 * The persistent class for the " V_SUPPORT_INSTANCE" database table.
 * 																									// REVIEW: author expected
 */

public class VSupportInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer price;


	private String service;


	private ServiceInstance si;



	private Date siStardDate;


	private String siStatus;


	private User user;

	public VSupportInstance() {
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}



	public Date getSiStardDate() {
		return this.siStardDate;
	}

	public void setSiStardDate(Date siStardDate) {
		this.siStardDate = siStardDate;
	}

	public String getSiStatus() {
		return this.siStatus;
	}

	public void setSiStatus(String siStatus) {
		this.siStatus = siStatus;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}