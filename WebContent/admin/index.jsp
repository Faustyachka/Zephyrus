<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

Admin index page
<br>

<c:forEach items="${logins}" var="login" varStatus="counter">
${counter.count}. ${login};
<br>
</c:forEach>

<a href="logout">logout</a>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />