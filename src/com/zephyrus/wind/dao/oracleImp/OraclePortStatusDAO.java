package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;                     										  	//REVIEW: unused imports
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.PortStatus;
																							//REVIEW: documentation expected
public class OraclePortStatusDAO extends OracleDAO<PortStatus> implements IPortStatusDAO {
	
	private static final String TABLE_NAME = "PORT_STATUS";
    private static final String SQL_SELECT = "SELECT ID, PORT_STATUS_VALUE, ROWNUM AS ROW_NUM " + 
                                      "FROM " + 												//REVIEW: wrong formatting
                                       TABLE_NAME + " ";                       
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
												"(PORT_STATUS_VALUE) VALUES(?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";

    public OraclePortStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(PortStatus.class, connection, daoFactory);
	}
																									//REVIEW: documentation expected
	@Override
	public void update(PortStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getPortStatusValue());  	
    	stmt.setLong(2, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}
																									//REVIEW: documentation expected
	@Override
	public PortStatus insert(PortStatus record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getPortStatusValue());
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
	}
																										//REVIEW: documentation expected
	@Override
	public int remove(PortStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(PortStatus item, ResultSet rs) throws SQLException,
			Exception {
		item.setId(rs.getInt(1));
    	item.setPortStatusValue(rs.getString(2));
		
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
