<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="/Zephyrus/resources/javascript/dataValidation.js">
	
</script>
<div id="columns">
<div id="navigation">
	<div style="text-align: center">
		<a href="/Zephyrus/reportChoosing"> <input type="button"
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
	<h3>Profitability by month report</h3>
	<form id="form" method="post" action="">
		<label><font color="red">${message}</font></label><br>
		<table>
			<tr>
				<label>Write date in format YYYY-MM</label>
			</tr>
			<tr>
				<td>Month:</td>
				<td><input type="text" id="month" name="month" value="${date}"></td>
			</tr>
		</table>
		<script>
			function review() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/profitabilityReport?last=1";
				form.submit();
			}
		</script>
		<input type="button" value="Review Report" class="button"
			onclick="review()">
		<script>
			function downloadExcel() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getExcelProfitability";
				form.submit();
			}
		</script>
		<input type="button" class="button" value="Download Excel" id="excel"
			name="excel" onclick="downloadExcel()">
		<script>
			function downloadCSV() {
				var form = document.getElementById("form");
				form.action = "/Zephyrus/getCSVProfitability";
				form.submit();
			}
		</script>
		<input type="button" value="Download CSV" id="csv" class="button"
			onclick="downloadCSV()">
	</form>
	<br />
	<c:if test="${not empty records}">
		<table style="border-collapse: collapse; width: 60%; padding: 10px;">
			<tr style="background-color: blue">
				<td align="center" style="background-color: #3a75c4; color: white;">Profit</td>
				<td align="center" style="background-color: #3a75c4; color: white;">Provider Location</td>
			</tr>
			<c:forEach items="${records}" var="record">
				<tr>
					<td align="center" style="border: 1px solid #3a75c4;">${record.profit}$</td>
					<td align="center" style="border: 1px solid #3a75c4;">${record.providerLocation}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${records != null and empty records}">
		Not enough data to form report
	</c:if>
	
	<table style="width: 100%">
		<tr>
			<td style="width: 50%;" align="left"><c:if
					test="${last > (count*2)}">
					<a href="/Zephyrus/profitabilityReport?last=${last-(count*2)}"> <input
						type="button" value="Previous page" class="button" />
					</a>
				</c:if></td>
			<td style="width: 50%" align="right"><c:if test="${hasNext==1}">
					<a href="/Zephyrus/profitabilityReport?last=${last}"> <input
						type="button" value="Next page" class="button" />
					</a>
				</c:if></td>
		</tr>
	</table>
	
</div></div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />