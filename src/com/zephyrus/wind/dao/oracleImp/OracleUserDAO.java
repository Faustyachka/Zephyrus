package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IUsersDAO;
import com.zephyrus.wind.model.User;

public class OracleUserDAO extends OracleDAO<User> implements IUsersDAO {

	private final String TABLE_NAME = "MISTERDAN.USERS";
    private final String SQL_SELECT = "SELECT USER_ID, FIRST_NAME, LAST_NAME, " + 
                                      "MIDDLE_NAME, PASSWORD, USERNAME FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET FIRST_NAME = ?, LAST_NAME = ?, " + 
                                      "MIDDLE_NAME = ?, PASSWORD = ?, USERNAME =? WHERE " + 
                                      " USER_ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (FIRST_NAME, LAST_NAME, " + 
                                      "MIDDLE_NAME, PASSWORD, USERNAME) " +
                                      "VALUES (?,?,?,?,?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_USER_ID = 1;
    private static final int COLUMN_FIRST_NAME = 2;
    private static final int COLUMN_LAST_NAME = 3;
    private static final int COLUMN_MIDDLE_NAME = 4;
    private static final int COLUMN_PASSWORD = 5;
    private static final int COLUMN_USERNAME = 6;
    
    public OracleUserDAO(Connection connection) throws Exception{
        super(User.class, connection);
        select = SQL_SELECT;
        remove = SQL_REMOVE;
    }

    @Override
    protected void fillItem(User item, ResultSet rs) throws SQLException {
    	item.setUserId(rs.getLong(COLUMN_USER_ID));
    	item.setFirstName(rs.getString(COLUMN_FIRST_NAME));
    	item.setLastName(rs.getString(COLUMN_LAST_NAME));
    	item.setMiddleName(rs.getString(COLUMN_MIDDLE_NAME));
    	item.setPassword(rs.getString(COLUMN_PASSWORD));
    	item.setUsername(rs.getString(COLUMN_USERNAME));
    }
    
    
    public void update(User record) throws Exception {
    	stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_FIRST_NAME, record.getFirstName());
    	stmt.setString(COLUMN_LAST_NAME, record.getLastName());
    	stmt.setString(COLUMN_MIDDLE_NAME, record.getMiddleName());
    	stmt.setString(COLUMN_PASSWORD, record.getPassword());
    	stmt.setString(COLUMN_USERNAME, record.getUsername());
    	stmt.setLong(COLUMN_USER_ID, record.getUserId());
        stmt.executeUpdate();
    }

    public int insert(User record) throws Exception {
    	stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_FIRST_NAME, record.getFirstName());
    	stmt.setString(COLUMN_LAST_NAME, record.getLastName());
    	stmt.setString(COLUMN_MIDDLE_NAME, record.getMiddleName());
    	stmt.setString(COLUMN_PASSWORD, record.getPassword());
    	stmt.setString(COLUMN_USERNAME, record.getUsername());
        return stmt.executeUpdate();
    }

    public int remove(User record) throws Exception {
        return removeById((int)record.getUserId());
    }

	@Override
	public User findByUsername(String username) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USERNAME = ?");
        stmt.setString(1, username);
        rs = stmt.executeQuery();
        return fetchSingleResult(rs);
	}
}
