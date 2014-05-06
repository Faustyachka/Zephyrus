<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navigation">
	<div style="text-align: center"><a href="/Zephyrus/provision"> 
	<input name="tasks" type="button" value="Tasks" class="navibutton" /></a></div>
	<div style="text-align: center"><a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
</div>
<div class="main">
	<jsp:include page="../reports/reports.jsp" />
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />