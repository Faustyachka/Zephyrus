package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
																											// REVIEW: documentation expected
public class GenerateReportCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		//TODO																								// REVIEW: TO DO what?
		return PAGES.REPORT_PAGE.getValue();
	}

}
