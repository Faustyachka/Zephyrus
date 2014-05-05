<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navigation">
	<center>
		<a href="/Zephyrus/reportChoosing"> <input name="reports"
			type="button" value="Reports" class="navibutton" />
		</a> <br> <a href="/Zephyrus/customersupport"> <input
			name="users" type="button" value="Users" class="navibutton" />
		</a>
	</center>
</div>
<div class="main">
	<jsp:include page="../reports/reports.jsp" />
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />