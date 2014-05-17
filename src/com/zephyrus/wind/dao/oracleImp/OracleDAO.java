package com.zephyrus.wind.dao.oracleImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;

/**
* The abstract class that defines common to all DAO methods.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/

public abstract class OracleDAO<T> {
    
    protected Class<T> itemClass;
    protected Connection connection;
    protected PreparedStatement stmt = null;
    protected CallableStatement cs = null;
    protected ResultSet rs = null;
    protected OracleDAOFactory daoFactory;
    
    public OracleDAO (Class<T> itemClass, Connection connection, OracleDAOFactory daoFactory) throws Exception{
        this.itemClass = itemClass;
        if(connection == null) throw new Exception("No connection found!");
        this.connection = connection;
        this.daoFactory = daoFactory;
    }
    
    /**
    * Method return class instance from database result set
    * @param ResultSet                                                           //REVIEW: description expected
    * @return class instance
    */
    protected T fetchSingleResult(ResultSet rs) 
            throws SQLException, 
            InstantiationException,
            IllegalAccessException, Exception{                                       //REVIEW: Exception is superclass of all of this exceptions
        if (rs.next()) {
            T item = itemClass.newInstance();
            fillItem(item, rs);
            rs.close();
        	if(stmt != null)
        		stmt.close();                                                      //REVIEW: braces expected
        	if(cs != null)
        		cs.close();
            return item;
        } else {
        	rs.close();
        	if(stmt != null)
        		stmt.close();
        	if(cs != null)
        		cs.close();
            return null;
        }

    }

    /**
     * Method take ResultSet and return ArrayList of class instance
     * @param ResultSet database result set
     * @return ArrayList of class instance
     */
    protected ArrayList<T> fetchMultiResults(ResultSet rs)
            throws SQLException,
            InstantiationException,
            IllegalAccessException, Exception{                                              //REVIEW: see previous comments about Exception
        ArrayList<T> resultList = new ArrayList<T>();
        T item = null;
        while (rs.next()) {
        	item = itemClass.newInstance();
            fillItem(item, rs);
        	resultList.add(item);
        }
            
        rs.close();                                                                    
    	if(stmt != null)                                                                 //REVIEW: comments expected
    		stmt.close();                                                                //REVIEW: braces expected
    	if(cs != null)
    		cs.close();
        return resultList;
    }
    
    /**
	 * Method takes created class instance and set values to its variables 
	 * @param class instance T
	 * @param ResultSet a database result set represented by DB
	 */
    protected abstract void fillItem(T item, ResultSet rs) throws SQLException, Exception;   //REVIEW: see previous comments about Exception
    
    /**
   	 * Method gives text of SQL select query
   	 *@return text of select query
   	 */
    protected abstract String getSelect();
    
    /**
   	 * Method gives text of SQL delete query
   	 *@return text of delete query
   	 */
    protected abstract String getDelete();
    
    /**
	 * Method find all class instance from DB records for corresponding object type. 
	 * It is deprecated after creation method find(int, int).
	 * @return ArrayList<T> - collection of class instance
	 */
    @Deprecated
    public ArrayList<T> findAll() throws Exception {
        stmt = connection.prepareStatement(getSelect());
        rs = stmt.executeQuery();
        return fetchMultiResults(rs);
    }

    /**
	 * Method find specified count of class instance
	 * @param int firstItem - begin item of search
	 * @param int count - number of return class instance
	 * @return ArrayList<T> - collection of class instance
	 */
                                                                                      //REVIEW: extra line
    public ArrayList<T> find(int firstItem, int count) throws Exception {
    	int lastItem = firstItem + count - 1;
        stmt = connection.prepareStatement("SELECT * FROM ( " + getSelect()  + 
        		") WHERE ROW_NUM BETWEEN ?  AND ?");
        stmt.setInt(1, firstItem);
		stmt.setInt(2, lastItem);
        rs = stmt.executeQuery();
        return fetchMultiResults(rs);
    }
    
    
    /**
	 * Method find count of existing class instance from corresponding DB table
	 * @return int - number of existing class instance
	 */
    public int count() throws SQLException{
    	int result = 0;
    	stmt = connection.prepareStatement("SELECT COUNT(*) FROM (" + getSelect() +
    			" )");
    	rs = stmt.executeQuery();
    	if (rs.next()) {
    		result = rs.getInt(1);
    	}
    	rs.close();
    	return result;
    }
    ;
    
    /**
	 * Method find an class instance from DB table by its primary key
	 * @param id the primary key
	 * @return class instance
	 */
    public T findById(int id) throws Exception {
        stmt = connection.prepareStatement(getSelect() + "WHERE ID=?");
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        return fetchSingleResult(rs);
    }
    
    /**
	 * Method find an class instance from DB table by corresponding rowid
	 * @param rowid of the record
	 * @return class instance
	 */
    public T findByRowId(String id) throws Exception {
        stmt = connection.prepareStatement(getSelect() + "WHERE ROWID=?");
        stmt.setString(1, id);
        rs = stmt.executeQuery();
        return fetchSingleResult(rs);
    }

    /**
	 * Method delete an class instance from the database by its primary key.
	 * @param id the primary key
	 * @return count of removed class instance
	 */
    public int removeById(int id) throws Exception {
        stmt = connection.prepareStatement(getDelete() + "ID=?");
        stmt.setInt(1, id);
        int res = stmt.executeUpdate();
        stmt.close();
        return res;
    }
}

