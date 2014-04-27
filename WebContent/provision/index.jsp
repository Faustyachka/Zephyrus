<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/accordion.js"></script>

<div class="navigation">
  <center>
   <br />
   <input name="tasks" type="button" value="Tasks" class="button" />
  <br />
  <br />
  <input name="reports" type="button" value="Reports" class="button" />
  </center></div>
  <div class="main">
<jsp:include page="../view/tasks.jsp" />
</div>

  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />