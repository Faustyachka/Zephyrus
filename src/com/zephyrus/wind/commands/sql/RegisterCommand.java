package com.zephyrus.wind.commands.sql;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.email.Email;
import com.zephyrus.wind.email.EmailSender;
import com.zephyrus.wind.email.RegistrationSuccessfulEmail;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new Customer User account in system.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class RegisterCommand extends SQLCommand {

	private String name;
	private String sname;
	private String email;
	private String password;
	private String confPassord;
	private Date date;
	
	/**
	 * This method allows to create new Customer User account in Data Base. It
	 * checks all necessary input data to avoid the exceptions.
	 * 
	 * @return null because all request to this command are AJAX. Method only
	 *         return messages about errors or success.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		// get all parameters from page 
		name = request.getParameter("firstname");
		sname = request.getParameter("secondname");
		email = request.getParameter("email");
		password = request.getParameter("password");
		confPassord = request.getParameter("confirmpass");
		date = new Date(new java.util.Date().getTime());
		
		if (name == null || sname==null || email == null || password==null || confPassord==null) {
			reply(response, "Failed to create user!");
			return null;
		}
		
		// check first name
		if (name.equals("")) { 
			reply(response, "Name can not be empty");
			return null;
		}

		// check second name
		if (sname.equals("")) { 
			reply(response, "Second name can not be empty");
			return null;
		}

		// check email correctness
		final Pattern pattern = Pattern
				.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
		final Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) { 
			reply(response, "Wrong email format");
			return null;
		}

		// check the existing of email in system
		IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
		User existingUser = userDAO.findByEmail(email);
		if (existingUser != null) {
			reply(response, "This email already exist in system");
			return null;
		}
		// check password
		if (password.equals("")) { 
			reply(response, "Password can not be empty");
			return null;
		}
		
		if (password.length() < 5 || password.length() > 30) {
			reply(response, "Password length should be from 5 to 30 characters");
			return null;
		}
		
		// check password confirmation on corresponding
		if (!password.equals(confPassord)) {
			reply(response, "Passwords don't coincides!");
			return null;
		}

		else { 
			// if everything is OK create user
			createUser(userDAO);
			reply(response, "Account created!");
			return null;
			
		}
	}
	
	private void createUser(IUserDAO userDAO) throws Exception {
		User user = new User();
		user.setFirstName(name);
		user.setLastName(sname);
		user.setEmail(email);
		user.setPassword(SHAHashing.getHash(password));
		user.setStatus(USER_STATUS.ACTIVE.getValue());
		IUserRoleDAO roleDAO = getOracleDaoFactory().getUserRoleDAO();
		UserRole role = roleDAO.findById(ROLE.CUSTOMER.getId());
		user.setRole(role);
		user.setRegistrationData(date);
		userDAO.insert(user);
		EmailSender sender = new EmailSender();
		Email emailMessage = new RegistrationSuccessfulEmail(name, email,
				password);
		sender.sendEmail(user.getEmail(), emailMessage);
	}
	
	private void reply(HttpServletResponse response, String replyText) throws IOException {
		response.setContentType("text/plain");		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(replyText);
	}

}
