package com.zephyrus.wind.dao.interfaces;


import java.util.ArrayList;

/**
 *
 * @author MrDan
 */
public interface IDAO<T> {
    ArrayList<T> findAll() throws Exception;
    T findById(int id) throws Exception;
    void update(T record) throws Exception;
    int insert(T record) throws Exception;
    int remove(T record) throws Exception;
    int removeById(int id) throws Exception;
}
