<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <center><input name="createdevice" type="button" value="Create a Device" class="button" />
  <br />
  <br />
  <input name="createcable" type="button" value="Create a Cable" class="button" />
  <br />
  <br />
  <input name="deletecable" type="button" value="Delete a Cable" class="button" />
  </center></div>
  <div class="main">
  <center>
    <h2>Create a Device</h2></center>
  <form id="createdevice" name="form3" method="post" action="">
    <label>Device ID:	</label><input type="text" name="serialID" id="textfield" />
<br />
<br />
  <input type="submit" name="button" id="button" value="Create a Device" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />