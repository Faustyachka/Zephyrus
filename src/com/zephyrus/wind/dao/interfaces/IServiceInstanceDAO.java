package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;																		// REVIEW: unused import
import java.util.ArrayList;

import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.User;
																									// REVIEW: documentation expected
public interface IServiceInstanceDAO extends IDAO<ServiceInstance> {

	
	/**
	 * Method finds Service Instances by User ID
	 * 
	 * @param User ID
	 * @return collection of existing Service Instances
	 * @author unknown
	 */
	ArrayList<ServiceInstance> getServiceInstancesByUserId(int id)
			throws Exception;

	
	/**
	 * Method finds Service Instances by User in all status expect DISCONNECTED
	 * 
	 * @param User
	 * @return ArrayList of existing Service Instances in all status expect DISCONNECTED
	 * @author Miroshnychenko Nataliya
	 */
	public ArrayList<ServiceInstance> getActiveServiceInstancesByUser(User user) throws Exception;

}
