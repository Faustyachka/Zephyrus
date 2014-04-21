<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
<center>
<input name="tasks" type="button" value="Tasks" class="button" />
  <br />
  <br />
  <input name="reports" type="button" value="Reports" class="button" />
  <br />
  <br />
  <input name="accounts" type="button" value="Accounts" class="button" />
  </center>
</div>
<div class="main">
<center><h2>Create new account</h2></center>
  <form id="createaccount" name="form1" method="post" action="">
  <table>
  <tr>
  <td>
  <label>First name:	</label></td>
  <td><input type="text" name="name" id="textfield" /></td>
  </tr>
<tr><td>
  <label>Second name:	</label></td><td><input type="text" name="surname" id="textfield" /></td>
  </tr>
  <tr>
  <td>
  <label>E-mail:	</label></td>
  <td><input type="text" name="email" id="textfield" />
  </td>
  </tr>
  <tr>
  <td>
  <label>Password:	</label>
  </td>
  <td><input type="text" name="password" id="textfield" />
  </td>
  </tr>
  <tr>
  <td>
  <label>Confirm password:	</label>
  </td>
  <td><input type="text" name="password2" id="textfield" />
  </td>
  </tr>
  <tr>
  <td>
  <label>User role:	</label>
  </td>
  <td>
  <select name="userrole">
  <option value="1" selected >Customer Support Engineer</option>
  <option value="2" >Provisioning Engineer</option>
  <option value="3" >Installation Engineer</option>
  <option value="4" >Administrator</option>
  </select>
  </td>
  </tr>
  </table>
  <input type="submit" name="button" id="button" value="Save new account" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />