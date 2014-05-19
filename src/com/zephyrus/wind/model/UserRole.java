package com.zephyrus.wind.model;

/**
 * The persistent class for the USER_ROLES database table.
 */
public class UserRole {

	private Integer id;
	private String roleName;

	public UserRole() { }

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