package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.User;

public interface IUserDAO extends IDAO<User> {

		 ArrayList<User> getUsersByRoleId(int roleId) throws Exception;

}
