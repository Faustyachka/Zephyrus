package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.Task;
																								// REVIEW: documentation expected
public interface ICableDAO extends IDAO<Cable> {

	boolean existConnectToPort(int port_id) throws SQLException;

	Cable findCableFromServLoc(int serviceLocationID) throws Exception;

	Cable findCableByTask(Task task) throws Exception;

}
