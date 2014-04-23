package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.model.UserRole;

public class OracleUserRoleDAO extends OracleDAO<UserRole> implements IUserRoleDAO{
	private static final String TABLE_NAME = "MISTERDAN.USER_ROLES";
    private final String SQL_SELECT = "SELECT ID, ROLE_NAME " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ROLE_NAME = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (ROLE_NAME) " +                                       
                                      "VALUES (?) " + "RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ROLE_NAME = 2;
    
	public OracleUserRoleDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(UserRole.class, connection, daoFactory);
	}

	@Override
	public void update(UserRole record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_ROLE_NAME, record.getRoleName());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public UserRole insert(UserRole record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	stmt.setString(1, record.getRoleName());    	
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
		return findByRowId(rowId);
	}

	@Override
	public int remove(UserRole record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(UserRole item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setRoleName(rs.getString(COLUMN_ROLE_NAME));
		
		
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
