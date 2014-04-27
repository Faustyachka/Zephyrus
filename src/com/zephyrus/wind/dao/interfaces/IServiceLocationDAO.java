package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;													// REVIEW: unused import
																								// REVIEW: documentation expected
public interface IServiceLocationDAO extends IDAO<ServiceLocation> {

	public ArrayList<ServiceLocation> getServiceLocationsByUserId(int id) throws Exception;
}
