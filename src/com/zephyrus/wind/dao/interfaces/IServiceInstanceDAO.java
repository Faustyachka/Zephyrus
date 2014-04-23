package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceInstance;

public interface IServiceInstanceDAO extends IDAO<ServiceInstance> {

	ArrayList<ServiceInstance> getServiceInstancesByUserId(int id)
			throws SQLException, InstantiationException, IllegalAccessException;

}
