<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Zephyrus</title>
        <link href="/Zephyrus/resources/css/zephyrus_css.css" type="text/css" rel="stylesheet" />
		<script src="/Zephyrus/resources/javascript/jquery-1.11.0.min.js"></script>
    </head>
    <body>
        <div class="container">
  <div class="header">
  <c:choose>
<c:when test="${user != null}">
<div id="logout_link">
Hello, ${user.email}
<br>
<a href="/Zephyrus/logout">logout</a>
</div>
</c:when>
<c:otherwise>
<a href="/Zephyrus/login" id="login_link">
      <input type="button" value="Log in" />
</a>
<a href="/Zephyrus/registerPage" id="register_link">register</a>
</c:otherwise>
</c:choose> 
  <a href="/Zephyrus/home" id="main_link">
  <input type="button" value="Main">
  </a>
  <c:if test="${user != null }">
   <a href="/Zephyrus/login" id="home_link">
  <input type="button" value="Home">
  </a>
  </c:if>
  </div>