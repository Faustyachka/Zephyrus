package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;

public interface IServiceLocationDAO extends IDAO<ServiceLocation> {

	public ArrayList<ServiceLocation> getServiceLocationsByUserId(int id) throws Exception;
}
