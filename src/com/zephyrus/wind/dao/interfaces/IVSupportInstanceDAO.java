package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.VSupportInstance;
																									// REVIEW: documentation expected
public interface IVSupportInstanceDAO extends IDAO<VSupportInstance>{
		ArrayList<VSupportInstance> getInstancesByUserId(int id) throws Exception;					// REVIEW: documentation expected, DAOException should be thrown
}
