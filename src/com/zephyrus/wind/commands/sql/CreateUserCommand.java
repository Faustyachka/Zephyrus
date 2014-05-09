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
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * This class contains the method, that is declared in @link								// REVIEW: link is not working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new Engineer account in system by Administrator.
 * 
 * @return null because all request to this command are AJAX. Command only					// REVIEW: return. again.
 *         return messages about errors or success.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class CreateUserCommand extends SQLCommand {
	
	/**
	 * This method allows to create new Engineer account in Data Base. It
	 * checks all necessary input data to avoid the exceptions. 
	 * 
	 * @return null because all request to this command are AJAX. Method only
     *         return messages about errors or success.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		User admin = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (admin == null || admin.getRole().getId() != ROLE.ADMIN.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Administrator's account to view this page!<br>"					// REVIEW: HTML
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// get all parameters from page
		String name = request.getParameter("firstname");
		String sname = request.getParameter("secondname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confPassord = request.getParameter("confirmpass");
		int roleId = Integer.parseInt(request.getParameter("engtype"));					// REVIEW: what if param is null or if parse failed
		Date s = new Date(new java.util.Date().getTime());

		// check first name
		if (name.equals("")) {															// REVIEW: what if it is null?
			response.setContentType("text/plain");										// REVIEW: method should be written to encapsulate this code. Only param is message.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Name can not be empty");
			return null;
		}

		// check second name
		if (sname.equals("")) {															// REVIEW: what if it is null?
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Second name can not be empty");
			return null;
		}

		// check email correctness
		final Pattern pattern = Pattern
				.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
		final Matcher matcher = pattern.matcher(email);
		if (!matcher.find()) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Bad email");									// REVIEW: "Bad email" -> "Wrong email format"
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
		if (password.equals("")) {														// REVIEW: what if it is null?
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

		else {																		// REVIEW: could be formed as separate method
			// if everything is OK create user
			User user = new User();
			user.setFirstName(name);
			user.setLastName(sname);
			user.setEmail(email);
			user.setPassword(SHAHashing.getHash(password));
			user.setStatus(USER_STATUS.ACTIVE.getValue());
			IUserRoleDAO roleDAO = getOracleDaoFactory().getUserRoleDAO();
			UserRole role = roleDAO.findById(roleId);
			user.setRole(role);
			user.setRegistrationData(s);
			userDAO.insert(user);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Account created!");
		}
		return null;
	}

}
