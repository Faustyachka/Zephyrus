<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>

<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <div style="text-align:center"><input name="backtotask" type="button" value="Back to Tasks" class="button" /></div></div>
  <div class="main">
  <div style="text-align:center">
    <h2>Workflow for Order ${order.id} by Task ${id}</h2></div>
    <br>
  <div style="text-align:center">Choose connection properties:</div>
  <br>
  <form method="post" action="/Zephyrus/createConnection">
  <table>
  <tr>
  <td></td><td>Device ${device.id}</td><td>Port ${port.portNumber}</td><td>Cable</td></tr>
  <tr>
  <td></td><td><input type="text" name="deviceNum" id="deviceNum" /></td>
  			<td><input type="text" name="portNum" id="portNum" /></td>
  			<td><input type="text" name="cableID" id="cableID" /></td>
  			</tr>
  <tr>
  <td></td><td><a href="/Zephyrus/installation/createDevice.jsp"> <input type="button"
			value="Create device" class="button" /></a></td><td> </td>
			<td><a href="/Zephyrus/createCable?id=1"><input type="button"
			value="Create cable" class="button" />
		</a></td></tr>
  <tr>
  <td></td><td></td><td></td><td><input type="submit" name="button" id="button" 
  						value="Create connection" class="button"/></td>
  	</table>
  	</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />