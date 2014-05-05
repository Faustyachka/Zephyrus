<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
<div class="navigation"></div>
<br>
<div class="main">
	<br> <br>
	<form id="form" method="post" action="">
	<label><font color="red">${message}</font></label><br>
		<table>
		<tr>
		<label>Write dates in format YYYY-MM-DD</label></tr>
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
		<input type="button" value="Review Report" class="button" onclick="review()">
		<script>
			function downloadExcel() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getExcelDisconnectOrders";
				form.submit();
			}
		</script>
		<input type="button" class="button" value="Download Excel" id="excel" name="excel"
			onclick="downloadExcel()"> 
		<script>
			function downloadCSV() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getCSVDisconnectOrders";
				form.submit();
			}
		</script>	
		<input type="button"
			value="Download CSV" id="csv" class="button" onclick="downloadCSV()">
	</form>
	<table border="1"
		style="border: 1px solid black; border-collapse: collapse;">
		<tr style="background-color: blue">
			<td>User Name</td>
			<td>Order ID</td>
			<td>Order status</td>
			<td>Product Name</td>
			<td>Provider Location</td>
		</tr>
		<c:forEach items="${records}" var="record">
			<tr>
				<td>${record.username}</td>
				<td>${record.orderID }</td>
				<td>${record.orderStatus }</td>
				<td>${record.productName }</td>
				<td>${record.providerLocation }</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/Zephyrus/disconnectOrders?last=${last}"> <input type="button"
		value="Next page" />
	</a>

</div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />