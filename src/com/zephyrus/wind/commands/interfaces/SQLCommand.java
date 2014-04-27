package com.zephyrus.wind.commands.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;
																													// REVIEW: documentation & author expected
public abstract class SQLCommand implements Command {
	private static ThreadLocal<OracleDAOFactory> oracleFactoryThreadLocal = new ThreadLocal<OracleDAOFactory>(){	// REVIEW: too long line
		public OracleDAOFactory initialValue(){
			return new OracleDAOFactory();
		}
	};

  public SQLCommand() {																								// REVIEW: empty constructor. is it necessary? 
  }
  																													// REVIEW: Override annotation & documentation expected
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
    	  System.out.println("Connection closed");
      }
      return page;
  }
  
  protected OracleDAOFactory getOracleDaoFactory(){
	  return oracleFactoryThreadLocal.get();
  }
  
  protected abstract String doExecute(HttpServletRequest request, HttpServletResponse response)
          throws SQLException, Exception;
}
