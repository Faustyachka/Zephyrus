package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the USER_ROLES database table.
 * 
 */

public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;


	private String roleName;

	public UserRole() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}