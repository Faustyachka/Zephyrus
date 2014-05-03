package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;											
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;												// REVIEW: unused imports found
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
																										// REVIEW: documenatation & author expected
public class CustomerServicesCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		IServiceInstanceDAO serviceDAO = getOracleDaoFactory().getServiceInstanceDAO();
		User user = (User) request.getSession().getAttribute("user");
		
		
		ArrayList<ServiceInstance> actualServices = serviceDAO.getActiveServiceInstancesByUser(user);
		
		request.setAttribute("actualServices", actualServices);
		
		return PAGES.CUSTOMERSERVICES_PAGE.getValue();
	}

}
