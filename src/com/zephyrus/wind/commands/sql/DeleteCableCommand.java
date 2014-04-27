/**
 * 
 */
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
 * It is supposed to delete the cable from the system.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful deletion of cable
 * @author Ielyzaveta Zubacheva
 */
public class DeleteCableCommand extends SQLCommand {
	
	/**
	 * This method deletes chosen cable from the database. 
	 * Method gets parameters of device's ID and port's ID, which cable is connected to.
	 * By means of DAO, chosen object Cable with mentioned parameters is deleted from database.
	 * 
	 * @see com.zephyrus.wind.model.Cable
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.model.Port
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
	 * 
	 * @return page with confirmation of successful deletion of cable
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String deviceID = request.getParameter("deviceID");
		String portNum = request.getParameter("portNum");
		if (deviceID.isEmpty()) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Device should be chosen.");
			return "installation/deleteCable.jsp";
		}
		if (portNum.isEmpty()) {
			response.setContentType("text/plain");  
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write("Port for cable should be chosen.");
			return "installation/deleteCable.jsp";
		}
		Cable cable = new Cable();
		
		ServiceLocation so = new ServiceLocation();
		
		Device device = new Device();
		IDeviceDAO devicedao = getOracleDaoFactory().getDeviceDAO();
		device = devicedao.findById(Integer.parseInt(deviceID));
		
		Port port = new Port();
		port.setPortNumber(Integer.parseInt(portNum));
		port.setDevice(device);
		
		cable.setPort(port);
		cable.setServiceLocation(so);
		ICableDAO dao = getOracleDaoFactory().getCableDAO();
		dao.remove(cable);
		
		request.setAttribute("message", "Cable deleted <br> <a href='/Zephyrus/installation'>return to home page</a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
