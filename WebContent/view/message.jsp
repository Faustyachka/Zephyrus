<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navigation">
<div style="text-align:center"><a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
</div>
<div class="main">
${errorMessage}
${message}
<c:if test="${messageNumber == 1}">
You should login under Administrator's account to view this page!<br>
<a href='/Zephyrus/login'><input type='button' class='button' value='Login'/></a>
</c:if>
<c:if test="${messageNumber == 2}">
You should login to view this page!<br> 
<a href='/Zephyrus/login'>
<input type='button' class='button' value='Login'/></a>
</c:if>
<c:if test="${messageNumber == 3}">
You should login under Provisioning or Installation Engineer's account to view this page!<br>
<a href='/Zephyrus/login'>
<input type='button' class='button' value='Login'/>
</a>
</c:if>
<c:if test="${messageNumber == 4}">
You must select task from task's page!<br>
<a href='/Zephyrus/provision'>
<input type='button' class='button' value='Tasks'/>
</a>
</c:if>
<c:if test="${messageNumber == 5}">
You should login under Installation Engineer's account to view this page!<br>
<a href='/Zephyrus/login'>
<input type='button' class='button' value='Login'/>
</a>
</c:if>
<c:if test="${messageNumber == 6}">
You should login under Provisioning Engineer's account to view this page!<br>
<a href='/Zephyrus/login'>
<input type='button' class='button' value='Login'/>
</a>
</c:if>
<c:if test="${messageNumber == 7}">
Task completed! <br>
<a href='/Zephyrus/provision'>
<input type='button' class='button' value='Back to Tasks'/>
</a>
</c:if>
<c:if test="${messageNumber == 8}">
You should login under Customer Support Engineer's account to view this page!<br>
<a href='/Zephyrus/login'>
<input type='button' class='button' value='Login'/>
</a>
</c:if>
<c:if test="${messageNumber == 9}">
You must select the user from table!<br>
<a href='/Zephyrus/customersupport'>
<input type='button' class='button' value='Users'/>
</a>
</c:if>
<c:if test="${messageNumber == 10}">
Password changed!<br>
<a href='/Zephyrus/customersupport'>
<input type='button' class='button' value='Users'/>
</a>
</c:if>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />