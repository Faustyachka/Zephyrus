<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script> 

<div class="navigation">
  <a href="/Zephyrus/customerOrders" class="current">
   <input type="button" value="My orders" class="navibutton"/>
   </a>
   <br>
   <a href="/Zephyrus/customerServices" class="current">
   <input type="button" value="My Services" class="navibutton"/>
   </a>
   <br>
   <a href="/Zephyrus/chooseReport" class="current">
   <input type="button" value="Reports" class="navibutton"/>
   </a>
   <br>
  </div>
  <div class="main">
  <h2> Actual orders </h2>
  <div id="actual">
  <c:forEach items="${actualOrders}" var="actualOrder">
  <h5>Order ${actualOrder.id}</h5>
  <div>
    <ul>
      <li>Order service: ${actualOrder.productCatalog.serviceType.serviceType} </li>
      <li>Order type:${actualOrder.orderType.orderType} </li>
      <li>Order date: ${actualOrder.orderDate}</li>
      <li>Order status:${actualOrder.orderStatus.orderStatusValue} </li>
    </ul>
    <c:if test="${actualOrder.orderStatus.id == 1}">
    <a href="/Zephyrus/cancelOrder?orderId=${actualOrder.id}">
    <input type="button" value="Cancel order">  
    </a>  
    <a href="/Zephyrus/sendOrder?orderId=${actualOrder.id}">
    <input type="button" value="Send Order" />  
    </a>
    </c:if>
  </div>
  </c:forEach>
  </div>
  
<br>

<h2> Worked-out orders </h2>
<div id="workedOut">
<c:forEach items="${workedOutOrders}" var="workedOutOrder">
  <h5>Order ${workedOutOrder.id}</h5>
  <div>
    <ul>
      <li>Order service: ${workedOutOrder.productCatalog.serviceType.serviceType} </li>
      <li>Order type:${workedOutOrder.orderType.orderType} </li>
      <li>Order date: ${workedOutOrder.orderDate}</li>
      <li>Order status:${workedOutOrder.orderStatus.orderStatusValue} </li>
    </ul>
    <a href="/Zephyrus/locationServices?locationId=${workedOutOrder.serviceLocation.id}">
    <input type="button" value="Location services">
    </a>
  </div>
  </c:forEach>
  </div>
  
  </div>
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
