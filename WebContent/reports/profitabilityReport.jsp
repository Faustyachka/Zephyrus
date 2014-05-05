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
		<label>Write date in format YYYY-MM</label></tr>
			<tr>
				<td>Month:</td>
				<td><input type="text" id="month" name="month"
					value="${date}"></td>
			</tr>			
		</table>
		<script>
			function review() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/profitabilityReport";
				form.submit();
			}
		</script>	
		<input type="button" value="Review Report" class="button" onclick="review()">
		<script>
			function downloadExcel() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getExcelProfitability";
				form.submit();
			}
		</script>
		<input type="button" class="button" value="Download Excel" id="excel" name="excel"
			onclick="downloadExcel()"> 
		<script>
			function downloadCSV() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getCSVProfitability";
				form.submit();
			}
		</script>	
		<input type="button"
			value="Download CSV" id="csv" class="button" onclick="downloadCSV()">
	</form>
	<table border="1"
		style="border: 1px solid black; border-collapse: collapse;">
		<tr style="background-color: blue">
			<td>Profit</td>
			<td>Provider Location</td>
		</tr>
		<c:forEach items="${records}" var="record">
			<tr>
				<td>${record.profit}</td>
				<td>${record.providerLocation}</td>
			</tr>
		</c:forEach>
	</table>

</div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />