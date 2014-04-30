package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;

public class DeleteConnectionPropertiesCommand extends SQLCommand {

	public int id;
	public Cable cable = null;
	public Port port = null;
	public Device device = null;
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		id = (Integer) request.getAttribute("taskId");
						
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(id);
		ServiceOrder order = task.getServiceOrder();
		ServiceLocation loc = order.getServiceLocation();
		
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		cable = cableDAO.findCableFromServLoc(loc.getId());
		
		port = cable.getPort();
		
		device = port.getDevice();
		
		request.getSession().setAttribute("task", id);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("order", order);
		request.setAttribute("cable", cable);
		
		return PAGES.INSTALLATIONDISCONNECTWORKFLOW_PAGE.getValue();
	}


}
