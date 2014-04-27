package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IVSupportOrderDAO;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.VSupportOrder;

public class OracleVSupportOrderDAO extends OracleDAO<VSupportOrder> implements IVSupportOrderDAO{
	private static final String TABLE_NAME = "V_SUPPORT_ORDER";
    private static final String SQL_SELECT = "SELECT USER_ID, SO_ID, SL_COORD, " + 
                                      "SERVICE, SO_DATE, SO_VALUE  FROM " + 
                                       TABLE_NAME + " ";
  
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";

	public OracleVSupportOrderDAO(
			Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(VSupportOrder.class, connection, daoFactory);
		
	}

	@Override
	public void update(VSupportOrder record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VSupportOrder insert(VSupportOrder record) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remove(VSupportOrder record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void fillItem(VSupportOrder item, ResultSet rs)
			throws Exception {
		User user = daoFactory.getUserDAO().findById(rs.getInt(1));
		item.setUser(user);
		ServiceOrder so = daoFactory.getServiceOrderDAO().findById(rs.getInt(2));
		item.setSo(so);
		item.setSlCoord(rs.getString(3));
		item.setService(rs.getString(4));
		item.setSoDate(rs.getDate(5));
		item.setSoValue(rs.getString(6));
		
		
	}

	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		// TODO Auto-generated method stub
		return SQL_REMOVE;
	}

	@Override
	public ArrayList<VSupportOrder> getOrdersByUserId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
