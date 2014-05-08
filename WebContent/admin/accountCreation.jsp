<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
	<script>
	 $().ready(function(){
	     $('#submit').click(function(){
	    	 var fname = $('#firstname').val();
	    	 var sname = $('#secondname').val();
	         var email = $('#email').val();
	         var pass = $('#password').val();
	         var cpass = $('#confirmpass').val();
	         var etype = $('#engtype').val();
	         $.post('/Zephyrus/createaccount',{firstname:fname,secondname:sname,email:email,password:pass,confirmpass:cpass,engtype:etype},function(rsp){
	             $("#errordiv").empty();
	             
	             if (rsp == 'Account created!') {
	            	 document.forms["form1"].reset();
	            	 $('#messagediv').text(rsp);	
	             } else {
	            	 $('#errordiv').text(rsp);	
	             }
	             });
	         });

	         return false;
	     });
	     </script>
<style>
hr {
    border: none;
    background-color: #508eeb; 
    color: #508eeb; 
    height: 2px;
   }
</style>
<div class="navigation">
  <div style="text-align:center"><a href="/Zephyrus/reportChoosing">
  <input name="reports" type="button" value="Reports" class="navibutton" /></a></div>
  <div style="text-align:center"><a href="/Zephyrus/admin">
  <input name="accounts" type="button" value="Accounts" class="navibutton" /></a></div>
  <div style="text-align:center"><a href="/Zephyrus/admin/accountCreation.jsp"> 
  <input type="button"	value="Create account" class="navibutton" /></a></div>
<br>
<hr>
<br>
<div style="text-align:center"><a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
</div>
<div class="main">
<center><h2>Create new account</h2></center>
  			<form id="createaccount" name="form1" method="post" action="" id="form1">
					<font color="red"><div id="errordiv"></div></font>
					<font color="green"><div id="messagediv"></div></font>
				<table>
					<tr>
						<td><label>First name: </label></td>
						<td><input type="text" name="firstname" id="firstname" value="${fname}"/></td>
					</tr>
					<tr>
						<td><label>Second name: </label></td>
						<td><input type="text" name="secondname" id="secondname" value="${sname}"/></td>
					</tr>
					<tr>
						<td><label>E-mail: </label></td>
						<td><input type="text" name="email" id="email" value="${mail}"/><span id="valid"></span></td>
						
					</tr>
					<tr>
						<td><label>Password: </label></td>
						<td><input type="password" name="password" id="password" value="${pass}"/></td>
					</tr>
					<tr>
						<td><label>Confirm password: </label></td>
						<td><input type="password" name="confirmpass" id="confirmpass"  value="${cpass}"/><span id="validpass"></span></td>
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
				<tr>
				</tr>
				<tr>
				<td></td>
				<td>
				<input type="button" name="create" id="submit"
					value="Create account" class="button" />
					</td>
					</tr>
					</table>
			</form>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />