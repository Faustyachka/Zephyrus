<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
<center>
  <input name="reports" type="button" value="Reports" class="button" />
  <br />
  <br />
  <input name="Users" type="button" value="Accounts" class="button" />
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
 						<input type="hidden" value ="${userId}" name="userId" /></td>
 					</tr>
 				</table>
 				<input type="submit" name="button" id="button"
 					value="Change password" class="button" />
 			</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />