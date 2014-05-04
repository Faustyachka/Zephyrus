<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
	<center>
		 <br /> 
		 <a href="/Zephyrus/provision">
		<input name="tasks" type="button" value="Back to Tasks" class="navibutton" /> 
		</a>
	</center>
</div>
<div class="main">
	<center>
		<h2>Create Circuit</h2>
	</center>
	<div><font color="red"> ${message} </font></div>
	<form method="post" action="/Zephyrus/createCircuit">
	<table>
	<tr>
	<td>Task id</td>
	<td>${task.id}</td>
	</tr>
	<tr> <td> Order date</td>
	<td> ${task.serviceOrder.orderDate} </td>
	</tr>
	<tr>
	<td> Service Name </td>
	<td> ${task.serviceOrder.productCatalog.serviceType.serviceType } </td>
	</tr>
	<tr>
	<td> Port number </td>
	<td> ${port.portNumber} </td>
	</tr>
	<tr>
	<td> Device serial number </td>
	<td> ${port.device.serialNum} </td>
	</tr>
	<tr>
	<td> Customer's address: </td>
	<td> ${task.serviceOrder.serviceLocation.address} </td>
	</tr>
	
	</table>
		<label>Circuit configuration: </label>
		<input type="hidden" name="taskId" value="${task.id}"/>
		<input type = "text" id="circuit" name="circuit"/>
		 <input type="submit" name="button" id="button"
			value="Create Circuit" class="button" />
	</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />