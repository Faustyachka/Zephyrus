package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;

public class OracleCableDAO extends OracleDAO<Cable> implements ICableDAO {
	
	private static final String TABLE_NAME = "CABLES";
    private static final String SQL_SELECT = "SELECT ID, PORT_ID, SERVICE_LOCATION_ID " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_ID = ?, SERVICE_LOCATION_ID = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
											"(PORT_ID, SERVICE_LOCATION_ID) VALUES(?,?)" +
											"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_PORT_ID = 2;
    private static final int COLUMN_SERVICE_LOCATION_ID = 3;    

	public OracleCableDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Cable.class, connection, daoFactory);
	}

	@Override
	public void update(Cable record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_PORT_ID, record.getPort().getId());
    	stmt.setInt(COLUMN_SERVICE_LOCATION_ID, record.getServiceLocation().getId());    	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public Cable insert(Cable record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getPort().getId());
    	cs.setInt(2, record.getServiceLocation().getId());    
    	cs.registerOutParameter(3, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(3);
		return findByRowId(rowId);
	}

	@Override
	public int remove(Cable record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Cable item, ResultSet rs) throws SQLException, Exception {
		item.setId(rs.getInt(COLUMN_ID));
		IPortDAO portDAO = daoFactory.getPortDAO();
		Port port = portDAO.findById(rs.getInt(COLUMN_PORT_ID));
		item.setPort(port);
		IServiceLocationDAO serviceLocationDAO = daoFactory.getServiceLocationDAO();
		ServiceLocation serviceLocation = serviceLocationDAO.findById(rs.getInt(COLUMN_SERVICE_LOCATION_ID));
		item.getServiceLocation();
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
