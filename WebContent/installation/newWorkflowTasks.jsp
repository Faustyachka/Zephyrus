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
			<div style="text-align:center"><a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
</div>
<div class="main">
  <div style="text-align:center">
    <h2>Workflow for Order ${task.serviceOrder.id} by Task ${task.id}</h2>
    </div>
    <br>
    <div><font color="red"> ${error} </font>
<font color="green"> ${message} </font></div> <br>
  <div style="text-align:center">Choose connection properties:</div>
  <br>
  <form method="post" action="/Zephyrus/createConnection?taskId=${task.id}&port=${port.id}&cable=${cable.id}">
  <table>
  <tr>
  <th width="300">Device serial number</th><th width="300">Port ID</th><th width="300">Cable ID</th></tr>
  <tr>
  <td align="center">${device.serialNum}</td><td align="center">${port.portNumber}</td><td align="center">${cable.id}</td>
  </tr>
  <tr>
  <td align="center"><c:if test= "${device.id == null}">
  <a href="/Zephyrus/deviceCreationProperties?taskId=${task.id}"> 
  <input type="button" value="Create device" class="button" />
  </a>
  </c:if>
  </td> <td> </td>
		<td align="center"><c:if test= "${cable.id == null}">
		<c:if test = "${device.id != null}">
		<a href="/Zephyrus/createCable?taskId=${task.id}">
		<input type="button" value="Create cable" class="button" />
		</a>
		</c:if>
		</c:if>
		</td>
		</tr>
  <tr>
  </tr>
  <tr>
  <td></td><td></td><td></td>
  <td align="center"><c:if test= "${device.id != null}">
  <c:if test = "${cable.id != null}">
  <input type="submit" name="button" id="button" value="Create connection" class="button"/>
  </c:if>
  </c:if>
  </td>
  	</table>
  	</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
