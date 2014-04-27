package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to create the cable in the system.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful creation of cable
 * @author Ielyzaveta Zubacheva
 */
public class CreateCableCommand extends SQLCommand {

	/**
	 * This method creates the cable in the database. 
	 * Method gets parameters of device's ID and port's ID, which cable will be connected to.
	 * By means of DAO, new object Cable with mentioned parameters is created in the database.
	 * 
	 * @see com.zephyrus.wind.model.Cable
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.model.Port
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
	 * 
	 * @return page with confirmation of successful creation of cable
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String type = request.getParameter("cableType");
		
		// NewScenarioWorkflow wf = new NewScenarioWorkflow(order);
		// wf.createCable(taskID, type);
		
		request.setAttribute("message", "Cable created <br> <a href='/Zephyrus/installation'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
