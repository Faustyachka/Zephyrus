package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;

/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */

public class CreateNewPassComand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int userId;
		if (request.getParameter("userId").equals("")){
			userId=Integer.parseInt(request.getParameter("userId2"));
		} else {
		userId = Integer.parseInt(request.getParameter("userId"));
		}
		String pass = request.getParameter("pass");
		String confPass = request.getParameter("confpass");
		if(pass==null || confPass==null) {
			request.setAttribute("error", "Password can not be empty");
			request.setAttribute("id", userId);
			return "support/changepass.jsp";
		}
		if (!(pass.equals(confPass))){
			request.setAttribute("error", "Passwords do not coincide");
			request.setAttribute("id", userId);
			return "support/changepass.jsp";
		} 
		String password = SHAHashing.getHash(pass);
		IUserDAO dao = oracleDaoFactory.getUserDAO();
		User user = dao.findById(userId);
		user.setPassword(password);
		dao.update(user);
		request.setAttribute("message", "Password changed <br> <a href='/Zephyrus/support'>return to home page</a>");		
		return Pages.MESSAGE_PAGE.getValue();
	}

}
