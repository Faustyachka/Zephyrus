package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class CreateUserCommand extends SQLCommand {
	

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IDAO<User> oud;
		int counter=0;
		String name = request.getParameter("firstname");
		String sname = request.getParameter("secondname");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String confPassord = request.getParameter("confirmpass");
		int roleId = Integer.parseInt(request.getParameter("engtype"));
		oud = getOracleDaoFactory().getUserDAO();
		Date s = new Date(new java.util.Date().getTime());
		ArrayList<User> users = oud.findAll();
		if (name.equals("")) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Name can not be empty"); 
		    return null;
		}
		if (sname.equals("")) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Second name can not be empty"); 
		    return null;
		}
		if (password.equals("")) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Second name can not be empty"); 
		    return null;
		}
		if (!password.equals(confPassord)) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Passwords don't coincides!"); 
		    return null;
		}
		final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
		final Matcher matcher = pattern.matcher(email);

		if (!matcher.find()) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Bad email"); 
		    return null;
		} 
		for (User u: users) {
			if (u.getEmail().equals(email)) {
				counter++;
			}
		}
		if (counter>0) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("This email already exist in system"); 
		    return null;
		} else {
			User user = new User();
			user.setFirstName(name);
			user.setLastName(sname);
			user.setEmail(email);		
			user.setPassword(SHAHashing.getHash(password));
			user.setStatus(USER_STATUS.ACTIVE.geValue());
			IDAO<UserRole> ord = getOracleDaoFactory().getUserRoleDAO();
			UserRole role = ord.findById(roleId);
			user.setRole(role);
			user.setRegistrationData(s);			
			oud.insert(user);			
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Account created!"); 
			
		}
		return null;
	}

}
