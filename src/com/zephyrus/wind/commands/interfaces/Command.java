package com.zephyrus.wind.commands.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * This interface provides generic method for Commands' execution
 */
public interface Command {
	
	/**
	 * This generic method allows to execute current Command with
	 * given request/response params of servlet that invoked this method
	 * @param request instance of HttpServletRequest, which relays given request params
	 * @param response instance of HttpServletResponse, which allows to relay response
	 * @return string, representing page user should be redirected to after
	 * method execution
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
