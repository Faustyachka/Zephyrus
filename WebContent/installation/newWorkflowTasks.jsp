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
  <form method="post" action="/Zephyrus/createConnection?id=${id}&port=${port.id}&cable=${cable.id}">
  <table>
  <tr>
  <th width="300">Device ID</th><th width="300">Port ID</th><th width="300">Cable ID</th></tr>
  <tr>
  <td align="center">${device.id}</td><td align="center">${port.id}</td><td align="center">${cable.id}</td>
  			</tr>
  <tr>
  <td align="center">
  <a href="/Zephyrus/installation/createDevice.jsp"> <input type="button"
			value="Create device" class="button" /></a></td><td> </td>
			<td align="center"><a href="/Zephyrus/createCable"><input type="button"
			value="Create cable" class="button" />
		</a></td></tr>
  <tr>
  </tr>
  <tr>
  <td></td><td></td><td></td><td align="center"><input type="submit" name="button" id="button" 
  						value="Create connection" class="button"/></td>
  	</table>
  	</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />