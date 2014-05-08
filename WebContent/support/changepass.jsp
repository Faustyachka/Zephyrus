<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
		<div class="navigation">
	<div style="text-align:center"><a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
	<div style="text-align:center"><a href="/Zephyrus/customersupport"> 
	<input name="users" type="button" value="Users" class="navibutton" /></a></div>
	<br>
	<hr>
	<br>
	<div style="text-align:center"><a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
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
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />