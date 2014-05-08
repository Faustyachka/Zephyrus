<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
<div class="navigation">
   <div style="text-align:center"><a href="/Zephyrus/customerOrders" class="current">
   <input type="button" value="My orders" class="navibutton"/> </a></div>
   <div style="text-align:center"><a href="/Zephyrus/customerServices" class="current">
   <input type="button" value="My Services" class="navibutton"/></a></div>
   <br>
   <hr>
   <br>
   <div style="text-align:center"><a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
<br>
<br>
<div style="text-align:center"><a href="/Zephyrus/start">
<input type="button"	value="Get connected" class="meganavibutton" /></a></div>
  </div>
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
The price is: ${service.price} $;
</li>
</ul>
<a href="/Zephyrus/saveOrder">
<input type="button" value="Save Order"  class="button" />
</a>

<a href="/Zephyrus/sendOrder">
<input type="button" value="Send Order"  class="button"/>
</a>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />