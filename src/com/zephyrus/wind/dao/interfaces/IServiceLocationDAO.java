package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;
import com.zephyrus.wind.model.ServiceLocation;

/**
* The interface enforces the operations needed to deal with OracleServiceLocationDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IServiceLocationDAO extends IDAO<ServiceLocation> {

	public ArrayList<ServiceLocation> getServiceLocationsByUserId(int id) throws Exception;
}
