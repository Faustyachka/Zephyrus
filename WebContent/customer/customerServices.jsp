<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
   <div style="text-align:center"><a href="/Zephyrus/customerOrders" class="current">
   <input type="button" value="My orders" class="navibutton"/> </a></div>
   <div style="text-align:center"><a href="/Zephyrus/customerServices" class="current">
   <input type="button" value="My Services" class="navibutton"/></a></div>
   <div style="text-align:center"><a href="/Zephyrus/chooseReport" class="current">
   <input type="button" value="Reports" class="navibutton"/></a></div>
  </div>
  <div class="main">
  <h2> My Service Instances </h2>
  <div id="actual">
  <c:forEach items="${actualServices}" var="actualService">
  <h5>Service ${actualService.key.id}</h5>
  <div>
   <ul>
      <li>Start date: ${actualService.key.startDate}</li>
      <li>Service name: ${actualService.key.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${actualService.key.productCatalog.price} $</li>
      <li>Status: ${actualService.key.servInstanceStatus.servInstanceStatusValue}</li>
      <li>Address: ${actualService.value}</li>
</ul>
	<c:if test="${actualService.key.servInstanceStatus.id == 2}">
    <a href="/Zephyrus/modifyService?id=${actualService.key.id}">
    <input type="button" value="Modify">  
    </a>  
    <a href="/Zephyrus/disconnectServiceInstance?id=${actualService.key.id}">
    <input type="button" value="Delete">  
    </a>  
    </c:if>
  </div>
  </c:forEach>
  </div>

<br>

 </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
