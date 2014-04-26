package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.User;
																								// REVIEW: documentation expected
public interface IUserDAO extends IDAO<User> {

		 ArrayList<User> getUsersByRoleId(int roleId) throws Exception;							// REVIEW: DAOException should be thrown
		 User findByEmail(String email) throws Exception;

}
