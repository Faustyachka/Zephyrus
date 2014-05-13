<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="columns">
<div id="navigation">
<div style="text-align:center"><a href="/Zephyrus/view/about.jsp"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/services.jsp">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/contacts.jsp">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
</div>
<div id="main">
	${errorMessage} ${message}
	<c:if test="${messageNumber == 1}">
You should login under Administrator's account to view this page!<br>
		<a href='/Zephyrus/login'><input type='button' class='button'
			value='Login' /></a>
	</c:if>
	<c:if test="${messageNumber == 2}">
You should login to view this page!<br>
		<a href='/Zephyrus/login'> <input type='button' class='button'
			value='Login' /></a>
	</c:if>
	<c:if test="${messageNumber == 3}">
You should login under Provisioning or Installation Engineer's account to view this page!<br>
		<a href='/Zephyrus/login'> <input type='button' class='button'
			value='Login' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 4}">
You must select task from task's page!<br>
		<a href='/Zephyrus/displayTasks'> <input type='button' class='button'
			value='Tasks' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 5}">
You should login under Installation Engineer's account to view this page!<br>
		<a href='/Zephyrus/login'> <input type='button' class='button'
			value='Login' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 6}">
You should login under Provisioning Engineer's account to view this page!<br>
		<a href='/Zephyrus/login'> <input type='button' class='button'
			value='Login' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 7}">
Task completed! <br>
		<a href='/Zephyrus/displayTasks'> <input type='button' class='button'
			value='Back to Tasks' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 8}">
You should login under Customer Support Engineer's account to view this page!<br>
		<a href='/Zephyrus/login'> <input type='button' class='button'
			value='Login' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 9}">
You must select the user from table!<br>
		<a href='/Zephyrus/customersupport'> <input type='button'
			class='button' value='Users' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 10}">
Password changed!<br>
		<a href='/Zephyrus/customersupport'> <input type='button'
			class='button' value='Users' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 11}">
You don`t have orders and services<br>
		<a href='/Zephyrus/view/start.jsp'> <input type='button'
			class='button' value='Get connected' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 12}">
User doesn`t exist<br>
		<a href='/Zephyrus/customer'> <input type='button'
			class='button' value='Back to orders' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 13}">
You should select existing Service Instance<br>
		<a href='/Zephyrus/customerServices'> <input type='button'
			class='button' value='Back to Service Instance' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 14}">
Disconnect order for selected Service Instance has been created<br>
		<a href='/Zephyrus/customerServices'> <input type='button'
			class='button' value='Back to Service Instance' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 15}">
No available products for modify selected Service Instance<br>
		<a href='/Zephyrus/customerServices'> <input type='button'
			class='button' value='Back to Service Instance' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 16}">
You should select available product for modify Service Instance<br>
		<a href='/Zephyrus/customerServices'> <input type='button'
			class='button' value='Back to Service Instance' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 17}">
Modify order for selected Service Instance has been created<br>
		<a href='/Zephyrus/customerServices'> <input type='button'
			class='button' value='Back to Service Instance' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 18}">
New order has been sent to processing!<br>
		<a href='/Zephyrus/customerOrders'> <input type='button'
			class='button' value='Your orders' />
		</a>
	</c:if>
	<c:if test="${messageNumber == 19}">
You must select Service Location!<br>
		<a href='/Zephyrus/view/start.jsp'> <input type='button'
			class='button' value='Your orders' />
		</a>
	</c:if>
</div>
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />