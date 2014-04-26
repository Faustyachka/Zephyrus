<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <center><input name="backtotask" type="button" value="Back to Tasks" class="button" />
  </center></div>
  <div class="main">
  <center>
    <h2></h2></center>
  <form id="newconnection" name="newconnection" method="post" action="/Zephyrus/newconnection">
  <center>Choose connection properties:</center>
  <table>
  <tr>
  <td></td><td>Device</td><td>Port</td><td>Cable</td></tr>
  <tr>
  <td></td><td> </td><td> </td><td> </td></tr>
  <tr>
  <td></td><td><a href="/Zephyrus/installation/createDevice.jsp"> <input type="button"
			value="Create device" class="button" /></a></td><td> </td>
			<td><a href="/Zephyrus/installation/createCable.jsp"><input type="button"
			value="Create cable" class="button" />
		</a></td></tr>
  <tr>
  <td></td><td></td><td></td><td><input type="submit" name="button" id="button" value="Create connection" class="button"/></td>
  	</table>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />