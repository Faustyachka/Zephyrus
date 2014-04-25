package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.User;

/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */

public class CustomerSupportCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
//		IUserDAO dao = oracleDaoFactory.getUserDAO();
//		ArrayList<User> users = dao.findAll();
//		request.getSession().setAttribute("users", users);
//		return Pages.SUPPORT_PAGE.getValue();
		
		Gson gson = new Gson();
		String action=(String)request.getParameter("action");
		response.setContentType("application/json");
		if(action==null) {
			return PAGES.SUPPORT_PAGE.getValue();
		} else {
		if (action.equals("list")) {
		try{     
			IUserDAO dao = getOracleDaoFactory().getUserDAO();			
			    ArrayList<User> lstUser=dao.getUsersByRoleId(2);			
			    JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<User>>() {}.getType());			
			    JsonArray jsonArray = element.getAsJsonArray();			
			    String listData=jsonArray.toString();   						
			    listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";  		
			    response.getWriter().print(listData);
			    return null;			
			    }catch(Exception ex){
			
			     String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
			
			     response.getWriter().print(error);
			
			     ex.printStackTrace();
			
			    }
		} }	
		
		return PAGES.SUPPORT_PAGE.getValue();
	}

}
