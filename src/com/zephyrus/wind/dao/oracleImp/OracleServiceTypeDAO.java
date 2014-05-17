package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.model.ServiceType;
																									//REVIEW: documentation expected
public class OracleServiceTypeDAO extends OracleDAO<ServiceType> implements IServiceTypeDAO {       //REVIEW: long line
	private static final String TABLE_NAME = "SERVICE_TYPE";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_TYPE_VALUE, ROWNUM AS ROW_NUM " + 
                                      "FROM " + 													//REVIEW: wrong formatting
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_TYPE_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_TYPE_VALUE) " +                                     
                                      "VALUES (?)" + " RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    
    private static final int COLUMN_ID = 1;													//REVIEW: unused constants
    private static final int COLUMN_SERVICE_TYPE = 2;

	public OracleServiceTypeDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceType.class, connection, daoFactory);
	}
																							//REVIEW: documentation expected
	@Override
	public void update(ServiceType record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getServiceType());  	
    	stmt.setLong(2, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}
																							//REVIEW: documentation expected
	@Override
	public ServiceType insert(ServiceType record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getServiceType());    	
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
	}
																								//REVIEW: documentation expected
	@Override
	public int remove(ServiceType record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceType item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
    	item.setServiceType(rs.getString(2));
		
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
