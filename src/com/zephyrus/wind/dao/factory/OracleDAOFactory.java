package com.zephyrus.wind.dao.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCableDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCircuitDAO;
import com.zephyrus.wind.dao.oracleImp.OracleDeviceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderTypeDAO;
import com.zephyrus.wind.dao.oracleImp.OraclePortDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProductCatalogDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProviderLocationDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceInstanceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceLocationDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceOrderDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceTypeDAO;
import com.zephyrus.wind.dao.oracleImp.OracleTaskDAO;
import com.zephyrus.wind.dao.oracleImp.OracleTaskStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleUserDAO;
import com.zephyrus.wind.dao.oracleImp.OracleUserRoleDAO;
import com.zephyrus.wind.managers.ConnectionManager;

public class OracleDAOFactory implements IDAOFactory {
	
	private Connection connection = null;
    private volatile static OracleDAOFactory instance;
    
    private OracleDAOFactory(){
    }
    
    public static OracleDAOFactory getInstance(){
        if (instance == null)
        	synchronized (OracleDAOFactory.class) {
        		if (instance == null)
        			instance = new OracleDAOFactory();
			}
        return instance;
    }
    
    public void beginConnection() throws SQLException{
        connection = ConnectionManager.INSTANCE.getConnection();
    }
    
    public void endConnection() throws SQLException{
        if(connection != null)
            connection.close();
    }
    
    public void beginTransaction() throws SQLException{
        if(connection != null)
            connection.setAutoCommit(false);
    }
    
    public void endTransaction() throws SQLException{
        if(connection != null){
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    
    public void abortTransaction() throws SQLException{
        if(connection != null){
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

	@Override
	public ICableDAO getCableDAO() throws Exception {
		return new OracleCableDAO(connection);
	}

	@Override
	public ICircuitDAO getCircuitDAO() throws Exception {
		return new OracleCircuitDAO(connection);
	}

	@Override
	public IDeviceDAO getDeviceDAO() throws Exception {
		return new OracleDeviceDAO(connection);
	}

	@Override
	public IOrderStatusDAO getOrderStatusDAO() throws Exception {
		return new OracleOrderStatusDAO(connection);
	}

	@Override
	public IOrderTypeDAO getOrderTypeDAO() throws Exception {
		return new OracleOrderTypeDAO(connection);
	}

	@Override
	public IPortDAO getPortDAO() throws Exception {
		return new OraclePortDAO(connection);
	}

	@Override
	public IProductCatalogDAO getProductCatalogDAO() throws Exception {
		return new OracleProductCatalogDAO(connection);
	}

	@Override
	public IProviderLocationDAO getProviderLocationDAO() throws Exception {
		return new OracleProviderLocationDAO(connection);
	}

	@Override
	public IServiceInstanceDAO getServiceInstanceDAO() throws Exception {
		return new OracleServiceInstanceDAO(connection);
	}

	@Override
	public IServiceInstanceStatusDAO getServiceInstanceStatusDAO()
			throws Exception {
		return new OracleServiceInstanceStatusDAO(connection);
	}

	@Override
	public IServiceLocationDAO getServiceLocationDAO() throws Exception {
		return new OracleServiceLocationDAO(connection);
	}

	@Override
	public IServiceOrderDAO getServiceOrderDAO() throws Exception {
		return new OracleServiceOrderDAO(connection);
	}

	@Override
	public IServiceTypeDAO getServiceTypeDAO() throws Exception {
		return new OracleServiceTypeDAO(connection);
	}

	@Override
	public ITaskDAO getTaskDAO() throws Exception {
		return new OracleTaskDAO(connection);
	}

	@Override
	public ITaskStatusDAO getTaskStatusDAO() throws Exception {
		return new OracleTaskStatusDAO(connection);
	}

	@Override
	public IUserDAO getUserDAO() throws Exception {
		return new OracleUserDAO(connection);
	}

	@Override
	public IUserRoleDAO getUserRoleDAO() throws Exception {
		return new OracleUserRoleDAO(connection);
	}
	
	
    
}
