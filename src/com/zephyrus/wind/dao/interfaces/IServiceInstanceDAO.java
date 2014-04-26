package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;																		// REVIEW: unused import
import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceInstance;
																									// REVIEW: documentation expected
public interface IServiceInstanceDAO extends IDAO<ServiceInstance> {

	ArrayList<ServiceInstance> getServiceInstancesByUserId(int id)
			throws Exception;

}
