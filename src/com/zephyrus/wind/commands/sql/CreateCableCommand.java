package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

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
	 * By means of workflow, new object Cable with mentioned parameters is created in the database.
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
		int id = (Integer) request.getAttribute("id");
		int portNum = (Integer) request.getAttribute("port");
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		Port port = portDAO.findById(portNum);
		Device device = port.getDevice();
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(id);
		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = new NewScenarioWorkflow(order);
		wf.createCable(id);
		
		Cable cable = new Cable();
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		cable = cableDAO.findCableFromServLoc((order.getServiceLocation()).getId());
		
		request.setAttribute("taskId", id);
		request.setAttribute("cable",cable);
		return "newConnectionProperties";
		}

}
