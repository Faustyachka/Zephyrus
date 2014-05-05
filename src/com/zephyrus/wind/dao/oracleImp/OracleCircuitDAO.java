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
    private static final String SQL_SELECT = "SELECT ID, PORT_ID, CONFIG, ROWNUM AS ROW_NUM " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_ID = ?, " + 
                                      " CONFIG = ? " +
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
											"(PORT_ID, CONFIG) VALUES(?, ?)" +
											"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    

	public OracleCircuitDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Circuit.class, connection, daoFactory);
	}

	@Override
	public void update(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(1, record.getPort().getId());
    	stmt.setString(2, record.getConfig());
    	stmt.setLong(3, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public Circuit insert(Circuit record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getPort().getId());
    	cs.setString(2, record.getConfig());
    	cs.registerOutParameter(3, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(3);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(Circuit record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Circuit item, ResultSet rs) throws SQLException, Exception {
		item.setId(rs.getInt(1));
		IPortDAO portDAO = daoFactory.getPortDAO();
		Port port = portDAO.findById(rs.getInt(2));
		item.setPort(port);
		item.setConfig(rs.getString(3));
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
