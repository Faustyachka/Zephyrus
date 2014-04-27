<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="navigation">
  <center><input name="backtotask" type="button" value="Back to Task" class="button" />
  </center></div>
  <div class="main">
  <center>
    <h2>Create a Cable</h2></center>
  <form id="createcable" name="createcable" method="post" action="/Zephyrus/createcable">
  <table>
  <tr>
  <td>Choose cable type:</td>
  <td><select>
  <c:forEach items="cableTypes" var="cableType">
  				<option value = "${cableType}">${cableType}</option>
  		</c:forEach>
  	</select></td></tr>
  <tr>
  <td></td><td><input type="submit" name="button" id="button" value="Create a Cable" class="button"/></td>
  	</table>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />