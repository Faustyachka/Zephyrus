<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script type="text/javascript" src="../resources/javascript/dataValidation.js" > </script>
	<script>
	 $().ready(function(){
	     $('#submitt').click(function(){
	    	 var fname = $('#fname').val();
	    	 var sname = $('#sname').val();
	         var email = $('#email').val();
	         var pass = $('#pass').val();
	         var cpass = $('#confirmpass').val();
	         var etype = $('#engtype').val();
	         $.post('/Zephyrus/createaccount',{firstname:fname,secondname:sname,email:email,pass:pass,confirmpass:cpass,engtype:etype},function(rsp){
	             $("#somediv").empty();
	             $('#somediv').text(rsp);	 
	             if (rsp == 'Account created!') {
	            	 document.forms["form1"].reset();
	             }
	             });

	         });

	         return false;
	     });
	     </script>
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
<center><h2>Create new account</h2></center>
  			<form id="createaccount" name="form1" method="post" action="" id="form1">
					<div id="somediv"></div>
				<table>
					<tr>
						<td><label>First name: </label></td>
						<td><input type="text" name="name" id="fname" value="${fname}"/></td>
					</tr>
					<tr>
						<td><label>Second name: </label></td>
						<td><input type="text" name="surname" id="sname" value="${sname}"/></td>
					</tr>
					<tr>
						<td><label>E-mail: </label></td>
						<td><input type="text" name="email" id="email" value="${mail}" /><span id="valid"></span></td>
						
					</tr>
					<tr>
						<td><label>Password: </label></td>
						<td><input type="password" name="password" id="pass" value="${pass}" /></td>
					</tr>
					<tr>
						<td><label>Confirm password: </label></td>
						<td><input type="password" name="password2" id="confirmpass"  value="${cpass}"/><span id="validpass"></span></td>
					</tr>
					<tr>
						<td><label>User role: </label></td>
						<td><select name="userrole" id="engtype">
								<option value="2" >Customer Support Engineer</option>
								<option value="3">Provisioning Engineer</option>
								<option value="4">Installation Engineer</option>
								<option value="1">Administrator</option>
						</select></td>
					</tr>
				</table>
				<input type="button" name="buttonk" id="submitt"
					value="Save new account" class="button" />
			</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />