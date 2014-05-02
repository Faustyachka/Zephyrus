package com.zephyrus.wind.commands.sql;

import java.math.BigDecimal;																	// REVIEW: unused import found
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.email.Email;
import com.zephyrus.wind.email.EmailSender;
import com.zephyrus.wind.email.RegistrationSuccessfulEmail;
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
		if(userDAO.findByEmail(email) != null){																		
			request.setAttribute("message", "User with current email already exists! "
					+ "<a href=' " + PAGES.REGISTER_PAGE.getValue()+"'>Return to register page</a>"); 
			return PAGES.MESSAGE_PAGE.getValue();
		}
		Date s = new Date(new java.util.Date().getTime());									  	// REVIEW: "s" name is not suitable for this variable
		User user = new User();
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
	
		EmailSender sender = new EmailSender();
    	Email emailMessage = new RegistrationSuccessfulEmail(firstName, email, password);
    	sender.sendEmail(user, emailMessage);
		
	    request.setAttribute("message", "You have successfully registered!");
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
