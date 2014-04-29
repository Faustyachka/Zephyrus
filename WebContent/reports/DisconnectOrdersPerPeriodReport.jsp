<%@page
	import="com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport"%>
<%@page import="com.zephyrus.wind.reports.MostProfitableRouterReport"%>
<%@page import="com.zephyrus.wind.reports.RouterUtilReport"%>
<%@page
	import="com.zephyrus.wind.reports.rowObjects.MostProfitableRouterRow"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
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
			<td>Username</td>
			<td>Order ID</td>
			<td>Order Status</td>
			<td>Product name</td>
			<td>Provider location</td>
		</tr>

		<%  
	Date startDate = new Date(2014,1,1);
	Date endDate = new Date(2015,1,1);
	if((request.getParameter("startYear")!=null)&&(request.getParameter("startMonth")!=null)&&(request.getParameter("startDay")!=null)){
		startDate.setYear(Integer.parseInt(request.getParameter("startYear")));
		startDate.setMonth(Integer.parseInt(request.getParameter("startMonth")));
		startDate.setDate(Integer.parseInt(request.getParameter("startDay")));
	}
	if((request.getParameter("endYear")!=null)&&(request.getParameter("endMonth")!=null)&&(request.getParameter("endtDay")!=null)){
		startDate.setYear(Integer.parseInt(request.getParameter("endYear")));
		startDate.setMonth(Integer.parseInt(request.getParameter("endMonth")));
		startDate.setDate(Integer.parseInt(request.getParameter("endDay")));
	}
	DisconnectOrdersPerPeriodReport report = new DisconnectOrdersPerPeriodReport(startDate,endDate);
	request.getSession().setAttribute("disconnRepo", report);
	for(int i = 0; i<report.getReport().size(); i++){
%>
		<tr>
			<td><%=report.getReport().get(i).getUsername() %></td>
			<td><%=report.getReport().get(i).getOrderID() %></td>
			<td><%=report.getReport().get(i).getOrderStatus() %></td>
			<td><%=report.getReport().get(i).getProductName() %></td>
			<td><%=report.getReport().get(i).getProviderLocation() %></td>
		</tr>
		<%
} %>
	</table>
	<a href="/Zephyrus/downloadReport?type=xls">Convert to Exel</a>
</body>
</html>