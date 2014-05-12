package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;													
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;												
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.User;
																										
/**
 * This class contains the method, that is declared in
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to display
 * defined user's Service Instances to Customer User.
 * 
 * @author Miroshnychenko Nataliya
 * 
 */

public class CustomerServicesCommand extends SQLCommand {

	
	/**
	 * This method gets the data about Service Instances for
	 * defines user from database and sends it to the page of viewing.
	 * 
	 * @return Customer User page with Service Instances. Also can return the error page if the received
	 *         user ID is not valid
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		IServiceInstanceDAO serviceDAO = getOracleDaoFactory().getServiceInstanceDAO();
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		Map<ServiceInstance, String> activeServices = new HashMap<ServiceInstance, String>();	
		
		ArrayList<ServiceInstance> services = serviceDAO.getActiveServiceInstancesByUser(user);
		
		for (ServiceInstance instance: services) {												
			activeServices.put(instance, 
					orderDAO.getSICreateOrder(instance).getServiceLocation().getAddress());	
		}	
		
		request.setAttribute("actualServices", activeServices);
		
		return PAGES.CUSTOMERSERVICES_PAGE.getValue();
	}

}
