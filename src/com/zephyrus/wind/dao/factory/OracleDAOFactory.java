package com.zephyrus.wind.dao.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.zephyrus.wind.managers.ConnectionManager;

public class OracleDAOFactory implements IDAOFactory {
	
	private Connection connection = null;
    private static OracleDAOFactory instance;
    
    private OracleDAOFactory(){
        
    }
    
    public static OracleDAOFactory getInstance(){
        if (instance == null)
            instance = new OracleDAOFactory();
        return instance;
    }
    
    public void beginConnection() throws SQLException{
        connection = ConnectionManager.getInstance().getConnection();
    }
    
    public void endConnection() throws SQLException{
        if(connection != null)
            ConnectionManager.getInstance().closeConnection(connection);
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
    
}
