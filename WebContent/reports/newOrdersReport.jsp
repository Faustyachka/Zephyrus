<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
<div class="navigation"></div>
<div class="main">

	<br> <br>
	<a href="/Zephyrus/reportChoosing">
	<input type="button" class="button" value="Back to Reports"/>
	</a>
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
				form.action = "/Zephyrus/newOrders?last=1";
				form.submit();
			}
		</script>
		<input type="button" value="Review Report" class="button" onclick="review()">
		<script>
			function downloadExcel() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getExcelNewOrders";
				form.submit();
			}
		</script>
		<input type="button" class="button" value="Download Excel" id="excel" name="excel"
			onclick="downloadExcel()"> 
		<script>
			function downloadCSV() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getCSVNewOrders";
				form.submit();
			}
		</script>	
		<input type="button"
			value="Download CSV" id="csv" class="button" onclick="downloadCSV()">
	</form>
	<table border="1"
		style="border: 1px solid black; border-collapse: collapse; width: 100%">
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
	<table style="width: 100%">
	<tr>
	<td style="width: 50%;" align="left">
	<c:if test="${last > (count*2)}">
	<a href="/Zephyrus/newOrders?last=${last-(count*2)}"> <input type="button"
		value="Previous page" class="button"/>
	</a>
	</c:if>
	</td>
	<td style="width: 50%" align="right">
	<c:if test="${next==1}">
	<a href="/Zephyrus/newOrders?last=${last}"> <input type="button"
		value="Next page" class="button" />
	</a>
	</c:if>
	</td>
	</tr>
	</table>
	
</div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />