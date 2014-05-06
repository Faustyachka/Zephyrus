<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/reportChoosing">
  <input name="reports" type="button" value="Reports" class="navibutton" /></a>
  <div style="text-align:center"><a href="../admin">
  <input name="accounts" type="button" value="Accounts" class="navibutton" /></a>
</div>
	<div class="main">
	<jsp:include page="../reports/reports.jsp" />
	</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />