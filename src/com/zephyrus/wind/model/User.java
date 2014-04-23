package com.zephyrus.wind.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.Date;


/**
 * The persistent class for the USERS database table.
 * 
 */

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;


	private String firstName;

	private String lastName;

	private String password;


	private Date registrationData;


	private Integer roleId;

	private Integer status;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationData() {
		return this.registrationData;
	}

	public void setRegistrationData(Date registrationData) {
		this.registrationData = registrationData;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}