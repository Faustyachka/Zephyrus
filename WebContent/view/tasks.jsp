<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

	<h2>Current tasks</h2>
	<div id="actual">
		<c:forEach items="${activeTasks}" var="actualTask">
			<h5>Task# ${actualTask.id}</h5>
			<div>
				<ul>
					<li>Order date: ${actualTask.serviceOrder.orderDate}</li>
					<li>Order id: ${actualTask.serviceOrder.id}</li>
					<li>Scenario: ${actualTask.serviceOrder.orderType.orderType } </li>
					<li>Service: ${actualTask.serviceOrder.productCatalog.serviceType.serviceType} </li>
				</ul>
				<a href="/Zephyrus/taskRedirector?id=${actualTask.id}"> 
					<input type="button" value="Complete" class="button">
				</a> 
			</div>
		</c:forEach>
	</div>
	<br>

	<h2>Available Tasks</h2>
	<div id="workedOut">
		<c:forEach items="${availableTasks}" var="availableTask">
			<h5>Task# ${availableTask.id}</h5>
			<div>
				<ul>
					<li>Order date: ${availableTask.serviceOrder.orderDate}</li>
					<li>Order id: ${availableTask.serviceOrder.id}</li>
					<li>Scenario: ${availableTask.serviceOrder.orderType.orderType } </li>
					<li>Service: ${availableTask.serviceOrder.productCatalog.serviceType.serviceType} </li>
				</ul>
				<a href="/Zephyrus/assignTask?id=${availableTask.id}"> 
				<input type="button" value="Take Task" class="button">
				</a>
			</div>
		</c:forEach>
	</div>


