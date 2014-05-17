package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;	
import java.sql.Date;

import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

/**
* The interface enforces the operations needed to deal with OracleServiceOrderDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IServiceOrderDAO extends IDAO<ServiceOrder> {

	/**
	 * Method finds Service Orders object for Service Instance ID
	 * @param int id Service Instance ID														
	 * @return List of orders for given Service Instance											
	 */
	public ArrayList<ServiceOrder> getServiceOrdersByServiceInstanceId(int id)
			throws Exception;
	
	/**
	 * Method finds Service Orders object for Service Location ID
	 * @param int id Service Location ID														
	 * @return List of orders for given Service Location ID											
	 */
	public ArrayList<ServiceOrder> getServiceOrdersByServiceLocationId(int id) throws Exception;

	/**
	 * Method finds disconnected Service Orders object for period
	 * @param Date startDate start of period
	 * @param Date endDate end of period
	 * @return List of disconnected orders for period
	 */
	public ArrayList<ServiceOrder> getDisconnectSOByPeriod(Date startDate,
			Date endDate) throws Exception;

	/**
	 * Method finds new Service Orders object for period
	 * @param Date startDate start of period
	 * @param Date endDate end of period
	 * @return List of new orders for period
	 */
	public ArrayList<ServiceOrder> getNewSOByPeriod(java.sql.Date startDate,
			java.sql.Date endDate) throws Exception;
	
	/**
	 * Method finds creation Service Orders object for Service Instance
	 * @param ServiceInstance serviceInstance Service Instance															
	 * @return Service Orders on which basis the SI create 
	 */
	public ServiceOrder getSICreateOrder(ServiceInstance serviceInstance) throws Exception;
	
	/**
	 * Method finds Service Orders for user
	 * @param User instance												
	 * @return List of orders for given user
	 */
	public ArrayList<ServiceOrder> getOrdersByUser(User user)  throws Exception;

}
