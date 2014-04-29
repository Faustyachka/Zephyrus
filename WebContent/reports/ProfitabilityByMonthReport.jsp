<%@page import="com.zephyrus.wind.reports.ProfitabilityByMonthReport"%>
<%@page import="java.sql.Date"%>
<%@page import="com.zephyrus.wind.reports.RouterUtilReport"%>
<%@page import="com.zephyrus.wind.reports.rowObjects.RouterUtilRow"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>Provider Location</td>
			<td>Profit</td>
		</tr>

		<% 	Date date = new Date(2014,1,1);
		if((request.getParameter("dateYear")!=null)&&(request.getParameter("dateMonth")!=null)){
			date.setYear(Integer.parseInt(request.getParameter("dateYear")));
			date.setMonth(Integer.parseInt(request.getParameter("dateMonth")));
		}
		ProfitabilityByMonthReport report = new ProfitabilityByMonthReport(date);
	request.getSession().setAttribute("routerUtil", report);
	for(int i = 0; i<report.getReport().size(); i++){
%>
		<tr>
			<td><%=report.getReport().get(i).getProviderLocation() %></td>
			<td><%=report.getReport().get(i).getProfit() %></td>
		</tr>
		<%
} %>
	</table>
	<a href="/Zephyrus/downloadExel">Convert to Exel</a>
</body>
</html>