<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
<div id="columns">
<div id="navigation">
	<div style="text-align:center"><a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
	<div style="text-align:center"><a href="/Zephyrus/customersupport"> 
	<input name="users" type="button" value="Users" class="navibutton" /></a></div>
	<br>
	<hr>
	<br>
<div style="text-align:center"><a href="/Zephyrus/view/about.jsp"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/services.jsp">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/contacts.jsp">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
		</div>
		<div id="main">
			<center>
				<h2>Change password for Customer user</h2>
			</center>
			<label><font color="red">${error}</font></label>
			<form id="changepassword" name="form2" method="post" action="/Zephyrus/createnewpass">
				<table>
					<tr>
						<td><label>New password: </label></td>
						<td><input type="password" name="password" id="password" maxlength="30"/></td>  <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				Minimal length: 5 symbols
			</td>
					</tr> 
					<tr>
						<td><label>Confirm password: </label></td>
						<td><input type="password" name="confirmpass" id="confirmpass" maxlength="30"/> 
						<input type="hidden" 						
						value =<c:if test= '${param.id != null}'>${param.id}</c:if>
						<c:if test= "${userId != null}">${userId}</c:if>
						 id="userId" name="userId" /></td> <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				Repeat password
			</td>
					</tr>
				</table>
				<input type="submit" name="button" id="button"
					value="Change password" class="button" />
			</form>
	</div></div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />