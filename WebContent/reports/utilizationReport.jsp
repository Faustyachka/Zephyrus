<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="/Zephyrus/resources/javascript/dataValidation.js">
	
</script>
<div class="navigation">
	<div style="text-align:center"><a href="/Zephyrus/reportChoosing">
	<input type="button" class="button" value="Back to Reports"/></a></div>
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
	<form id="form" method="post" action="">
		<label><font color="red">${message}</font></label><br> <a
			href="/Zephyrus/getUtilizationExcel"> <input type="button"
			class="button" value="Download Excel" id="excel" name="excel">
		</a> <a href="/Zephyrus/getUtilizationCSV"> <input type="button"
			value="Download CSV" id="csv" class="button">
		</a>
	</form>
	<table border="1"
		style="border: 1px solid black; border-collapse: collapse; width: 100%">
		<tr style="background-color: blue">
			<td>Router Serial number</td>
			<td>Capacity</td>
			<td>Utilization</td>
		</tr>
		<c:forEach items="${records}" var="record">
			<tr>
				<td>${record.routerSN}</td>
				<td>${record.capacity }</td>
				<td>${record.routerUtil }</td>
			</tr>
		</c:forEach>
	</table>
	<table style="width: 100%">
		<tr>
			<td style="width: 50%;" align="left"><c:if
					test="${last > (count*2)}">
					<a href="/Zephyrus/newOrders?last=${last-(count*2)}"> <input
						type="button" value="Previous page" class="button" />
					</a>
				</c:if></td>
			<td style="width: 50%" align="right"><c:if test="${next==1}">
					<a href="/Zephyrus/newOrders?last=${last}"> <input
						type="button" value="Next page" class="button" />
					</a>
				</c:if></td>
		</tr>
	</table>

</div>


<jsp:include page="../WEB-INF/jsphf/footer.jsp" />