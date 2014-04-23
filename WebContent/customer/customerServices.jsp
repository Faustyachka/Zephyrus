<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
  <a href="Zephyrus/view/cutomerOrders" class="current">
   <input type="button" value="My orders" />
   </a>
   <br>
   <a href="Zephyrus/view/cutomerServices" class="current">
   <input type="button" value="My Services" />
   </a>
   <br>
   <a href="Zephyrus/view/chooseReport" class="current">
   <input type="button" value="Reports" />
   </a>
   <br>
  </div>
  <div class="main">
  <h2> Planned and active Service Instances </h2>
  <div id="actual">
  <h5>Service 1</h5>
  <div>
    <ul>
      <li>Start date: </li>
      <li>Service name: </li>
      <li>Price: </li>
      <li>Status: </li>
    </ul>
    <a href="Zephyrus/modifyService?id=">
    <input type="button" value="Modify">  
    </a>  
    <a href="Zephyrus/deleteService?id=">
    <input type="button" value="Delete">  
    </a>  
  </div>
  <h5>Service 2</h5>
  <div>
    <ul>
      <li>Start date: </li>
      <li>Service name: </li>
      <li>Price: </li>
      <li>Status: </li>
    </ul>
    <a href="Zephyrus/modifyService?id=">
    <input type="button" value="Modify">  
    </a>  
    <a href="Zephyrus/deleteService?id=">
    <input type="button" value="Delete">  
    </a>  
  </div>
  <h5>Service 3</h5>
  <div>
    <ul>
      <li>Start date: </li>
      <li>Service name: </li>
      <li>Price: </li>
      <li>Status: </li>
    </ul>
    <a href="Zephyrus/modifyService?id=">
    <input type="button" value="Modify">  
    </a>  
    <a href="Zephyrus/deleteService?id=">
    <input type="button" value="Delete">  
    </a>  
  </div>
  </div>

<br>

<h2> Disconnected Service Instances </h2>
<div id="workedOut">
  <h5>Service 1</h5>
  <div>
    <ul>
      <li>Start date: </li>
      <li>Disconnect date: </li>
      <li>Service name: </li>
      <li>Price: </li>
	</ul> 
  </div>
  <h5>Service 2</h5>
  <div>
    <ul>
      <li>Start date: </li>
      <li>Disconnect date: </li>
      <li>Service name: </li>
      <li>Price: </li>
	</ul> 
  </div>
  </div>

  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
