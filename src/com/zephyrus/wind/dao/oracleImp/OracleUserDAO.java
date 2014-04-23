package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.model.User;

public class OracleUserDAO extends OracleDAO<User> implements IUserDAO{
	private static final String TABLE_NAME = "MISTERDAN.USERS";
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
    private static final int COLUMN_REGISTRATION_DATA = 6;
    private static final int COLUMN_STATUS = 7;
    private static final int COLUMN_ROLE_ID = 8;

	public OracleUserDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(User.class, connection, daoFactory);
	}

	@Override
	public void update(User record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getFirstName());
    	stmt.setString(2, record.getLastName());
    	stmt.setString(3, record.getEmail());
    	stmt.setString(4, record.getPassword());
    	stmt.setDate(5, record.getRegistrationData());
    	stmt.setBigDecimal(6, record.getStatus());
    	stmt.setBigDecimal(7, record.getRoleId());
    	stmt.setLong(8, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(User record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(1, record.getFirstName());
    	stmt.setString(2, record.getLastName());
    	stmt.setString(3, record.getEmail());
    	stmt.setString(4, record.getPassword());
    	stmt.setDate(5, record.getRegistrationData());
    	stmt.setBigDecimal(6, record.getStatus());
    	stmt.setBigDecimal(7, record.getRoleId());
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
	

	@Override
	public ArrayList<User> getUsersByRoleId(int roleId) throws SQLException, InstantiationException, IllegalAccessException {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE ROLE_ID = ?");
		stmt.setInt(1, roleId);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

	@Override
	public User findByEmail(String email) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE EMAIL = ?");
		stmt.setString(1, email);
		rs = stmt.executeQuery();
		return fetchSingleResult(rs);
	}

}
