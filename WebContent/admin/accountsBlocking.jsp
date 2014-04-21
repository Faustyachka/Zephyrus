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
<form id="createdevice" name="form3" method="post" action="">
  <p>
    <label>Group Users by:	</label>
    <select name="usersparameter">
      <option value="1">Parameter1</option>
      <option value="2">Parameter2</option>
    </select>
  </p>
  
  <table>
    <tr><td><label>Blocked users</label></td>
  <td><label>User's name</label></td></tr>
  <tr>
  <td>
    <label>
      <input type="checkbox" name="CheckboxGroup1" value="user1" id="CheckboxGroup1_0" />
    </label></td>
    <td>
    <label>User1</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user2" id="CheckboxGroup1_1" /></label></td>
    <td>
    <label>User2</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user3" id="CheckboxGroup1_2" /></label></td>
    <td>
    <label>User3</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user4" id="CheckboxGroup1_3" /></label></td>
    <td>
    <label>User4</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user5" id="CheckboxGroup1_4" /></label></td>
    <td>
    <label>User5</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user6" id="CheckboxGroup1_5" checked="checked"/></label></td>
    <td>
    <label>User6</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user7" id="CheckboxGroup1_6" /></label></td>
    <td>
    <label>User7</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user8" id="CheckboxGroup1_7" checked="checked"/></label></td>
    <td>
    <label>User8</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user9" id="CheckboxGroup1_8" checked="checked"/></label></td>
    <td>
    <label>User9</label></td></tr>
    <tr>
    <td><label>
      <input type="checkbox" name="CheckboxGroup1" value="user10" id="CheckboxGroup1_9" /></label>
      <td>
    <label>User10</label></td></tr></table>
   <input type="submit" name="button" value="Save changes" class="button"/>
   <br>
   <br>
   <input type="button" value="Create account" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />