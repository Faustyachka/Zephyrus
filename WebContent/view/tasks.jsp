<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<h2>Current tasks:</h2>
<c:if test="${not empty activeTasks}">
	<div id="actual">
		<c:forEach items="${activeTasks}" var="actualTask">
			<h5>Task# ${actualTask.id}</h5>
			<div>
				<ul>
					<li>Order date: ${actualTask.serviceOrder.orderDate}</li>
					<li>Order id: ${actualTask.serviceOrder.id}</li>
					<li>Scenario: ${actualTask.serviceOrder.orderType.orderType }
					</li>
					<li>Service:
						${actualTask.serviceOrder.productCatalog.serviceType.serviceType}
					</li>
					<li>Address:
						${actualTask.serviceOrder.serviceLocation.address}</li>
				</ul>
					<a href="/Zephyrus/taskRedirector?id=${actualTask.id}">
					<input type="button" value="Complete" class="button" />
					</a>					
					<a href="/Zephyrus/suspendTask?id=${actualTask.id}">
					<input type="button" value="Suspend" class="button" />
					</a>
			</div>
		</c:forEach>
	</div>
</c:if>
<c:if test="${empty activeTasks}">
	<label>No active tasks</label>
</c:if>
<br>

<h2>Available Tasks:</h2>
<c:if test="${not empty availableTasks}">
	<div id="workedOut">
		<c:forEach items="${availableTasks}" var="availableTask">
			<h5>Task# ${availableTask.id}</h5>
			<div>
				<ul>
					<li>Order date: ${availableTask.serviceOrder.orderDate}</li>
					<li>Order id: ${availableTask.serviceOrder.id}</li>
					<li>Scenario: ${availableTask.serviceOrder.orderType.orderType }
					</li>
					<li>Service:
						${availableTask.serviceOrder.productCatalog.serviceType.serviceType}
					</li>
					<li>Address:
						${availableTask.serviceOrder.serviceLocation.address}</li>
				</ul>
				<form method="post" action="/Zephyrus/assignTask">
					<input type="hidden" name="id" value="${availableTask.id}">
					<input type="submit" value="Take Task" class="button">
				</form>
			</div>
		</c:forEach>
	</div>
</c:if>
<c:if test="${empty availableTasks}">
	<label>No tasks available yet</label>
</c:if>

<h2>Suspended Tasks:</h2>
<c:if test="${not empty suspendedTasks}">
	<div id="suspended">
		<c:forEach items="${suspendedTasks}" var="suspendedTask">
			<h5>Task# ${suspendedTask.id}</h5>
			<div>
				<ul>
					<li>Order date: ${suspendedTask.serviceOrder.orderDate}</li>
					<li>Order id: ${suspendedTask.serviceOrder.id}</li>
					<li>Scenario: ${suspendedTask.serviceOrder.orderType.orderType }
					</li>
					<li>Service:
						${suspendedTask.serviceOrder.productCatalog.serviceType.serviceType}
					</li>
					<li>Address:
						${suspendedTask.serviceOrder.serviceLocation.address}</li>
				</ul>
				<form method="post" action="/Zephyrus/renewTask">
					<input type="hidden" name="id" value="${suspendedTask.id}">
					<input type="submit" value="Renew Task" class="button">
				</form>
			</div>
		</c:forEach>
	</div>
</c:if>
<c:if test="${empty suspendedTasks}">
	<label>No tasks suspended</label>
</c:if>

