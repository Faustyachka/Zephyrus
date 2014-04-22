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
<form id="createdevice" name="form3" method="post" action="">
  <label>Group Users by:	</label><select name="usersparameter">
  <option value="1">Parameter1</option>
  <option value="2">Parameter2</option>
  </select>
  <
  <p>
    <label>
      <input type="radio" name="RadioGroup1" value="user1" id="RadioGroup1_0" />
      User1</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user2" id="RadioGroup1_1" />
      User2</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user3" id="RadioGroup1_2" />
      User3</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user4" id="RadioGroup1_3" />
      User4</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user5" id="RadioGroup1_4" />
      User5</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user6" id="RadioGroup1_5" />
      User6</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user7" id="RadioGroup1_6" />
      User7</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user8" id="RadioGroup1_7" />
      User8</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user9" id="RadioGroup1_8" />
      User9</label>
    <br />
    <label>
      <input type="radio" name="RadioGroup1" value="user10" id="RadioGroup1_9" />
      User10</label>
    <br />
  </p>
  <br />
<br />
  <input type="submit" name="button" id="button" value="Orders and Service Instances" height="30"/>
   <input type="submit" name="button" id="button" value="Change password" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />