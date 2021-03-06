package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;

/**
* The interface enforces the operations needed to deal with OracleUserDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IUserDAO extends IDAO<User> {

	/**
	 * Method find Users by Role ID
	 * @param int roleId Role ID
	 * @param int firstItem first row of select		
	 * @param int count count of rows											
	 * @return List of users for given role.										
	 */
	public ArrayList<User> getUsersByRoleId(int roleId, int firstItem, int count) throws Exception;
	
	/**
	 * Method find User by email
	 * @param String email - user email																
	 * @return user object
	 */
	User findByEmail(String email) throws Exception;
	
	/**
	 * Method obtains email addresses of specified user group
	 * @param role group of users represented by Role
	 * @param firstItem index of the first record to return, starting from 1
	 * @param count number of records to return
	 * @return collection of Strings representing user emails
	 */	
	List<String> getGroupEmails(ROLE role, int firstItem, int count)  throws SQLException;

	/**
	 * Method find count of Users by Role ID
	 * @param int roleId Role ID															
	 * @return count of users for given Role ID. If users don`t exist return 0.
	 */
	int getCountUsersForRoleId(int roleId) throws Exception;

}
