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
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.User;
/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class AdminCommand extends SQLCommand  {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		Gson gson = new Gson();
		String action=(String)request.getParameter("action");
		response.setContentType("application/json");
		if(action==null) {
			return Pages.ADMIN_PAGE.getValue();
		} else {
		if (action.equals("list")) {
		try{     
			IDAO<User> dao = oracleDaoFactory.getUserDAO();
			    //Fetch Data from User Table
			
			    ArrayList<User> lstUser=dao.findAll(); 
			
			    //Convert Java Object to Json   
			
			    JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<User>>() {}.getType());
			
			    JsonArray jsonArray = element.getAsJsonArray();
			
			    String listData=jsonArray.toString();   
			
			    //Return Json in the format required by jTable plugin
			
			    listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";  
			
			    response.getWriter().print(listData);
			    return null;
			
			    }catch(Exception ex){
			
			     String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
			
			     response.getWriter().print(error);
			
			     ex.printStackTrace();
			
			    }
		} }	
		
		return Pages.ADMIN_PAGE.getValue();
	}


}
