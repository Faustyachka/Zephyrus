package com.zephyrus.wind.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;
																								// REVIEW: documentation expected
public class SessionTimeOutFilter implements Filter {

FilterConfig fc = null;																			// REVIEW: bad formatting
																								// REVIEW: Override annotation expected
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);

        // if session doesn't exist, forward user to welcome page
        if (session == null) {
            try {
                req.setAttribute("title", MessageManager.WELCOME);
                req.getRequestDispatcher(PAGES.HOME_PAGE.getValue()).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
