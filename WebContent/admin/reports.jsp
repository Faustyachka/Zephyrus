<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
	<div class="navigation">
		<center>
			<br />
			<a href="/Zephyrus/reportChoosing">
			<input name="reports" type="button" value="Reports"
				class="navibutton" /> 
				</a> <br /> <br /> <input name="accounts"
				type="button" value="Accounts" class="navibutton" />
		</center>
	</div>
	<div class="main">
	<jsp:include page="../reports/reports.jsp" />
	</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />