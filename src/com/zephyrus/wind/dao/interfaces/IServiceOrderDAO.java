package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;	
import java.sql.Date;

import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
																									// REVIEW: documentation expected, DAOException should be thrown
public interface IServiceOrderDAO extends IDAO<ServiceOrder> {

	public ArrayList<ServiceOrder> getServiceOrdersByServiceInstanceId(int id)
			throws Exception;
	public ArrayList<ServiceOrder> getServiceOrdersByServiceLocationId(int id) throws Exception;


	public ArrayList<ServiceOrder> getDisconnectSOByPeriod(Date startDate,
			Date endDate) throws Exception;

	public ArrayList<ServiceOrder> getNewSOByPeriod(java.sql.Date startDate,
			java.sql.Date endDate) throws Exception;
	
	/**
	 * Method finds creation Service Orders instances for Service Instance
	 * 
	 * @param Service Instance
	 * @return Service Orders on which basis the SI create 
	 */
	public ServiceOrder getSICreateOrder(ServiceInstance serviceInstance) throws Exception;
	
	/**
	 * Method finds Service Orders for user
	 * 
	 * @param User
	 * @return collection of Service Orders 
	 */
	public ArrayList<ServiceOrder> getOrdersByUser(User user)  throws Exception;

}
