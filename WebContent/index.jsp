<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Zephyrus</title>
    </head>
    <body>
Main index page
<a href="/Zephyrus/view/register.jsp">register</a>
<a href="/Zephyrus/admin">admin</a>
<a href="/Zephyrus/customersupport">support</a>
<br>
<c:choose>
<c:when test="${username != null}">
<a href="/Zephyrus/login">home</a>
<a href="/Zephyrus/logout">logout</a>
</c:when>
<c:otherwise>
<a href="/Zephyrus/login">login</a>
</c:otherwise>
</c:choose> 
    </body>
</html>



