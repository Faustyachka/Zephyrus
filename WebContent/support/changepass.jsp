<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Main index</title>
<link href="style.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="container">
		<div class="header">
			<input type="button" name="button" value="Log out" class="logout" />
		</div>
		<div class="navigation">
			<center>
				<input name="reports" type="button" value="Reports" class="button" />
				<br /> <br /> <input name="users" type="button" value="Users"
					class="button" />
			</center>
		</div>
		<div class="main">
			<center>
				<h2>Change password for Customer user</h2>
			</center>
			<label>${error}</label>
			<form id="changepassword" name="form2" method="post" action="/Zephyrus/createnewpass">
				<table>
					<tr>
						<td><label>New password: </label></td>
						<td><input type="password" name="pass" id="pass" /></td>
					</tr>
					<tr>
						<td><label>Confirm password: </label></td>
						<td><input type="password" name="confpass" id="confpass" />
						<input type="hidden" value ="${userId}" id="userId" /></td>
					</tr>
				</table>
				<input type="submit" name="button" id="button"
					value="Change password" class="button" />
					<input type="hidden" id="userId" name="userId" value="${param.id}"/>
					<input type="hidden" id="userId" name="userId2" value="${id}"/>
			</form>
		</div>
		<div class="footer"></div>
	</div>
</body>
</html>