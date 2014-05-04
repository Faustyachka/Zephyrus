package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;																		// REVIEW: unused import
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


	ArrayList<ServiceOrder> getDisconnectSOByPeriod(Date startDate,
			Date endDate) throws Exception;

	ArrayList<ServiceOrder> getNewSOByPeriod(java.sql.Date startDate,
			java.sql.Date endDate) throws Exception;
	
	/**
	 * Method finds creation Service Orders object for Service Instance
	 * 
	 * @param Service Instance
	 * @return Service Orders on which basis the SI create 
	 * @author Mirosjnychenko Nataliya
	 */
	ServiceOrder getSICreateOrder(ServiceInstance serviceInstance) throws Exception;

}
