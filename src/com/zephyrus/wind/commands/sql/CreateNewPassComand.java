package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;

/**
 * 																									// REVIEW: documenation expected
 * @author Alexandra Beskorovaynaya
 *
 */

public class CreateNewPassComand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		IUserDAO dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);
		if (user == null){
			request.setAttribute("error", "No such user!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		String pass = request.getParameter("pass");
		String confPass = request.getParameter("confpass");
		if(pass==null || confPass==null) {
			request.setAttribute("error", "Password can not be empty");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp";
		}
		if (!(pass.equals(confPass))){
			request.setAttribute("error", "Passwords do not coincide");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp";
		} 
		String password = SHAHashing.getHash(pass);
		user.setPassword(password);
		dao.update(user);
		request.setAttribute("message", "Password changed <br> <a href='/Zephyrus/support'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
