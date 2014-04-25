package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceOrder;

public interface IServiceOrderDAO extends IDAO<ServiceOrder> {

	public ArrayList<ServiceOrder> getServiceOrdersByServiceInstanceId(int id)
			throws Exception;
	public ArrayList<ServiceOrder> getServiceOrdersByServiceLocationId(int id) throws Exception;

}
