<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="navigation"></div>
<div class="main">
Details of your order are:
<br>
<ul>
<c:if test="${order != null }">
<li>
The type of your order: ${order.orderType.orderType};
</li>
</c:if>
<li>
Location: ${serviceLocation.address};
</li>

<li>
The name of your service: ${service.serviceType.serviceType};
</li>
<li>
The price is: ${service.price};
</li>
</ul>
<a href="/Zephyrus/saveOrder">
<input type="button" value="Save Order" />
</a>

<a href="/Zephyrus/sendOrder">
<input type="button" value="Send Order" />
</a>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />