<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link href="/Zephyrus/resources/css/metro/crimson/jtable.css"
	rel="stylesheet" type="text/css" />
<link href="/Zephyrus/resources/css/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.custom.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/jquery.jtable.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/adminJTable.js"
	type="text/javascript"></script>
	<div class="navigation">
		<center>
			<br /> <input name="reports" type="button" value="Reports"
				class="button" /> <br /> <br /> <input name="accounts"
				type="button" value="Accounts" class="button" />
		</center>
	</div>
	<div class="main">
	<div id="PersonTableContainer"></div>
		<a href="/Zephyrus/admin/accountCreation.jsp"> <input type="button"
			value="Create account" class="button" />
		</a>
	</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />