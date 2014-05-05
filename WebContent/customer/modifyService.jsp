<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>

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
  <h2> Available services </h2>
  <div id="prod">
  <form action="/Zephyrus/modifyOrder">
	<c:forEach items="${products}" var="product">
	<c:if test="${product.id != serviceInstance.productCatalog.id}">
	<input type="hidden" value="${serviceInstance.id}" name="serviceInstance"/>
	<div><input type='radio' class='radio' name='product' id='${product.id }' value = '${product.id }'> ${product.serviceType.serviceType}, ${product.price} $ month <br></div>
	</c:if>
	</c:forEach>
	<br>
    <input type="submit" value="Modify service" class="button"/>  
    </form>
  </div>

<br>

 </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
