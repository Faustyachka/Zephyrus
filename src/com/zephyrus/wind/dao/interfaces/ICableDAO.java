package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;

import com.zephyrus.wind.model.Cable;
																								// REVIEW: documentation expected
public interface ICableDAO extends IDAO<Cable> {

	boolean findPortID(int port_id) throws SQLException;

}
