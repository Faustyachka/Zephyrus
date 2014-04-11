package com.zephyrus.wind.dao.interfaces;

import com.zephyrus.wind.model.User;

public interface IUsersDAO extends IDAO<User> {
	User findByUsername(String username) throws Exception;
}
