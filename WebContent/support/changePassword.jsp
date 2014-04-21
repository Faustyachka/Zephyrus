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
    <h2>Change password for Customer user</h2></center>
  <form id="changepassword" name="form2" method="post" action="">
  <table>
  <tr>
  <td>
    <label>New password:	</label>
    </td>
    <td>
    <input type="text" name="password" id="textfield" />
</td>
</tr>
<tr>
<td>
  <label>Confirm password:	</label>
  </td>
  <td><input type="text" name="password2" id="textfield" />
  </td>
  </tr>
  </table>
  <input type="submit" name="button" id="button" value="Change password" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />