package com.zephyrus.wind.commands.sql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.UserStatus;
import com.zephyrus.wind.model.User;

public class LoginCommand extends SQLCommand {

	@Override
	public String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getUserPrincipal().getName();
		IUserDAO userDAO = oracleDaoFactory.getUserDAO();
		User user = userDAO.findByEmail(userName);
		if(user != null){
			if(user.getStatus()==UserStatus.BLOCKED.geValue()){
				request.logout();
				request.setAttribute("message", "Your account " + userName + "is blocked!");
				return Pages.MESSAGE_PAGE.getValue();
			} else {
				if(request.getSession().getAttribute("user") == null)
					request.getSession().setAttribute("user", user);
				if(request.getSession().getAttribute("service") != null){
					return Pages.ORDERDETAIL_PAGE.getValue();
				}
				for(ROLE role : ROLE.values()){
					if(user.getRole().getId()==role.getId()){
						return role.getHome();
					}
				}
			}
		}
		
		return Pages.HOME_PAGE.getValue();
	}

}
