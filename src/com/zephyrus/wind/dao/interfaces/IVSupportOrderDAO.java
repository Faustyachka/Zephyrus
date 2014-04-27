package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.VSupportOrder;
																								// REVIEW: documentation expected
public interface IVSupportOrderDAO extends IDAO<VSupportOrder> {
		ArrayList<VSupportOrder>getOrdersByUserId(int id) throws Exception;						// REVIEW: DAOExceptions should be thrown
}
