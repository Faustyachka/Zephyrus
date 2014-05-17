package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.model.UserRole;

/**
 * OracleUserRoleDAO implementation of the IUserRoleDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleUserRoleDAO works with DB table USER_ROLES and UserRole class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleUserRoleDAO extends OracleDAO<UserRole> implements IUserRoleDAO{				
	private static final String TABLE_NAME = "USER_ROLES";
	private final String SQL_SELECT = 
			"SELECT ID, ROLE_NAME, ROWNUM AS ROW_NUM  " + 
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET ROLE_NAME = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (ROLE_NAME) " +                                       
			"VALUES (?) " + "RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";


	/**
     * Constructor return OracleDAO instance for class UserRole
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleUserRoleDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(UserRole.class, connection, daoFactory);
	}

	/**
	 * Method update UserRole class instance using command update in the database.
	 * @param UserRole class instance
	 */
	@Override
	public void update(UserRole record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getRoleName());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant UserRole class instance.
	 * @param inserting UserRole class instance
	 * @return inserted UserRole class instance
	 */
	@Override
	public UserRole insert(UserRole record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		stmt.setString(1, record.getRoleName());    	
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(2);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete UserRole class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(UserRole record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes UserRole class instance and set values to its variables 
	 * @param class instance UserRole
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(UserRole item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setRoleName(rs.getString(2));
	}

	/**
   	 * Method gives text of SQL select query for table USER_ROLES
   	 *@return text of select query
   	 */
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}
	
	/**
   	 * Method gives text of SQL delete query for table USER_ROLES
   	 *@return text of delete query
   	 */
	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

}
