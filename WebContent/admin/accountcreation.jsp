<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Main index</title>
<link href="../resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  $(document).ready(function() {
        $('#email').change(function() {
            if($(this).val() != '') {
                var pattern = /^([a-z0-9_\.-])+@[a-z0-9-]+\.([a-z]{2,4}\.)?[a-z]{2,4}$/i;
                if(pattern.test($(this).val())){
                    $(this).css({'border' : '1px solid #569b44'});
                    $('#valid').text('Ok');
                } else {
                    $(this).css({'border' : '1px solid #ff0000'});
                    $('#valid').text('Not valid');
                }
            } else {
                $(this).css({'border' : '1px solid #ff0000'});
                $('#valid').text('Can not be empty');
            }
        });
    }); 
 $(document).ready(function() {
     $('#confirmpass').change(function() {
    	 $(".error").hide();
    	    var valueX = $("#pass").val();
    	    var valueY = $("#confirmpass").val();
    	    if (valueX != valueY) {
    	    	$(this).css({'border' : '1px solid #ff0000'});
    	    	$('#validpass').text('Dont matches');
    	    } else {
    	    	$(this).css({'border' : '1px solid #569b44'});
    	    	$('#validpass').text('Ok');
    	    }
 });
 });

    </script>
</head>

<body>
	<div class="container">
		<div class="header">
			<input type="button" name="button" value="Log out" class="logout" />
		</div>
		<div class="navigation">
			<center>
				<input name="tasks" type="button" value="Tasks" class="button" /> <br />
				<br /> <input name="reports" type="button" value="Reports"
					class="button" /> <br /> <br /> 
					<input name="accounts" id="accounts"
					type="button" value="Users" class="button" />
			</center>
		</div>
		<div class="main">
			<center>
				<h2>Create new account</h2>
			</center>
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
		<div class="footer"></div>
	</div>
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
	 
	 $(document).on('click', '#accounts', function(){
			$.ajax({
			    url: "/Zephyrus/admin",
			    success:function(result){
			        document.location.href="/Zephyrus/admin";
			    }});
		    });

	</script>
</body>
</html>