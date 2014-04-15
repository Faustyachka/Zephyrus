package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.enums.ROLE;

public class LoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		for(ROLE role : ROLE.values()){
			if(request.isUserInRole(role.name())){
				if(request.getSession().getAttribute("username") == null)
					request.getSession().setAttribute("username", request.getUserPrincipal().getName());
				return role.getHome();
			}
		}
		return Pages.HOME_PAGE.getValue();
	}

}
