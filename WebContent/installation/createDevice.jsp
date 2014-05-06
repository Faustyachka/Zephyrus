<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
	
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/installation"> 
  <input type="button"	value="Back to Tasks" class="navibutton" /></a>
  </div></div>
  <div class="main">
  <div style="text-align:center">
    <h2>Create a Device for Task# ${taskId}</h2></div>
  <form method="post" action="/Zephyrus/createDevice">
 <div><font color="red"> ${message} </font></div>
  <br>
  <br>
  <label>Type the serial number of device. Pay your attention that serial number must be
  in format LLLNNNNCCCC, where L - letter symbol, N - number symbol, C - letter or number symbol</label>
  <br> 
    <label>Device ID:	</label><input type="text" name="serialNum" id="serialNum" />
    <input type="hidden" name="taskId" value = "${taskId}"/>
<br />
<br />
  <input type="submit" name="button" id="check" value="Create a Device" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />