
package com.zephyrus.wind.dao.factory;

import com.zephyrus.wind.dao.interfaces.IUsersDAO;

public interface IDAOFactory {
   IUsersDAO getUserDAO() throws Exception;
}
