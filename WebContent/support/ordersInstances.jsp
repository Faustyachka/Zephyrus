<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
	<a href="/Zephyrus/reportChoosing"> 
	<input name="reports" type="button" value="Reports" class="navibutton" /></a>
	<a href="/Zephyrus/customersupport"> 
	<input name="users" type="button" value="Users" class="navibutton" /></a>
</div>
  <div class="main">
  <h2> Service Instances </h2>
  <div id="actual">
  <c:forEach items="${instances}" var="instance">
  <h5>Instance # ${instance.key.id}</h5>
  <div>
   <ul> 
   	  <c:if test= "${instance.key.startDate != null}">
      <li>Start date: ${instance.key.startDate}</li>
      </c:if>
      <li>Service name: ${instance.key.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${instance.key.productCatalog.price} $</li>
      <li>Status: ${instance.key.servInstanceStatus.servInstanceStatusValue}</li>
      <li>Address: ${instance.value}</li>
    </ul>
  </div>
  </c:forEach>
  </div>
<br>

<h2> Service Orders </h2>
<div id="workedOut">
  <c:forEach items="${orders}" var="order">
  <h5>Order # ${order.id}</h5>
  <div>
   <ul>
      <li>Start date: ${order.orderDate}</li>
      <li>Service name: ${order.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${order.productCatalog.price} $</li>
      <li>Type: ${order.orderType.orderType}</li>
      <li>Status: ${order.orderStatus.orderStatusValue}</li>
      <li>Address: ${order.serviceLocation.address }</li>
    </ul>
  </div>
  </c:forEach>
  </div>

  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />l>