package com.zephyrus.wind.commands.sql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.UserStatus;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;

public class RegisterCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("sname");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String cpassword = request.getParameter("confirmpass");
		request.setAttribute("firstname", firstName);
		request.setAttribute("lastname", lastName);
		
		IUserDAO userDAO = oracleDaoFactory.getUserDAO();
		User user = userDAO.findByEmail(email);
		if(user != null){
			request.setAttribute("message", "This login already exists! <a href=' " + Pages.REGISTER_PAGE.getValue()+"' > Return to register page </a>");
			return Pages.MESSAGE_PAGE.getValue();
		}
		Date s = new Date(new java.util.Date().getTime());
		user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setRegistrationData(s);
		user.setStatus(UserStatus.ACTIVE.geValue());
		user.setRoleId(ROLE.CUSTOMER.getId());
		userDAO.insert(user);

		if(request.getSession().getAttribute("service") != null){
			return Pages.ORDERDETAIL_PAGE.getValue();
		}
		
	    request.setAttribute("message", "You have successfully registered!");
		return Pages.MESSAGE_PAGE.getValue();
	}

}
