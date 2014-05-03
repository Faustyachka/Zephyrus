<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link href="/Zephyrus/resources/css/metro/crimson/jtable.css"
	rel="stylesheet" type="text/css" />
<link href="/Zephyrus/resources/css/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.custom.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/jquery.jtable.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/supportjTable.js"
	type="text/javascript"></script>

<div class="navigation">
	<center>
	<a href="">
		<input name="reports" type="button" value="Reports" class="navibutton" />
		</a>
		<br>
		<a href="/Zephyrus/customersupport">
		<input name="users" type="button" value="Users"
			class="navibutton" /> </a> 
	</center>
</div>
<div class="main">
	<form id="createdevice" name="form3" method="post"
		action="/Zephyrus/changepass">
		<div id="PersonTableContainer"></div>
		<br /> <input type="button" name="view_si" id="view_si"	value="Service Instances and Service Orders" class="button" /> 
			<input type="button" name="button" id="changepass" value="Change password" class="button" />
	</form>
</div>
<div class="footer"></div>

<script>
	$(document).on('click', '#changepass', function() {
		if ($(":radio[name=radiobutton]").filter(":checked").val()) {
			var userId = $('input[name=radiobutton]:checked').val();
			document.location.href = "support/changepass.jsp?id=" + userId;
		} else {
			alert("Check user");
		}
	});
	$(document).on('click', '#view_si', function() {
		if ($(":radio[name=radiobutton]").filter(":checked").val()) {
			var userId = $('input[name=radiobutton]:checked').val();
			document.location.href = "/Zephyrus/ordersAndServices?id=" + userId;
		} else {
			alert("Check user");
		}
	});
</script>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
