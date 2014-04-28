<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="navigation"></div>
<div class="main">
Details of the order are:
<br>
<ul>
<c:if test="${so != null }">
<li>
The type of your order: ${task.serviceOrder.orderType.orderType};
</li>
</c:if>
<li>
Location: ${task.serviceOrder.serviceLocation.address};
</li>

<li>
The name of the service: ${task.serviceOrder.productCatalog.serviceType.serviceType};
</li>
<li>
The price is: ${task.serviceOrder.productCatalog.price};
</li>
</ul>
<a href="/Zephyrus/approveBill?taskId=${task.id}">
<input type="button" value="Approve Bill" />
</a>

</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />