package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.Task;
																							// REVIEW: documentation expected
public interface IPortDAO extends IDAO<Port> {
	
	/**
	 * Method for searching ID of fist free port (without cable)
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO.getCableDAO
	 * @return ID of first free port or 0 if it doesn't exist
	 * @author Miroshnychenko Nataliya
	 */
	int findFreePortID() throws Exception;

	Port findPortFromTaskID(Task task) throws Exception;

}
