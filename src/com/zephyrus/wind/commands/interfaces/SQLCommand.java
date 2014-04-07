package com.zephyrus.wind.commands.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.managers.MessageManager;

public abstract class SQLCommand implements Command {
	protected OracleDAOFactory oracleDaoFactory;

  public SQLCommand() {
	  oracleDaoFactory = OracleDAOFactory.getInstance();
  }

  public String execute(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
      String page = null;
      try {
    	  oracleDaoFactory.beginConnection();
          page = doExecute(request, response);
      } catch(SQLException ex){
          ex.printStackTrace();
          request.setAttribute("errorMessage", MessageManager.SQL_ERROR_MESSAGE + ex.getMessage());
          request.setAttribute("title", MessageManager.ERROR);
          page = Pages.MESSAGE_PAGE.getValue();
      } finally{
    	  oracleDaoFactory.endConnection();
      }
      return page;
  }
  
  protected abstract String doExecute(HttpServletRequest request, HttpServletResponse response)
          throws SQLException, Exception;
}
