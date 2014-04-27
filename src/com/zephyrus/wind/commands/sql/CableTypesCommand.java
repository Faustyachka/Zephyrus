package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Port;

public class CableTypesCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
//		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
//		ArrayList<Port> ports = portDAO.findAll();
		ArrayList<String> cableTypes = null;
		cableTypes.add("Coaxial");
		cableTypes.add("Fiber optical");
		cableTypes.add("Unshielded Twisted Pair");
		cableTypes.add("Shielded Twisted Pair");
//		for (Port p : ports) {
//			String type = p.getType();
//			if (cableTypes.contains(type) == false) {
//				cableTypes.add(type);
//			}
//		}
		
		request.setAttribute("cableTypes", cableTypes);
		
		return PAGES.CREATECABLE_PAGE.getValue();
	}

}
