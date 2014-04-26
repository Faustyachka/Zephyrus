<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet"
	href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
	<a href="/Zephyrus/displayTasks" class="current"> <input
		type="button" value="Tasks" />
	</a> <br> <a href="/Zephyrus/chooseReport" class="current"> <!-- to write here necessary reference for reports displaying -->
		<input type="button" value="Reports" />
	</a> <br>
</div>
<div class="main">
	<h2>Current tasks</h2>
	<div id="actual">
		<c:forEach items="${actualTasks}" var="actualTask">
			<h5>Service ${actualTask.taskValue}</h5>
			<div>
				<ul>
					<li>Order date: ${actualTask.serviceOrder.orderDate}</li>
					<li>Order id: ${actualTask.serviceOrder.id}</li>
				</ul>
				<a href="/Zephyrus/completeTask?id=${actualTask.id}"> <!-- to write necessary command for your engineer -->
					<input type="button" value="Complete">
				</a> <a href="/Zephyrus/suspendTask?id=${actualTask.id}"> <input
					type="button" value="Suspend">
				</a>
			</div>
		</c:forEach>
	</div>

	<br>

	<h2>Available Tasks</h2>
	<div id="workedOut">
		<c:forEach items="${availableTasks}" var="availableTask">
			<h5>${availableTask.taskValue}</h5>
			<div>
				<ul>
					<li>Order date: ${availableTask.serviceOrder.orderDate}</li>
					<li>Order id: ${availableTask.serviceOrder.id}</li>
				</ul>
				<a href="/Zephyrus/takeTask?id=${availableTask.id}"> <input
					type="button" value="Take Task">
				</a>
			</div>
		</c:forEach>
	</div>

</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />