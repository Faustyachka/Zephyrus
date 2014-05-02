<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
	<script>
	 $().ready(function(){
	     $('#check').click(function(){
	    	 var serialNum = $('#serialNum').val();
	         $.post('/Zephyrus/createDevice',{serialNum:serialNum},function(rsp){
	             $("#somediv").empty();
	             $('#somediv').text(rsp);	 
	             if (rsp == 'Device created!') {
	            	 document.forms["createDevice"].reset();
	             }
	             });
	         });

	         return false;
	     });
	     </script>
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/installation"> <input type="button"	value="Back to Tasks" class="navibutton" /></a>
  </div></div>
  <div class="main">
  <div style="text-align:center">
    <h2>Create a Device for Task# ${taskId}</h2></div>
  <form method="post" action="/Zephyrus/createDevice?taskId=${taskId}">
  <div id="somediv"></div>
    <label>Device ID:	</label><input type="text" name="serialNum" id="serialNum" />
<br />
<br />
  <input type="submit" name="button" id="check" value="Create a Device" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />