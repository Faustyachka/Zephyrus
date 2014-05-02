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
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;

public class OracleCableDAO extends OracleDAO<Cable> implements ICableDAO {

	private static final String TABLE_NAME = "CABLES";
	private static final String SQL_SELECT = "SELECT ID, PORT_ID, SERVICE_LOCATION_ID, CABLE_TYPE, "+
			" ROWNUM AS ROW_NUM " + 
			"FROM " + 
			TABLE_NAME + " ";
	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
			" SET PORT_ID = ?, SERVICE_LOCATION_ID = ?, CABLE_TYPE = ? " + 
			" WHERE " + 
			" ID = ?";
	private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
			"(PORT_ID, SERVICE_LOCATION_ID, CABLE_TYPE) VALUES(?,?,?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";


	public OracleCableDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Cable.class, connection, daoFactory);
	}

	@Override
	public void update(Cable record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		if (record.getPort() == null){
    		stmt.setNull(1, java.sql.Types.INTEGER); 
    	} else {
    		stmt.setInt(1, record.getPort().getId());
    	}
		if (record.getServiceLocation() == null){
    		stmt.setNull(2, java.sql.Types.INTEGER); 
    	} else {
    		stmt.setInt(2, record.getServiceLocation().getId()); 
    	}
		stmt.setString(3, record.getCableType());
		stmt.setLong(4, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public Cable insert(Cable record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		if(record.getPort() == null){
			cs.setNull(1, java.sql.Types.INTEGER);
		}
		else {
			cs.setInt(1, record.getPort().getId());
		}
		if(record.getServiceLocation() == null){
			cs.setNull(2, java.sql.Types.INTEGER);
		}
		else {
			cs.setInt(2, record.getServiceLocation().getId());   
		}
		cs.setString(3, record.getCableType());
		cs.registerOutParameter(4, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(4);
		cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(Cable record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Cable item, ResultSet rs) throws SQLException, Exception {
		item.setId(rs.getInt(1));
		Port port = daoFactory.getPortDAO().findById(rs.getInt(2));
		item.setPort(port);
		IServiceLocationDAO serviceLocationDAO = daoFactory.getServiceLocationDAO();
		ServiceLocation serviceLocation = serviceLocationDAO.findById(rs.getInt(3));
		item.setServiceLocation(serviceLocation);
		item.setCableType(rs.getString(4));
	}

	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	/**
	 * Method checks existing connection to port
	 * 
	 * @param port ID
	 * @return true if connection exist, otherwise false
	 * @author Miroshnychenko Nataliya
	 */

	@Override
	public boolean existConnectToPort(int portId) throws SQLException {
		stmt = connection.prepareStatement("SELECT COUNT(*) FROM " + 
				TABLE_NAME + 
				" WHERE (PORT_ID = ? ) ");
		stmt.setInt(1, portId);
		rs = stmt.executeQuery();		
		if (rs.next() && rs.getInt(1)!=0){
			return true;
		}
		return false;
	}

	/**
	 * Method finds Cable object for Service Location ID
	 * 
	 * @param Service Location ID
	 * @return existing Cable, otherwise null
	 * @author Miroshnychenko Nataliya
	 */

	@Override
	public Cable findCableFromServLoc(int serviceLocationID) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE SERVICE_LOCATION_ID = ?");
		stmt.setInt(1, serviceLocationID);
		rs = stmt.executeQuery();
		return fetchSingleResult(rs);
	}
	
	
}
