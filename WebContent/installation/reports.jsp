<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navigation">
	<a href="/Zephyrus/installation"> 
	<input name="tasks"	type="button" value="Tasks" class="navibutton" /></a>
	<a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a>
</div>
<div class="main">
	<jsp:include page="../reports/reports.jsp" />
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />