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
							<a href="/Zephyrus/logout"><input type="button" value="Sign out" class="userbutton"></a>
						</div>
					<div  id="home_link">
						<a href="/Zephyrus/login">
				  		<input type="button" value="Profile" class="userbutton">
				  		</a></div>
					</c:when>
					<c:otherwise>
						<a href="/Zephyrus/login" id="login_link">
	      				<input type="button" value="Log in" class="userbutton"/>
						</a>
						<a href="/Zephyrus/registerPage" id="register_link">
						<input type="button" value="Register" class="userbutton"></a>
					</c:otherwise>
				</c:choose> 
  <a href="/Zephyrus/home"><img src="/Zephyrus/resources/css/images/main_logo.jpg"></a>

</div>