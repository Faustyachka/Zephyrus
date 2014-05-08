
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
<br>
			<label><font color="red">${message}</font></label>
			<form id="form" action="/Zephyrus/showReport" method="post">
			<c:forEach items="${types}" var="type">
			<label><input type="radio" value="${type.id}" name="reportType" class="reportType"/> ${type.name}</label> <br>
			</c:forEach>
			<input type="submit" class="button" value="Show report" name="show" id="show"/>
			</form>


