package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.model.User;

public class OracleUserDAO extends OracleDAO<User> implements IUserDAO{
	private static final String TABLE_NAME = "USERS";
    private static final String SQL_SELECT = "SELECT ID, FIRST_NAME, LAST_NAME, " + 
                                      "EMAIL, PASSWORD, REGISTRATION_DATA, STATUS, ROLE_ID FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET FIRST_NAME= ?, LAST_NAME = ?, " + 
                                      "EMAIL = ?, PASSWORD = ?, REGISTRATION_DATA = ?, STATUS = ?, ROLE_ID = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (FIRST_NAME, LAST_NAME, " + 
                                      "EMAIL, PASSWORD, REGISTRATION_DATA, STATUS, ROLE_ID) " +
                                      "VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_FIRST_NAME = 2;
    private static final int COLUMN_LAST_NAME= 3;
    private static final int COLUMN_EMAIL = 4;
    private static final int COLUMN_PASSWORD = 5;
    private static final int COLUMN_REGISTRATION_DATA = 5;
    private static final int COLUMN_STATUS = 5;
    private static final int COLUMN_ROLE_ID = 5;

	public OracleUserDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(User.class, connection, daoFactory);
	}

	@Override
	public void update(User record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_FIRST_NAME, record.getFirstName());
    	stmt.setString(COLUMN_LAST_NAME, record.getLastName());
    	stmt.setString(COLUMN_EMAIL, record.getEmail());
    	stmt.setString(COLUMN_PASSWORD, record.getPassword());
    	stmt.setDate(COLUMN_REGISTRATION_DATA, (java.sql.Date)record.getRegistrationData());
    	stmt.setBigDecimal(COLUMN_STATUS, record.getStatus());
    	stmt.setBigDecimal(COLUMN_ROLE_ID, record.getRoleId());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(User record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_FIRST_NAME, record.getFirstName());
    	stmt.setString(COLUMN_LAST_NAME, record.getLastName());
    	stmt.setString(COLUMN_EMAIL, record.getEmail());
    	stmt.setString(COLUMN_PASSWORD, record.getPassword());
    	stmt.setDate(COLUMN_REGISTRATION_DATA, (java.sql.Date)record.getRegistrationData());
    	stmt.setBigDecimal(COLUMN_STATUS, record.getStatus());
    	stmt.setBigDecimal(COLUMN_ROLE_ID, record.getRoleId());
		return stmt.executeUpdate();
	}

	@Override
	public int remove(User record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(User item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
		item.setFirstName(rs.getString(COLUMN_FIRST_NAME));
		item.setLastName(rs.getString(COLUMN_LAST_NAME));
		item.setEmail(rs.getString(COLUMN_EMAIL));
		item.setPassword(rs.getString(COLUMN_PASSWORD));
		item.setRegistrationData(rs.getDate(COLUMN_REGISTRATION_DATA));
		item.setStatus(rs.getBigDecimal(COLUMN_STATUS));
		item.setRoleId(rs.getBigDecimal(COLUMN_ROLE_ID));
		
	}
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

}
