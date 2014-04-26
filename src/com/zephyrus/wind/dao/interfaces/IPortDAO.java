package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Port;
																							// REVIEW: documentation expected
public interface IPortDAO extends IDAO<Port> {

	int findFreePort() throws Exception;

	Port findByDevPortID(int devId, int portId) throws Exception;

}
