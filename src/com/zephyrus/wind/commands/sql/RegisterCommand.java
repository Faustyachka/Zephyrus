package com.zephyrus.wind.commands.sql;

import java.math.BigDecimal;																	// REVIEW: unused import found
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;
																								// REVIEW: documentation expected
public class RegisterCommand extends SQLCommand {												// REVIEW: CreateUserCommand implements data check functionality but this command - doesn't

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");									// REVIEW: variable wasn't used - check functionality was not implemented
		request.setAttribute("firstname", firstName);
		request.setAttribute("lastname", lastName);
		
		IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
		User user = userDAO.findByEmail(email);
		if(user != null){																		// REVIEW: too long line
			request.setAttribute("message", "This login already exists! <a href=' " + PAGES.REGISTER_PAGE.getValue()+"' > Return to register page </a>"); 
			return PAGES.MESSAGE_PAGE.getValue();
		}
		Date s = new Date(new java.util.Date().getTime());									  	// REVIEW: "s" name is not suitable for this variable
		user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(SHAHashing.getHash(password));
		user.setRegistrationData(s);															// REVIEW: setRegistationDatE, not Data
		user.setStatus(USER_STATUS.ACTIVE.getValue());
		UserRole role = new UserRole();
		role.setId(ROLE.CUSTOMER.getId());
		role.setRoleName(ROLE.CUSTOMER.name());
		user.setRole(role);
		user = userDAO.insert(user);
		
		request.login(email, password);
		if(request.getSession().getAttribute("user") == null)
			request.getSession().setAttribute("user", user);
		if(request.getSession().getAttribute("service") != null){
			return PAGES.ORDERDETAIL_PAGE.getValue();
		}
		
	    request.setAttribute("message", "You have successfully registered!");
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
