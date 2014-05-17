package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;										
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * OracleUserDAO implementation of the IUserDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleUserDAO works with DB table USERS and User class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleUserDAO extends OracleDAO<User> implements IUserDAO{
	private static final String TABLE_NAME = "USERS";
	private static final String SQL_SELECT = 
			"SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, REGISTRATION_DATA, "
					+ " STATUS, ROLE_ID, ROWNUM AS ROW_NUM "
					+ " FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET FIRST_NAME= ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ?, "
			+ " REGISTRATION_DATA = ?, STATUS = ?, ROLE_ID = ? "
			+ " WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (FIRST_NAME, LAST_NAME, " + 
			"EMAIL, PASSWORD, REGISTRATION_DATA, STATUS, ROLE_ID) " +
			"VALUES (?,?,?,?,?,?,?) " + 
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + "WHERE ";

	/**
     * Constructor return OracleDAO instance for class User
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleUserDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(User.class, connection, daoFactory);
	}

	/**
	 * Method update User class instance using command update in the database.
	 * @param User class instance
	 */
	@Override
	public void update(User record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getFirstName());
		stmt.setString(2, record.getLastName());
		stmt.setString(3, record.getEmail());
		stmt.setString(4, record.getPassword());
		stmt.setDate(5, record.getRegistrationData());
		stmt.setInt(6, record.getStatus());
		stmt.setInt(7, record.getRole().getId());
		stmt.setLong(8, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant User class instance.
	 * @param inserting User class instance
	 * @return inserted User class instance
	 */
	@Override
	public User insert(User record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getFirstName());
		cs.setString(2, record.getLastName());
		cs.setString(3, record.getEmail());
		cs.setString(4, record.getPassword());
		cs.setDate(5, record.getRegistrationData());
		cs.setInt(6, record.getStatus());
		cs.setInt(7, record.getRole().getId());
		cs.registerOutParameter(8, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(8);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete User class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(User record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes User class instance and set values to its variables 
	 * @param class instance Cable
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(User item, ResultSet rs) throws Exception {
		item.setId(rs.getInt(1));
		item.setFirstName(rs.getString(2));
		item.setLastName(rs.getString(3));
		item.setEmail(rs.getString(4));
		item.setPassword(rs.getString(5));
		item.setRegistrationData(rs.getDate(6));
		item.setStatus(rs.getInt(7));
		UserRole role = daoFactory.getUserRoleDAO().findById(rs.getInt(8));
		item.setRole(role);
	}

	
	/**
   	 * Method gives text of SQL select query for table USERS
   	 *@return text of select query
   	 */
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	/**
   	 * Method gives text of SQL delete query for table USERS
   	 *@return text of delete query
   	 */
	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	/**
	 * Method find Users by Role ID
	 * @param int roleId Role ID
	 * @param int firstItem first row of select		
	 * @param int count count of rows											
	 * @return List of users for given role.										
	 */
	@Override
	public ArrayList<User> getUsersByRoleId(int roleId, int firstItem, int count) throws Exception {
		int lastItem = firstItem + count - 1;
		stmt = connection.prepareStatement("SELECT * FROM ( " + SQL_SELECT + " WHERE ROLE_ID = ?) "
				+ "WHERE ROW_NUM BETWEEN ? AND ?" );
		stmt.setInt(1, roleId);
		stmt.setInt(2, firstItem);
		stmt.setInt(3, lastItem);
		rs = stmt.executeQuery();
		ArrayList<User> users = fetchMultiResults(rs);
		rs.close();
		return users;
	}

	/**
	 * Method find count of Users by Role ID
	 * @param int roleId Role ID															
	 * @return count of users for given Role ID. If users don`t exist return 0.
	 */
	@Override
	public int getCountUsersForRoleId(int roleId) throws Exception {
		int result = 0;
		stmt = connection.prepareStatement("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE ROLE_ID = ? ");
		stmt.setInt(1, roleId);
		rs = stmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		return result;
	}

	/**
	 * Method find User by email
	 * @param String email - user email																
	 * @return user object
	 */
	@Override
	public User findByEmail(String email) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE EMAIL = ?");
		stmt.setString(1, email);
		rs = stmt.executeQuery();
		User user = fetchSingleResult(rs);
		rs.close();
		return user;
	}


	/**
	 * Method obtains email addresses of specified user group
	 * @param role group of users represented by Role
	 * @param firstItem index of the first record to return, starting from 1
	 * @param count number of records to return
	 * @return collection of Strings representing user emails
	 */	
	@Override
	public 	List<String> getGroupEmails(ROLE role, int firstItem, int count) throws SQLException{
		List<String> emails = new ArrayList<String>();
		int lastItem = firstItem + count - 1;
		stmt = connection.prepareStatement("SELECT EMAIL FROM ( " + SQL_SELECT + " WHERE ROLE_ID = ?) "
				+ "WHERE ROW_NUM BETWEEN ? AND ?" );
		stmt.setInt(1, role.getId());
		stmt.setInt(2, firstItem);
		stmt.setInt(3, lastItem);
		rs = stmt.executeQuery();
		while (rs.next()) {
			emails.add(rs.getString(1));
		}
		rs.close();
		return emails;
	}

}
