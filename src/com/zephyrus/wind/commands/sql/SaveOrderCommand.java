package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.ServiceOrder;

public class SaveOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IServiceOrderDAO orderDAO = oracleDaoFactory.getServiceOrderDAO();
		orderDAO.insert((ServiceOrder)request.getSession().getAttribute("order"));
		request.setAttribute("message", "Order successfuly saved!");
		return Pages.MESSAGE_PAGE.getValue();
	}

}
