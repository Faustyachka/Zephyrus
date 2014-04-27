package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.Port;

public class OracleCircuitDAO extends OracleDAO<Circuit> implements ICircuitDAO{
	
	private static final String TABLE_NAME = "CIRCUITS";
    private static final String SQL_SELECT = "SELECT ID, PORT_ID " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_ID = ?" + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
											"(PORT_ID) VALUES(?)" +
											"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_PORT_ID = 2;
    private static final int COLUMN_STATUS = 3;    

	public OracleCircuitDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Circuit.class, connection, daoFactory);
	}

	@Override
	public void update(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_PORT_ID, record.getPort().getId());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public Circuit insert(Circuit record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getPort().getId());
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(Circuit record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Circuit item, ResultSet rs) throws SQLException, Exception {
		item.setId(rs.getInt(COLUMN_ID));
		IPortDAO portDAO = daoFactory.getPortDAO();
		Port port = portDAO.findById(rs.getInt(COLUMN_PORT_ID));
		item.setPort(port);
		
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
