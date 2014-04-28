package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;


public class NewConnectionPropertiesCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		Order order = (Order) request.getSession().getAttribute("order");
		
//		IDeviceDAO deviceDAO = getOracleDaoFactory().getDeviceDAO();
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		
		Port port = portDAO.findById(portDAO.findFreePortID());
		Device device = port.getDevice();
		
		ArrayList<Cable> cables = cableDAO.findAll();
		ArrayList<Cable> availableCables = new ArrayList<Cable>();;
		for (Cable c : cables) {
			if (c.getPort().equals(null)) {
				availableCables.add(c);
			}
		}
		
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("availableCables", availableCables);
		request.setAttribute("order", order);
		
		return PAGES.INSTALLATIONNEWWORKFLOW_PAGE.getValue();
	}


}
