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
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/reportChoosing">
  <input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
  <div style="text-align:center"><a href="/Zephyrus/admin">
  <input name="accounts" type="button" value="Accounts" class="navibutton" /></a></div>
  <div style="text-align:center"><a href="/Zephyrus/admin/accountCreation.jsp"> 
  <input type="button"	value="Create account" class="navibutton" /></a></div>
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
	<jsp:include page="../reports/reports.jsp" />
	</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />