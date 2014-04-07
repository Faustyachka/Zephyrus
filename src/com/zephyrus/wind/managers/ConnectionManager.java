package com.zephyrus.wind.managers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	
	private DataSource ds;
    private Context cxt;
    private static ConnectionManager connManager;

    private ConnectionManager() {
        try{
        prepare();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        if (connManager == null) {
            connManager = new ConnectionManager();
        }
        return connManager;
    }

    private void prepare() throws NamingException, Exception {
        cxt = new InitialContext();
        if (cxt == null) {
            throw new Exception("Context Error! ");
        }
        ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/carrentdb");
        if (ds == null) {
            throw new Exception("Data source missing!");
        }
    }

    public Connection getConnection() throws SQLException{
        try{
        return ds.getConnection();
        } catch(SQLException ex){
            throw new SQLException("Can't get connection!", ex);
        }
    }

    public void closeConnection(Connection conn) throws SQLException {
       try{
         conn.close();
        } catch(SQLException ex){
        	throw new SQLException("Can't close connection!", ex);
        }
    }

}
