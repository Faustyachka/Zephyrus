package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Cable;

/**
* The interface enforces the operations needed to deal with OracleCableDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface ICableDAO extends IDAO<Cable> {

	boolean existConnectToPort(int port_id) throws SQLException;

	Cable findCableFromServLocID(int serviceLocationID) throws Exception;

}
