package com.zephyrus.wind.commands.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
																									// REVIEW: documentation expected
public interface Command {
	public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
