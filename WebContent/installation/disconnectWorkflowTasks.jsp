<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>

<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/installation"> <input type="button"
			value="Back to Tasks" class="button" /></a></div></div>
  <div class="main">
  <div style="text-align:center">
    <h2>Workflow for Order ${order.id} by Task ${task.id}</h2></div>
    <br>
  <div style="text-align:center">Connection properties:</div>
  <br>
  <label>${message}</label>
  <form method="post" action="/Zephyrus/deleteConnection?taskId=${task.id}">
  <table>
  <tr>
  <th width="300">Device ID</th><th width="300">Port ID</th><th width="300">Cable ID</th></tr>
  <tr>
  <td align="center">${device.id}</td><td align="center">${port.id}</td><td align="center">${cable.id}</td>
  </tr>
  <tr>
  <td></td></tr>
  <tr>
  </tr>
  <tr>
  <td></td><td></td><td></td><td align="center"><c:if test= "${device.id != null}"><c:if test = "${cable.id != null}"><input type="submit" name="button" id="button" 
  						value="Delete connection" class="button"/></c:if></c:if></td>
  	</table>
  	</form>
  	<c:if test= "${device == null}">
  	<c:if test="${port==null}">
  	Delete cable ${cable.id} from service location: ${order.serviceLocation.address}
  	<a href="/Zephyrus/deleteCable?taskId=${task.id}">
  	<input type="button" class="button" value="Delete Cable"/>
  	</a>
  	</c:if> 	
  	</c:if>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
