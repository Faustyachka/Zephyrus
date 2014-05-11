<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>

<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/installation"> <input type="button"
			value="Back to Tasks" class="navibutton" /></a></div>
			<br>
			<hr>
			<br>
<div style="text-align:center"><a href="/Zephyrus/view/about.jsp"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/services.jsp">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/contacts.jsp">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
			</div>
  <div class="main">
  <div style="text-align:center">
    <h2>Workflow for Order ${order.id} by Task ${task.id}</h2></div>
    <br>
    <div><font color="green"> ${message} </font>
    <font color="red"> ${error} </font>
    </div>
    <br>
    <c:if test= "${device != null}">
  	<c:if test="${port!=null}">
  <div style="text-align:center">Connection properties:</div>
  <br>
 
  
  <form method="post" action="/Zephyrus/deleteConnection">
  <input type="hidden" name="taskId" value="${task.id}"/>
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
  	</c:if> 
  	</c:if> 
  	<c:if test= "${device == null}">
  	<c:if test="${port==null}">
 	<c:if test="${cable!=null}"> 
  	Delete cable ${cable.id} from service location: ${order.serviceLocation.address} <br>
  	<form method="post" action="/Zephyrus/deleteCable">
  	<input type="hidden" name="taskId" value="${task.id}"/> 
  	<input type="submit" class="button" value="Delete Cable"/>
  	</form>
  	
  	</c:if> 
  	</c:if> 	
  	</c:if> 
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
