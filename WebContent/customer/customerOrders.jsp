<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<link rel="stylesheet" href="../resources/css/jquery-ui-1.10.4.min.css">
<script src="../resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="../resources/javascript/accordion.js"></script>

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
  <h2> Actual orders </h2>
  <div id="actual">
  <h5>Order 1</h5>
  <div>
    <ul>
      <li>Order adress: </li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
      <li>Order status: </li>
    </ul>
    <a href="Zephyrus/placingRequest">
    <input type="button" value="Location services">
    </a>
    <a href="Zephyrus/cancelOrder?id=">
    <input type="button" value="Cancel order">  
    </a>  
    <a href="Zephyrus/proceedOrder?id=">
    <input type="button" value="Proceed order">  
    </a>  
  </div>
  <h5>Order 2</h5>
  <div>
    <ul>
      <li>Order adress: </li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
      <li>Order status: </li>
    </ul>
    <a href="Zephyrus/placingRequest">
    <input type="button" value="Location services">
    </a>
    <a href="Zephyrus/cancelOrder?id=">
    <input type="button" value="Cancel order">  
    </a>  
    <a href="Zephyrus/proceedOrder?id=">
    <input type="button" value="Proceed order">  
    </a>  
  </div>
  <h5>Order 3</h5>
  <div>
    <ul>
      <li>Order adress: </li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
      <li>Order status: </li>
    </ul>
    <a href="Zephyrus/placingRequest">
    <input type="button" value="Location services">
    </a>
    <a href="Zephyrus/cancelOrder?id=">
    <input type="button" value="Cancel order">  
    </a>  
    <a href="Zephyrus/proceedOrder?id=">
    <input type="button" value="Proceed order">  
    </a>  
  </div>
  </div>

<br>

<h2> Worked-out orders </h2>
<div id="workedOut">
  <h5>Order 1</h5>
  <div>
    <ul>
      <li>Order adress: Kniaguy Zaton</li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
    </ul>
  </div>
  <h5>Order 2</h5>
  <div>
    <ul>
      <li>Order adress: </li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
      </ul>
  </div>
  <h5>Order 3</h5>
  <div>
    <ul>
      <li>Order adress: </li>
      <li>Order service: </li>
      <li>Order type: </li>
      <li>Order date: </li>
     </ul>
  </div>
  </div>

  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />
