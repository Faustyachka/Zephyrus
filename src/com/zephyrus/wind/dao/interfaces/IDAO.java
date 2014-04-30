package com.zephyrus.wind.dao.interfaces;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 *																										// REVIEW: documentation expected
 * @author MrDan																						// REVIEW: who is it?
 */
public interface IDAO<T> {																				// REVIEW: unchecked DAO exception should be used
	@Deprecated
	ArrayList<T> findAll() throws Exception;															// REVIEW: method forbidden. Paging should be used
	
    /**
	 * Method find specified count of class objects
	 * 
	 * @param int firstItem - begin item of search
	 * @param int count - number of return class objects
	 * @return ArrayList<T> - collection of class objects
	 */
	public ArrayList<T> find(int firstItem, int count) throws Exception;
	
    /**
	 * Method find count of existing class objects from corresponding DB table
	 * 
	 * @return int - number of existing class objects
	 */
	public int count() throws SQLException;
	T findById(int id) throws Exception;
    void update(T record) throws Exception;															
    T insert(T record) throws Exception;
    int remove(T record) throws Exception;
    int removeById(int id) throws Exception;
}
