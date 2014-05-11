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
	<div style="text-align: center">
		<a href="/Zephyrus/provision"> <input name="tasks" type="button"
			value="Back to Tasks" class="navibutton" /></a>
	</div>
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
	<center>
		<h2>Modify Circuit</h2>
	</center>
	<div>
		<font color="red"> ${error} </font>
	</div>
	<form method="post" action="/Zephyrus/modifyCircuit">
		<table>
			<tr>
				<td>Task id</td>
				<td>${task.id}</td>
			</tr>
			<tr>
				<td>Order date</td>
				<td>${task.serviceOrder.orderDate}</td>
			</tr>
			<tr>
				<td>Service Name</td>
				<td>${task.serviceOrder.productCatalog.serviceType.serviceType }
				</td>
			</tr>
			<tr>
				<td>Port number</td>
				<td>${port.portNumber}</td>
			</tr>
			<tr>
				<td>Device serial number</td>
				<td>${port.device.serialNum}</td>
			</tr>
			<tr>
				<td>Customer's address:</td>
				<td>${task.serviceOrder.serviceLocation.address}</td>
			</tr>

		</table>
		<label>Circuit configuration: ${circuit.config}</label> <br> <font
			style="font-style: italic;"><label>Write down the
				IP-address in format xxx.xxx.xxx.xxx where x - is the number symbol</label></font>
		<br> <input type="hidden" name="taskId" value="${task.id}" /> <input
			type="text" id="circuit" name="circuit" /> <br> <input
			type="submit" name="button" id="button" value="Modify Circuit"
			class="button" />
	</form>
</div></div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />