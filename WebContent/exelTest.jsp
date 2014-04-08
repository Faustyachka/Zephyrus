<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="test.ExelExport"  %>
  <%@ page import="java.util.List"  %>
    <%@ page import="java.util.ArrayList"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Write some text there
<%  List<String> list = new ArrayList<String>();
	list.add("Fist");
	list.add("Second");
	list.add("Last");
	ExelExport.writeListToFile("test.xls", list);
	
%>
</body>
</html>