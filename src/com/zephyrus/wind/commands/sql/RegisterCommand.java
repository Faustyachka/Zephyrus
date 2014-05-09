package com.zephyrus.wind.commands.sql;

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
 * This class contains the method, that is declared in @link								// REVIEW: link is not working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new Customer User account in system.
 * 
 * @return null because all request to this command are AJAX. Command only					// REVIEW: return
 *         return messages about errors or success.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class RegisterCommand extends SQLCommand {											
	
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
														
		// get all parameters from page														// REVIEW: excessive tabulation in this class
				String name = request.getParameter("firstname");
				String sname = request.getParameter("secondname");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String confPassord = request.getParameter("confirmpass");
				Date registrationDate = new Date(new java.util.Date().getTime());

				// check first name
				if (name.equals("")) {														// REVIEW: null pointer exception
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");									// REVIEW: this should be formed as the function, with only param: replyText
					response.getWriter().write("Name can not be empty");
					return null;
				}

				// check second name
				if (sname.equals("")) {														// REVIEW: null pointer exception			
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");									// REVIEW: this should be formed as the function, with only param: replyText
					response.getWriter().write("Second name can not be empty");
					return null;
				}

				// check email correctness
				final Pattern pattern = Pattern
						.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
				final Matcher matcher = pattern.matcher(email);
				if (!matcher.find()) {														// REVIEW: use matches()
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Bad email");								// "Bad email" -> "Wrong email format"
					return null;
				}

				// check the existing of email in system
				IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
				User existingUser = userDAO.findByEmail(email);
				if (existingUser != null) {
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("This email already exist in system");
					return null;
				}
				// check password
				if (password.equals("")) {													// REVIEW: null pointer exception
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Password can not be empty");
					return null;
				}

				// check password confirmation on corresponding
				if (!password.equals(confPassord)) {
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Passwords don't coincides!");
					return null;
				}

				else {																	// REVIEW: should be separate function
					// if everything is OK create user
					User user = new User();
					user.setFirstName(name);
					user.setLastName(sname);
					user.setEmail(email);
					user.setPassword(SHAHashing.getHash(password));
					user.setStatus(USER_STATUS.ACTIVE.getValue());
					IUserRoleDAO roleDAO = getOracleDaoFactory().getUserRoleDAO();
					UserRole role = roleDAO.findById(ROLE.CUSTOMER.getId());
					user.setRole(role);
					user.setRegistrationData(registrationDate);
					userDAO.insert(user);
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Account created!");
			     	EmailSender sender = new EmailSender();
			    	Email emailMessage = new RegistrationSuccessfulEmail(name, email, password);
			    	sender.sendEmail(user.getEmail(), emailMessage);
				}
				return null;
	}

}
