package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.Task;
																							// REVIEW: documentation expected
public interface IPortDAO extends IDAO<Port> {

	int findFreePortID() throws Exception;

	Port findPortFromTaskID(Task task) throws Exception;

}
