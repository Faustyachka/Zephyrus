package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import com.zephyrus.wind.model.ServiceOrder;

public interface IServiceOrderDAO extends IDAO<ServiceOrder> {

	public ArrayList<ServiceOrder> getServiceOrdersByServiceInstanceId(int id)
			throws Exception;
	public ArrayList<ServiceOrder> getServiceOrdersByServiceLocationId(int id) throws Exception;


	ArrayList<ServiceOrder> getDisconnectSOByPeriod(Date startDate,
			Date endDate) throws Exception;

	ArrayList<ServiceOrder> getNewSOByPeriod(java.sql.Date startDate,
			java.sql.Date endDate) throws Exception;

}
