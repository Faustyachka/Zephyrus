<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
<div id="columns">
<div id="navigation">
	<div style="text-align:center"><a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
	<div style="text-align:center"><a href="/Zephyrus/customersupport"> 
	<input name="users" type="button" value="Users" class="navibutton" /></a></div>
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
	<jsp:include page="../reports/reports.jsp" />
</div></div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />