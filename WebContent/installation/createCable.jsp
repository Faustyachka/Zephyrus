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
    <h2>Create a Cable</h2></center>
  <form id="createcable" name="createcable" method="post" action="">
    <label>Choose device:	</label><input type="text" name="deviceID" id="textfield" />
<br />
<label>Choose port:	</label><input type="text" name="portID" id="textfield" />
<br />
  <input type="submit" name="button" id="button" value="Create a Cable" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />