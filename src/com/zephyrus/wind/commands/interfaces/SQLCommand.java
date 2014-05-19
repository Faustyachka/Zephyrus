package com.zephyrus.wind.commands.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;

/**
 * This class implements generic methods, used to manage transactions
 * and concurrency of Commands' execution method invocations.
 */
public abstract class SQLCommand implements Command {
	private static ThreadLocal<OracleDAOFactory> oracleFactoryThreadLocal = new ThreadLocal<OracleDAOFactory>() {	
		public OracleDAOFactory initialValue(){
			return new OracleDAOFactory();
		}
	};

	public SQLCommand() {	}
  		
     /**
	  * This generic method allows to execute current Command with
	  * given request/response params of servlet that invoked this method
	  * @param request instance of HttpServletRequest, which relays given request params
	  * @param response instance of HttpServletResponse, which allows to relay response
	  * @return string, representing page user should be redirected to after
	  * method execution
	  */
	  @Override
	  public String execute(HttpServletRequest request, HttpServletResponse response)
	          throws Exception {
	      String page = null;
	      try {
	    	  getOracleDaoFactory().beginConnection();
	          page = doExecute(request, response);
	          getOracleDaoFactory().commitTransaction();
	      } catch(SQLException ex){
	          ex.printStackTrace();
	          request.setAttribute("errorMessage", MessageManager.SQL_ERROR_MESSAGE + ex.getMessage());
	          request.setAttribute("title", MessageManager.ERROR);
	          page = PAGES.MESSAGE_PAGE.getValue();
	      } finally{
	    	  getOracleDaoFactory().endConnection();
	    	  oracleFactoryThreadLocal.remove();
	      }
	      return page;
	  }
  
	  protected OracleDAOFactory getOracleDaoFactory(){
		  return oracleFactoryThreadLocal.get();
	  }
	  
	  protected abstract String doExecute(HttpServletRequest request, HttpServletResponse response)
	          throws SQLException, Exception;
}
