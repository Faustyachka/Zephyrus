package com.zephyrus.wind.dao.interfaces;


import java.util.ArrayList;

/**
 *																										// REVIEW: documentation expected
 * @author MrDan																						// REVIEW: who is it?
 */
public interface IDAO<T> {																				// REVIEW: unchecked DAO exception should be used
    ArrayList<T> findAll() throws Exception;															// REVIEW: method forbidden. Paging should be used
    T findById(int id) throws Exception;
    T findByRowId(String id) throws Exception;															// REVIEW: findByRowId? What is has to do with DAO?
    void update(T record) throws Exception;															
    T insert(T record) throws Exception;
    int remove(T record) throws Exception;
    int removeById(int id) throws Exception;
}
