package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.model.UserRole;
																								//REVIEW: documentation expected
public class OracleUserRoleDAO extends OracleDAO<UserRole> implements IUserRoleDAO{				
	private static final String TABLE_NAME = "USER_ROLES";
    private final String SQL_SELECT = "SELECT ID, ROLE_NAME, ROWNUM AS ROW_NUM  " + 
                                      "FROM " + 												//REVIEW: wrong formatting
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ROLE_NAME = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (ROLE_NAME) " +                                       
                                      "VALUES (?) " + "RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    
    
	public OracleUserRoleDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(UserRole.class, connection, daoFactory);
	}
																						//REVIEW: documentation expected
	@Override
	public void update(UserRole record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getRoleName());  	
    	stmt.setLong(2, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}
																						//REVIEW: documentation expected
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
																						//REVIEW: documentation expected
	@Override
	public int remove(UserRole record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(UserRole item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
    	item.setRoleName(rs.getString(2));
		
		
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
