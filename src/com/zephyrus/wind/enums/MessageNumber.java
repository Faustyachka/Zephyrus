/**
 * 
 */
package com.zephyrus.wind.enums;

/**
 * This enum contains numbers of all existing errors in system
 * @author Alexandra Beskorovaynaya
 */
public enum MessageNumber {
	LOGIN_ADMIN_ERROR(1),
	LOGIN_ERROR(2),
	LOGIN_INSTALL_PROVISION_ERROR(3),
	TASK_SELECTING_ERROR(4),
	LOGIN_INSTALL_ERROR(5),
	LOGIN_PROVISION_ERROR(6),
	TASK_COMPLETED_MESSAGE(7),
	LOGIN_SUPPORT_ERROR(8),
	USER_ID_ERROR(9),
	PASSWORD_CHANGED_MESSAGE(10);
	
	int id;

	public int getId() {
		return id;
	}

	private MessageNumber(int id) {
		this.id = id;
	}
	
}
