package com.zephyrus.wind.commands.sql;


import java.sql.SQLException;																


import java.util.ArrayList;

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
 * 	
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
		String serialNum = request.getParameter("serialNum");
		if (serialNum.isEmpty()) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Serial number cannot be empty.");
			return "installation/createDevice.jsp";
		}
		int counter = 0;
		Device device = new Device();																	// REVIEW: device addition should be performed in Workflow class
		device.setSerialNum(serialNum);
		IDeviceDAO dao = getOracleDaoFactory().getDeviceDAO();
		ArrayList<Device> devices = dao.findAll();
		for (Device d: devices) {
			if (d.getSerialNum().equals(serialNum)) {
				counter++;
			}
		}
		if (counter>0) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("The device with such serial number already exists in system"); 
		    return null;
		}
		device.setSerialNum(serialNum);
		dao.insert(device);
		
		request.setAttribute("message", "Device created <br> <a href='/Zephyrus/installation/newWorkflowTasks.jsp'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
