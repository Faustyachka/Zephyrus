<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
   <center>
		<input name="reports" type="button" value="Reports" class="navibutton" />
		<a href="/Zephyrus/customersupport">
		<input name="users" type="button" value="Users" class="navibutton" />
		</a>
	</center>
  </div>
  <div class="main">
  <h2> Service Instances </h2>
  <div id="actual">
  <c:forEach items="${instances}" var="instance">
  <h5>Instance # ${instance.id}</h5>
  <div>
   <ul> 
   	  <c:if test= "${instance.startDate != null}">
      <li>Start date: ${instance.startDate}</li>
      </c:if>
      <li>Service name: ${instance.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${instance.productCatalog.price} $</li>
      <li>Status: ${instance.servInstanceStatus.servInstanceStatusValue}</li>
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
    </ul>
  </div>
  </c:forEach>
  </div>

  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />l>