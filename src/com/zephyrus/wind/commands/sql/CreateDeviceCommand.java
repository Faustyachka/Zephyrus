package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Task;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to create new device in the system.
 * 
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful creation of device
 * @author Ielyzaveta Zubacheva
 */

public class CreateDeviceCommand extends SQLCommand {

	/**
	 * This method creates the device in the database. 
	 * Method gets parameter of device's serial number.
	 * By means of DAO, new object Device with mentioned parameter is created in the database.
	 * 
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
	 * 
	 * @return page with confirmation of successful deletion of cable
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String serialID = request.getParameter("serialID");
		if (serialID.isEmpty()) {
			request.setAttribute("error", "Serial number cannot be empty");
			request.setAttribute("SerialID", serialID);
			return "installation/createDevice.jsp";
		}
		Device device = new Device();
		device.setSerialNum(serialID);
		IDeviceDAO dao = getOracleDaoFactory().getDeviceDAO();
		dao.insert(device);
		
//		int orderId = Integer.parseInt(request.getParameter("orderId"));
//		ServiceOrder so = new ServiceOrder();
//		IServiceOrderDAO dao = oracleDaoFactory.getServiceOrderDAO();
//		so = dao.findById(orderId);
//		Task task = new Task();
//		task.setServiceOrder(so);
//		UserRole role = new UserRole();
//		IUserRoleDAO daoRole = oracleDaoFactory.getUserRoleDAO();
//		role = daoRole.findById("4");
//		task.setRole(role);
//		task.setTaskValue("Create Cable");
		
		request.setAttribute("message", "Device created <br> <a href='/Zephyrus/installation'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
