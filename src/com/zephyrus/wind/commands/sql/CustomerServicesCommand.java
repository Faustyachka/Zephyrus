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
		ArrayList<ServiceInstance> services = serviceDAO.getServiceInstancesByUserId(user.getId());
		ArrayList<ServiceInstance> actualServices = new ArrayList<ServiceInstance>();
		ArrayList<ServiceInstance> workedOutServices = new ArrayList<ServiceInstance>();
		for(ServiceInstance service : services){													  // REVIEW: this cycle could and should be transformed into DAO method
			if(service.getServInstanceStatus().getId() == SERVICEINSTANCE_STATUS.ACTIVE.getId() ||
					service.getServInstanceStatus().getId() == SERVICEINSTANCE_STATUS.PLANNED.getId()){
				actualServices.add(service);
			} else {
				workedOutServices.add(service);
			}
		}
		
		request.setAttribute("actualServices", actualServices);
		request.setAttribute("workedOutServices", workedOutServices);
		
		return PAGES.CUSTOMERSERVICES_PAGE.getValue();
	}

}
