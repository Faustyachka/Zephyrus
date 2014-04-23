package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.enums.Pages;

/**
 * 
 * @author Ielyzaveta Zubacheva
 *
 */

public class CreateDeviceCommand extends SQLCommand {

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
		IDeviceDAO dao = oracleDaoFactory.getDeviceDAO();
		dao.insert(device);
		request.setAttribute("message", "Device created <br> <a href='/Zephyrus/installation'>return to home page</a>");		
		return Pages.MESSAGE_PAGE.getValue();
	}

}