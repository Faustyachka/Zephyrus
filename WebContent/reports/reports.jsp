
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<label><font color="red">${message}</font></label>
<h3>Choose the type of report:</h3>
<br>
<form id="form" action="/Zephyrus/showReport" method="post">
	<c:forEach items="${types}" var="type">
		<label> 
		<input style="margin: 5px;" type="radio" value="${type.id}" name="reportType" /> ${type.name}
		</label>
		<br />
	</c:forEach>
	<input style="margin: 10px;" type="submit" class="button" value="Show report" name="show" id="show" />
</form>


