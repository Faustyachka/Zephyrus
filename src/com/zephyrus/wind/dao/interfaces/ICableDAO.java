package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Cable;

/**
* The interface enforces the operations needed to deal with OracleCableDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface ICableDAO extends IDAO<Cable> {

	/**
	 * Method checks existing connection to port
	 * @param int port ID                                                      
	 * @return true if connection exist, otherwise false
	 */
	boolean existConnectToPort(int port_id) throws SQLException;

	/**
	 * Method finds Cable object for Service Location ID                        
	 * @param Service Location ID
	 * @return existing Cable, otherwise null
	 */
	Cable findCableFromServLocID(int serviceLocationID) throws Exception;

}
