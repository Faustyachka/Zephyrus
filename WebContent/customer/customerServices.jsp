<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet"
	href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>
<style>
hr {
	border: none;
	background-color: #508eeb;
	color: #508eeb;
	height: 2px;
}
</style>
<div class="navigation">
	<div style="text-align: center">
		<a href="/Zephyrus/customerOrders" class="current"> <input
			type="button" value="My orders" class="navibutton" />
		</a>
	</div>
	<div style="text-align: center">
		<a href="/Zephyrus/customerServices" class="current"> <input
			type="button" value="My Services" class="navibutton" /></a>
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
<br>
<br>
<div style="text-align:center"><a href="/Zephyrus/view/start.jsp">
<input type="button"	value="Get connected" class="meganavibutton" /></a></div>
</div>
<div class="main">
	<script type = "text/javascript">
		function showNotification(notification, instanceID) {
			obj = document.getElementById(notification);
			obj.style.display = "";
			ref = document.getElementById("disconnectRef");
			ref.href = "/Zephyrus/disconnectServiceInstance?id="+instanceID;
		}
		
		function hideNotification(id) {
			obj = document.getElementById(id);
			obj.style.display = "none";
		}
	</script>
	<table id="deleteConfirmation" style = "z-index: 100; position: fixed !important;
					 background-image: url(/Zephyrus/resources/css/images/transparent_fill.png); 
					 height: 105%; width: 105%; position: absolute; 
					 left: -5px; top: expression(this.offsetParent.scrollTop+'px'); 
					 top: -5px; display: none;">
		<tr>
			<td valign = "center" align = "center">
				<table style="width: 300px; height: 200px; background-color: white; padding: 20px;">
					<tr>
						<td>
							<h3>Confirmation</h3>
							Do you really want to disconnect specified Service?<br /><br />
							<a id="disconnectRef" href="#">
								<input type="button" value="Confirm" style="font-size: 16px; padding: 5px;">
							</a>
							<input type="button" style="font-size: 16px; padding: 5px;"
									value="Discard" onclick="hideNotification('deleteConfirmation');">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<h2>My Service Instances</h2>
	<div id="actual">
		<c:forEach items="${actualServices}" var="actualService">
			<h5>Service ${actualService.key.id}</h5>
			<div>
				<ul>
					<li>Start date: ${actualService.key.startDate}</li>
					<li>Service name:
						${actualService.key.productCatalog.serviceType.serviceType}</li>
					<li>Price: ${actualService.key.productCatalog.price} $</li>
					<li>Status:
						${actualService.key.servInstanceStatus.servInstanceStatusValue}</li>
					<li>Address: ${actualService.value}</li>
				</ul>
				<c:if test="${actualService.key.servInstanceStatus.id == 2}">
					<a href="/Zephyrus/modifyService?id=${actualService.key.id}"> <input
						type="button" value="Modify">
					</a>
					<input type="button" value="Delete" 
						onclick="showNotification('deleteConfirmation', ${actualService.key.id});">
				</c:if>
			</div>
		</c:forEach>
	</div>
	<br>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
