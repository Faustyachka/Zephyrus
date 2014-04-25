package com.zephyrus.wind.commands.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;

public abstract class SQLCommand implements Command {
	private ThreadLocal<OracleDAOFactory> oracleFactoryThreadLocal = new ThreadLocal<OracleDAOFactory>(){
		public OracleDAOFactory initialValue(){
			return new OracleDAOFactory();
		}
	};

  public SQLCommand() {
  }

  public String execute(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
      String page = null;
      try {
    	  getOracleDaoFactory().beginConnection();
          page = doExecute(request, response);
      } catch(SQLException ex){
          ex.printStackTrace();
          request.setAttribute("errorMessage", MessageManager.SQL_ERROR_MESSAGE + ex.getMessage());
          request.setAttribute("title", MessageManager.ERROR);
          page = PAGES.MESSAGE_PAGE.getValue();
      } finally{
    	  getOracleDaoFactory().endConnection();
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
