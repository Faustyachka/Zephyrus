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
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCableDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCircuitDAO;
import com.zephyrus.wind.dao.oracleImp.OracleDeviceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderTypeDAO;
import com.zephyrus.wind.dao.oracleImp.OraclePortDAO;
import com.zephyrus.wind.dao.oracleImp.OraclePortStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProductCatalogDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProviderLocationDAO;
import com.zephyrus.wind.dao.oracleImp.OracleReportDAO;
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

/**
 * This class generates DAO instances and encapsulates connection to DB
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya & Igor Litvinenko
 */
public class OracleDAOFactory implements IDAOFactory {											

	private Connection connection = null;
    
    public OracleDAOFactory(){
    }
    
    /**
     * Obtains connection from pool for current factory.
     * Sets auto commit to false
     * @throws SQLException if failed to obtain connection
     */
    public void beginConnection() throws SQLException{
        connection = ConnectionManager.INSTANCE.getConnection();
        connection.setAutoCommit(false);
    }
    
    /**
     * Closes connection obtained by factory
     * @throws SQLException if failed to close connection
     */
    public void endConnection() {
        if(connection != null) {
        	try {
        		/*
        		 * Rollback invoked here only has an effect if commit() method was
        		 * not invoked. So it rolls back transaction which was not commited.
        		 */
        		connection.rollback();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
    
    /**
     * This function commits current transaction, presented by executions between 
     * <code>beginConnection()</code> and <code>endConnection()</code>
     * @throws SQLException if failed to commit
     */
    public void commitTransaction() throws SQLException {
    	if(connection != null) {
        	connection.commit();
        }
    }
    
    /**
     * This function rolls back current transaction, presented by executions between 
     * <code>beginConnection()</code> and <code>endConnection()</code>
     * @throws SQLException if failed to roll back
     */
    public void rollbackTransaction() throws SQLException {
    	if(connection != null) {
        	connection.rollback();
        }
    }
    
    /**
     * Method gets OracleCableDAO instance
     * @return OracleCableDAO instance
     */

	@Override
	public ICableDAO getCableDAO() throws Exception {
		return new OracleCableDAO(connection, this);
	}

	/**
     * Method gets OracleCircuitDAO instance
     * @return OracleCircuitDAO instance
     */
	@Override
	public ICircuitDAO getCircuitDAO() throws Exception {
		return new OracleCircuitDAO(connection, this);
	}

	/**
     * Method gets OracleDeviceDAO instance
     * @return OracleDeviceDAO instance
     */
	@Override
	public IDeviceDAO getDeviceDAO() throws Exception {
		return new OracleDeviceDAO(connection, this);
	}

	/**
     * Method gets OracleOrderStatusDAO instance
     * @return OracleOrderStatusDAO instance
     */
	@Override
	public IOrderStatusDAO getOrderStatusDAO() throws Exception {
		return new OracleOrderStatusDAO(connection, this);
	}

	/**
     * Method gets OracleOrderTypeDAO instance
     * @return OracleOrderTypeDAO instance
     */
	@Override
	public IOrderTypeDAO getOrderTypeDAO() throws Exception {
		return new OracleOrderTypeDAO(connection, this);
	}

	/**
     * Method gets OraclePortDAO instance
     * @return OraclePortDAO instance
     */
	@Override
	public IPortDAO getPortDAO() throws Exception {
		return new OraclePortDAO(connection, this);
	}

	/**
     * Method gets OracleProductCatalogDAO instance
     * @return OracleProductCatalogDAO instance
     */
	@Override
	public IProductCatalogDAO getProductCatalogDAO() throws Exception {
		return new OracleProductCatalogDAO(connection, this);
	}

	/**
     * Method gets OracleProviderLocationDAO instance
     * @return OracleProviderLocationDAO instance
     */
	@Override
	public IProviderLocationDAO getProviderLocationDAO() throws Exception {
		return new OracleProviderLocationDAO(connection, this);
	}

	/**
     * Method gets OracleServiceInstanceDAO instance
     */
	@Override
	public IServiceInstanceDAO getServiceInstanceDAO() throws Exception {
		return new OracleServiceInstanceDAO(connection, this);
	}

	/**
     * Method gets OracleServiceInstanceStatusDAO instance
     * @return OracleServiceInstanceStatusDAO instance
     */
	@Override
	public IServiceInstanceStatusDAO getServiceInstanceStatusDAO()
			throws Exception {
		return new OracleServiceInstanceStatusDAO(connection, this);
	}

	/**
     * Method gets OracleServiceLocationDAO instance
     * @return OracleServiceLocationDAO instance
     */
	@Override
	public IServiceLocationDAO getServiceLocationDAO() throws Exception {
		return new OracleServiceLocationDAO(connection, this);
	}

	/**
     * Method gets OracleServiceOrderDAO instance 
     * @return OracleServiceOrderDAO instance
     */
	@Override
	public IServiceOrderDAO getServiceOrderDAO() throws Exception {
		return new OracleServiceOrderDAO(connection, this);
	}
	
	/**
     * Method gets OracleServiceTypeDAO instance
     * @return OracleServiceTypeDAO instance
     */
	@Override
	public IServiceTypeDAO getServiceTypeDAO() throws Exception {
		return new OracleServiceTypeDAO(connection, this);
	}

	/**
     * Method gets OracleTaskDAO instance
     * @return OracleTaskDAO instance
     */
	@Override
	public ITaskDAO getTaskDAO() throws Exception {
		return new OracleTaskDAO(connection, this);
	}

	/**
     * Method gets OracleTaskStatusDAO instance
     * @return OracleTaskStatusDAO instance
     */
	@Override
	public ITaskStatusDAO getTaskStatusDAO() throws Exception {
		return new OracleTaskStatusDAO(connection, this);
	}

	/**
     * Method gets OracleUserDAO instance
     * @return OracleUserDAO instance
     */
	@Override
	public IUserDAO getUserDAO() throws Exception {
		return new OracleUserDAO(connection, this);
	}

	/**
     * Method gets OracleUserRoleDAO instance
     * @return OracleUserRoleDAO instance
     */
	@Override
	public IUserRoleDAO getUserRoleDAO() throws Exception {
		return new OracleUserRoleDAO(connection, this);
	}

	/**
     * Method gets OracleReportDAO instance
     * @return OracleReportDAO instance
     */
	@Override
	public IReportDAO getReportDAO() throws Exception {
		return (IReportDAO) new OracleReportDAO(connection, this);
	}

	/**
     * Method gets OraclePortStatusDAO instance
     * @return OraclePortStatusDAO instance
     */
	@Override
	public IPortStatusDAO getPortStatusDAO() throws Exception {
		return new OraclePortStatusDAO(connection, this);
	}
}

