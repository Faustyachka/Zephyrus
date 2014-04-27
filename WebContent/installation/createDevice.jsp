<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
	<script>
	 $().ready(function(){
	     $('#check').click(function(){
	    	 var serialNum = $('#serialNum').val();
	         $.post('/Zephyrus/createdevice',{serialNum:serialNum},function(rsp){
	             $("#somediv").empty();
	             $('#somediv').text(rsp);	 
	             if (rsp == 'Device created!') {
	            	 document.forms["createdevice"].reset();
	             }
	             });
	         });

	         return false;
	     });
	     </script>
<div class="navigation">
  <center><input name="backtotask" type="button" value="Back to Task" class="button" />
  </center></div>
  <div class="main">
  <center>
    <h2>Create a Device</h2></center>
  <form id="createdevice" name="createdevice" method="post" action="/Zephyrus/createdevice">
  <div id="somediv"></div>
    <label>Device ID:	</label><input type="text" name="serialNum" id="serialNum" />
<br />
<br />
  <input type="submit" name="button" id="check" value="Create a Device" class="button"/>
</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />