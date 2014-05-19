package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.PAGES;
																									
public class LogoutCommand implements Command {
																									
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.logout();
		request.getSession().invalidate();
		return PAGES.HOME_PAGE.getValue();
	}

}
