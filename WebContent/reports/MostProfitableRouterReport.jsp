<%@page import="com.zephyrus.wind.reports.MostProfitableRouterReport"%>
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
	<td>
		Device SN
	</td>
	<td>
		Profit
	</td>
</tr>

<%  MostProfitableRouterReport report = new MostProfitableRouterReport();
	request.getSession().setAttribute("profitRouter", report);
	for(int i = 0; i<report.getReport().size(); i++){
%>
	<tr>
		<td>
			<%=report.getReport().get(i).getRouterSN() %>
		</td>
		<td>
			<%=report.getReport().get(i).getProfit() %>
		</td>
	</tr>
<%
} %>
</table>
<a href="/Zephyrus/downloadRport?type=xls">Convert to Exel</a>
</body>
</html>