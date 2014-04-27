<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
  <a href="/Zephyrus/customerOrders" class="current">
   <input type="button" value="My orders" />
   </a>
   <br>
   <a href="/Zephyrus/customerServices" class="current">
   <input type="button" value="My Services" />
   </a>
   <br>
   <a href="/Zephyrus/chooseReport" class="current">
   <input type="button" value="Reports" />
   </a>
   <br>
  </div>
  <div class="main">
  <h2> Planned and active Service Instances </h2>
  <div id="actual">
  <c:forEach items="${actualServices}" var="actualService">
  <h5>Service ${actualService.id}</h5>
  <div>
   <ul>
      <li>Start date: ${actualService.startDate}</li>
      <li>Service name: ${actualService.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${actualService.productCatalog.price}</li>
      <li>Status: ${actualService.servInstanceStatus.servInstanceStatusValue}</li>
    </ul>
    <a href="/Zephyrus/modifyService?id=${actualService.id}">
    <input type="button" value="Modify">  
    </a>  
    <a href="/Zephyrus/deleteService?id=${actualService.id}">
    <input type="button" value="Delete">  
    </a>  
  </div>
  </c:forEach>
  </div>

<br>

<h2> Disconnected Service Instances </h2>
<div id="workedOut">
  <c:forEach items="${workedOutServices}" var="workedOutService">
  <h5>Service ${workedOutService.id}</h5>
  <div>
   <ul>
      <li>Start date: ${workedOutService.startDate}</li>
      <li>Service name: ${workedOutService.productCatalog.serviceType.serviceType}</li>
      <li>Price: ${workedOutService.productCatalog.price}</li>
    </ul>
  </div>
  </c:forEach>
  </div>

  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
