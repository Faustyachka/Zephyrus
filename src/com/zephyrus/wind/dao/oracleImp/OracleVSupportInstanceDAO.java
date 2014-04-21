package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IVSupportInstanceDAO;
import com.zephyrus.wind.model.VSupportInstance;

public class OracleVSupportInstanceDAO extends OracleDAO<VSupportInstance> implements IVSupportInstanceDAO{
	private static final String TABLE_NAME = "MISTERDAN.V_SUPPORT_INSTANCE";
    private static final String SQL_SELECT = "SELECT USER_ID, SI_ID, SI_STARD_DATE, " + 
                                      "SERVICE, PRICE, SI_STATUS FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SI_ID= ?, SI_STARD_DATE = ?, " + 
                                      "SERVICE = ?,  PRICE = ?, SI_STATUS = ? WHERE " + 
                                      " USER_ID = ?";
 
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";

	public OracleVSupportInstanceDAO(
			Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(VSupportInstance.class, connection, daoFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(VSupportInstance record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setBigDecimal(2, record.getSiId());
    	stmt.setDate(3, record.getSiStardDate());
    	stmt.setString(4, record.getService());
    	stmt.setBigDecimal(5, record.getPrice());
    	stmt.setString(6, record.getSiStatus());
    	stmt.setBigDecimal(1, record.getUserId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(VSupportInstance record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int remove(VSupportInstance record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void fillItem(VSupportInstance item, ResultSet rs)
			throws SQLException {
		item.setUserId(rs.getBigDecimal(1));
		item.setSiId(rs.getBigDecimal(2));
		item.setSiStardDate(rs.getDate(3));
		item.setService(rs.getString(4));
		item.setPrice(rs.getBigDecimal(5));
		item.setSiStatus(rs.getString(6));

		
	}

	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	@Override
	public ArrayList<VSupportInstance> getInstancesByUserId(int id) throws SQLException, InstantiationException, IllegalAccessException {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

}
