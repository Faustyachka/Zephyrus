package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;																

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.dao.oracleImp.OracleServiceOrderDAO;										    // REVIEW: unused imports found
import com.zephyrus.wind.dao.oracleImp.OracleUserRoleDAO;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Task;

/**
 * 																										// REVIEW: documentation expected
 * @author Ielyzaveta Zubacheva
 *
 */

public class CreateDeviceCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String serialID = request.getParameter("serialID");
		if (serialID.isEmpty()) {																		// REVIEW: serialID null check expected
			request.setAttribute("error", "Serial number cannot be empty");
			request.setAttribute("SerialID", serialID);													// REVIEW: SerialID - first letter should be in lower case
			return "installation/createDevice.jsp";
		}
		Device device = new Device();																	// REVIEW: device addition should be performed in Workflow class
		device.setSerialNum(serialID);
		IDeviceDAO dao = getOracleDaoFactory().getDeviceDAO();
		dao.insert(device);
		
//		int orderId = Integer.parseInt(request.getParameter("orderId"));								// REVIEW: is this code valid?
//		ServiceOrder so = new ServiceOrder();
//		OracleServiceOrderDAO dao = oracleDaoFactory.getServiceOrderDAO();
//		so = dao.findById(orderId);
//		Task task = new Task();
//		task.setServiceOrder(so);
//		UserRole role = new UserRole();
//		OracleUserRoleDAO daoRole = oracleDaoFactory.getUserRoleDAO();
//		role = daoRole.findById("4");
//		task.setRole(role);
//		task.setTaskValue("Create Cable");
		
		request.setAttribute("message", "Device created <br> <a href='/Zephyrus/installation'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
