package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the USER_ROLES database table.
 * 																								// REVIEW: author expected
 */

public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;


	private String roleName;

	public UserRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}