<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Zephyrus</title>
        <link href="/Zephyrus/resources/css/zephyrus_css.css" type="text/css" rel="stylesheet" />
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
		<link rel="shortcut icon" href="/Zephyrus/resources/css/images/icon.png" type="image/x-icon">
		<link rel="icon" href="/Zephyrus/resources/css/images/icon.png" type="image/x-icon">
		<script src="/Zephyrus/resources/javascript/jquery-1.11.0.min.js"></script>
    </head>
    <body>
        <div class="container">
  			<div class="header">
  				<c:choose>
					<c:when test="${user != null}">
					<div id="logout_link">
						<a href="/Zephyrus/logout">
							<input type="button" value="Sign out" class="userbutton">
						</a>
					</div>
					<div  id="home_link">
						<a href="/Zephyrus/login">
				  			<input type="button" value="Profile" class="userbutton">
				  		</a>
				  		<span class="split">|</span>
				  	</div>
				  	<div id="user_name">
					${user.email}
					</div>
					</c:when>
					<c:otherwise>
						<a href="/Zephyrus/login" id="login_link">
	      					<input type="button" value="Log in" class="userbutton"/>
						</a>
						<a href="/Zephyrus/registerPage" id="register_link">
							<input type="button" value="Register" class="userbutton">
							<span class="split">|</span>
						</a>
					</c:otherwise>
				</c:choose>
				<div id = "logo">
	  				<a href="/Zephyrus/home"><img src="/Zephyrus/resources/css/images/zephyrus_logo.png" height="200"></a>
	  			</div>
			</div>