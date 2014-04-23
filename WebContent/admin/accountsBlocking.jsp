<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<link rel="stylesheet" href="resources/css/jquery-ui-1.10.4.min.css">
<link rel="stylesheet" href="resources/css/jtable.css">
<script src="resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="resources/javascript/jquery.jtable.min.js"></script>
<script src="resources/javascript/blockingjTable.js"></script>

<div class="navigation">
<center>
<input name="tasks" type="button" value="Tasks" class="button" />
  <br />
  <br />
  <input name="reports" type="button" value="Reports" class="button" />
  <br />
  <br />
  <input name="accounts" type="button" value="Accounts" class="button" />
  </center>
</div>
<div class="main">
<div id="PersonTableContainer"></div>
<a href="Zephyrus/createEngineerAccount">
   <input type="button" value="Create account" class="button"/>
   </a>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />