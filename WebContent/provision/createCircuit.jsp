<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
	<center>
		 <br /> <br />
		<input name="tasks" type="button" value="Tasks" class="button" /> 
	</center>
</div>
<div class="main">
	<center>
		<h2>Create Circuit</h2>
	</center>
	<div> ${error}</div>
	<form method="post" action="/Zephyrus/createCircuit">
		<label>Circuit configuration: </label>
		<input type="hidden" name="taskId" value="${taskId}"/>
		<input type = "text" id="circuit" name="circuit"/>
		 <input type="submit" name="button" id="button"
			value="Create Circuit" class="button" />
	</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />