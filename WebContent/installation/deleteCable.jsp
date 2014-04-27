<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <center><input name="backtotask" type="button" value="Back to Task" class="button" />
  </center></div>
  <div class="main">
  <center><h2>Delete a Cable</h2></center>
  <form id="deletecable" name="deletecable" method="post" action="/Zephyrus/deletecable">
  <table>
  <tr>
  <td>Choose device:</td><td><input type="text" name="deviceID" id="deviceID" /></td></tr>
  <tr>
  <td>Choose port:</td><td><input type="text" name="portNum" id="portNum" /></td></tr>
  <tr>
  <td></td><td><input type="submit" name="button" id="button" value="Delete a Cable" class="button"/></td>
  	</table>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />