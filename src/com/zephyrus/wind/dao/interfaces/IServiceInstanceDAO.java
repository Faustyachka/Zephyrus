package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.User;

/**
* The interface enforces the operations needed to deal with OracleServiceInstanceDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IServiceInstanceDAO extends IDAO<ServiceInstance> {
	
	/**
	 * Method finds Service Instances by User ID
	 * @param int id  User id                                                                           
	 * @return List of existing Service Instances
	 */
	ArrayList<ServiceInstance> getServiceInstancesByUserId(int id)
			throws Exception;
	
	/**
	 * Method finds Service Instances by User in all status expect DISCONNECTED
	 * @param User instance																				
	 * @return ArrayList of existing Service Instances in all status expect DISCONNECTED
	 */
	public ArrayList<ServiceInstance> getActiveServiceInstancesByUser(User user) throws Exception;

}
