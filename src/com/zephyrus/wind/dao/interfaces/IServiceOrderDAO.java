package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceOrder;

public interface IServiceOrderDAO extends IDAO<ServiceOrder> {

	ArrayList<ServiceOrder> getServiceOrdersByUserId(int id)
			throws Exception;

}
