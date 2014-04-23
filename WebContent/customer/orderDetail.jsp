<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation"></div>
<div class="main">
Details of your order are:
<br>
<ul>
<li>
Location: ${serviceLocation.address};
</li>
<li>
The name of your service: ${service.serviceName};
</li>
<li>
The price is: ${service.price};
</li>
</ul>
<a href="saveOrder">
<input type="button" value="Save Order" />
</a>

<a href="sendOrder">
<input type="button" value="Send Order" />
</a>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />