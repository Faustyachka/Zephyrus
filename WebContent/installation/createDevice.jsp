<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <center><input name="backtotask" type="button" value="Back to Task" class="button" />
  </center></div>
  <div class="main">
  <center>
    <h2>Create a Device</h2></center>
  <form id="createdevice" name="createdevice" method="post" action="/Zephyrus/createdevice">
    <label>Device ID:	</label><input type="text" name="serialNum" id="serialNum" />
<br />
<br />
  <input type="submit" name="button" id="button" value="Create a Device" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />