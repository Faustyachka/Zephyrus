package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.model.UserRole;

public class OracleUserRoleDAO extends OracleDAO<UserRole> implements IUserRoleDAO{
	private final String TABLE_NAME = "USER_ROLES";
    private final String SQL_SELECT = "SELECT ID, ROLE_NAME " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ROLE_NAME = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (ROLE_NAME) " + 
                                      
                                      "VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ROLE_NAME = 2;
    
	public OracleUserRoleDAO(Connection connection)
			throws Exception {
		super(UserRole.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(UserRole record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_ROLE_NAME, record.getRoleName());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(UserRole record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_ROLE_NAME, record.getRoleName());    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(UserRole record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(UserRole item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setRoleName(rs.getString(COLUMN_ROLE_NAME));
		
		
	}

}