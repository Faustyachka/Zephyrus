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
	PASSWORD_CHANGED_MESSAGE(10), 
	ORDER_EMPTY_ERROR(11), 
	USER_EXIST_ERROR(12), 
	SERVICE_INSTANCE_ERROR(13), 
	SI_DISCONNECT_COMPLETED_MESSAGE(14), 
	PRODUCTS_ERROR(15), 
	PRODUCT_CATALOG_ERROR(16), 
	SI_MODIFY_COMPLETED_MESSAGE(17);
	
	int id;

	public int getId() {
		return id;
	}

	private MessageNumber(int id) {
		this.id = id;
	}
	
}
