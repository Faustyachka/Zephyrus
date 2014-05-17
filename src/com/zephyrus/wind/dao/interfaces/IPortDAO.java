package com.zephyrus.wind.dao.interfaces;

import com.zephyrus.wind.model.Port;

/**
* The interface enforces the operations needed to deal with OraclePortDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IPortDAO extends IDAO<Port> {
	
	/**
	 * Method for searching ID of fist free port (without cable)
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO.getCableDAO
	 * @return ID of first free port or 0 if it doesn't exist
	 */
	int findFreePortID() throws Exception;

}
