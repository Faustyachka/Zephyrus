package com.zephyrus.wind.dao.interfaces;


import java.sql.SQLException;
import java.util.ArrayList;

/**
* The interface enforces the minimum set of operations needed to deal with DAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/

public interface IDAO<T> {	
	
	/**
	 * Method find all class instance from DB records for corresponding object type. 
	 * It is deprecated after creation method find(int, int).
	 * @return ArrayList<T> - collection of class instance
	 */
	@Deprecated
	ArrayList<T> findAll() throws Exception;															
	
    /**
	 * Method find specified count of class instance
	 * @param int firstItem - begin item of search
	 * @param int count - number of return class instance
	 * @return ArrayList<T> - collection of class instance
	 */
	public ArrayList<T> find(int firstItem, int count) throws Exception;
	
    /**
	 * Method find count of existing class instance from corresponding DB table
	 * @return int - number of existing class instance
	 */
	public int count() throws SQLException;
	
    /**
	 * Method find an class instance from DB table by its primary key
	 * @param id the primary key
	 * @return class instance
	 */
	T findById(int id) throws Exception;
	
    /**
	 * Method update class instance using update in the database.
	 * @param updatable class instance
	 */
    void update(T record) throws Exception;	
    
    /**
	 * Method create new record and compliant class instance.
	 * @param insert class instance
	 * @return inserted class instance
	 */
    T insert(T record) throws Exception;
    
    /**
	 * Method delete an compliant class instance record from the database.
	 * @param removable class instance
	 * @return count of removed class instance
	 */
    int remove(T record) throws Exception;
    
    /**
	 * Method delete an class instance from the database by its primary key.
	 * @param id the primary key
	 * @return count of removed class instance
	 */
    int removeById(int id) throws Exception;
}
