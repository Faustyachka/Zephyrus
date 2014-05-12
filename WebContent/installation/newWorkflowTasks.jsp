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
<div id="columns">
<div id="navigation">
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
<div id="main">
  <div style="text-align:center">
    <h2>Workflow for Order at ${task.serviceOrder.serviceLocation.address} by Task "${task.task_value}"</h2>
    </div>
    <br>
    <div><font color="red"> ${error} </font>
<font color="green"> ${message} </font></div>
  <div style="text-align:center">Choose connection properties:</div>
  <br>
  <form method="post" action="/Zephyrus/createConnection?taskId=${task.id}&port=${port.id}&cable=${cable.id}">
  <table>
  <tr>
  <th width="300">Device serial number</th><th width="300">Port ID</th><th width="300">Cable ID</th></tr>
  <tr>
  <td align="center"><c:choose>
  <c:when test= "${device.id == null}"><img src="/Zephyrus/resources/css/images/no.png"></c:when>
  <c:otherwise><img src="/Zephyrus/resources/css/images/yes.png"></c:otherwise></c:choose>
  </td>
  <td align="center"><c:choose>
  <c:when test= "${port.portNumber == null}"><img src="/Zephyrus/resources/css/images/no.png"></c:when>
  <c:otherwise><img src="/Zephyrus/resources/css/images/yes.png"></c:otherwise></c:choose>
  </td>
  <td align="center"><c:choose>
  <c:when test= "${cable.id == null}"><img src="/Zephyrus/resources/css/images/no.png"></c:when>
  <c:otherwise>><img src="/Zephyrus/resources/css/images/yes.png"></c:otherwise></c:choose>
  </td>
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
</div></div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
