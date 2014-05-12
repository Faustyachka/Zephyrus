<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="/Zephyrus/resources/javascript/dataValidation.js">
	
</script>
<div id="columns">
<div id="navigation">
	<div style="text-align: center">
		<a href="/Zephyrus/reportChoosing"> <input type="navibutton"
			class="button" value="Back to Reports" /></a>
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
	<h3>Disconnect orders per period report</h3>
	<form id="form" method="post" action="">
		<label><font color="red">${message}</font></label><br>
		<table>
			<tr>
				<label>Write dates in format YYYY-MM-DD</label>
			</tr>
			<tr>
				<td>From date:</td>
				<td><input type="text" id="from" name="from"
					value="${fromDate}"></td>
			</tr>
			<tr>
				<td>To date:</td>
				<td><input type="text" id="to" name="to" value="${toDate}">
				</td>
			</tr>
		</table>
		<script>
			function review() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/disconnectOrders?last=1";
				form.submit();
			}
		</script>
		<br>
		<input type="button" value="Show Report" class="button"
			onclick="review()">
		<script>
			function downloadExcel() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getExcelDisconnectOrders";
				form.submit();
			}
		</script>
		<input type="button" class="button" value="Download Excel" id="excel"
			name="excel" onclick="downloadExcel()">
		<script>
			function downloadCSV() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getCSVDisconnectOrders";
				form.submit();
			}
		</script>
		<input type="button" value="Download CSV" id="csv" class="button"
			onclick="downloadCSV()">
	</form>
	<br />
	
	<c:if test="${not empty records}">
		<table style="border-collapse: collapse; width: 100%; padding: 10px;">
			<tr>
				<td align="center" style="background-color: #3a75c4; color: white;">User Name</td>
				<td align="center" style="background-color: #3a75c4; color: white;">Order ID</td>
				<td align="center" style="background-color: #3a75c4; color: white;">Order status</td>
				<td align="center" style="background-color: #3a75c4; color: white;">Product Name</td>
				<td align="center" style="background-color: #3a75c4; color: white;">Provider Location</td>
			</tr>
			<c:forEach items="${records}" var="record">
				<tr>
					<td align="center" style="border: 1px solid #3a75c4;">${record.username}</td>
					<td align="center" style="border: 1px solid #3a75c4;">${record.orderID }</td>
					<td align="center" style="border: 1px solid #3a75c4;">${record.orderStatus }</td>
					<td align="center" style="border: 1px solid #3a75c4;">${record.productName }</td>
					<td align="center" style="border: 1px solid #3a75c4;">${record.providerLocation }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${records != null and empty records}">
		Not enough data to form report
	</c:if>
	<table style="width: 100%">
		<tr>
			<td style="width: 50%;" align="left">
				<c:if test="${last > (count*2)}">
					<a href="/Zephyrus/newOrders?last=${last-(count*2)}"> 
						<input type="button" value="Previous page" class="button" />
					</a>
				</c:if>
				</td>
			<td style="width: 50%" align="right">
				<c:if test="${hasNext=='exist'}">
					<a href="/Zephyrus/newOrders?last=${last}"> 
						<input type="button" value="Next page" class="button" />
					</a>
				</c:if>
			</td>
		</tr>
	</table>

</div></div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />