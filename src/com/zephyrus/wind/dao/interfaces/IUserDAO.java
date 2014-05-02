package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;
// REVIEW: documentation expected
public interface IUserDAO extends IDAO<User> {

	/**
	 * Method find Users by Role ID
	 * 
	 * @param Role ID
	 * @return users collection
	 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
	 */
	public ArrayList<User> getUsersByRoleId(int roleId, int firstItem, int count) throws Exception;
	
	/**
	 * Method find User by email
	 * 
	 * @param email
	 * @return user object
	 * @author unknown
	 */
	User findByEmail(String email) throws Exception;
	
	/**
	 * Method finds emails by Role and output result using pagination
	 * 
	 * @param Role, first page Item and count Item
	 * @return emails collection
	 * @author Miroshnycjenko Nataliya
	 */	
	List<String> getGroupEmails(ROLE role, int firstItem, int count)  throws SQLException;

}
