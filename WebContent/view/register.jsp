<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
<script>
	 $().ready(function(){
	     $('#submitt').click(function(){
	    	 $('#submitt').attr("disabled", true); 
	    	 $("#loading").append("<img src='/Zephyrus/resources/css/images/animation.gif'/>");
	    	 var fname = $('#firstname').val();
	    	 var sname = $('#secondname').val();
	         var email = $('#email').val();
	         var pass = $('#password').val();
	         var cpass = $('#confirmpass').val();
	         $.post('/Zephyrus/register',{firstname:fname,secondname:sname,email:email,password:pass,confirmpass:cpass},function(rsp){
	        	 $("#loading").empty();	        	 
	             $("#somediv").empty();	 
	             if (rsp == 'Account created!') {
	            	 window.location = "/Zephyrus/view/successfullRegister.jsp";
	             } else {
		             $('#somediv').text(rsp);
		             $('#submitt').attr("disabled", false); 
	             }
	             });
	         });

	         return false;
	     });
	     </script>
<div id="columns">
<div id="navigation">
<div style="text-align:center"><a href="/Zephyrus/view/about.jsp"> 
<input type="button"	value="About Us" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/services.jsp">
<input type="button"	value="Services" class="navibutton" /></a></div>
<div style="text-align:center"><a href="/Zephyrus/view/contacts.jsp">
<input type="button"	value="Contacts" class="navibutton" /></a></div>
<br>
<br>
<div style="text-align:center"><a href="/Zephyrus/view/start.jsp">
<input type="button"	value="Get connected" class="meganavibutton" /></a></div>
  </div>
  <div id="main"> 
  <div id="registerBox">
            <h3> Registration </h3>
            <form action="" method="post" id="form1">
            <font color="red"><div id="somediv"></div></font>
            <table align="center" id="registerTable" >
            <tr>
            <td>
            First name:
            </td>
            <td>
            <input type="text" name="firstname" id="firstname" value="${firstname}" />
            </td>
            <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				A-Z, a-z
			</td>
            </tr>
            <tr>
            <td>
            Last name:
            </td>
            <td>
            <input type="text" name="secondname" id="secondname" value="${lastname}" />
            </td>
            <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				A-Z, a-z
			</td>
            </tr>
            <tr>
            <td>
            E-mail:
            </td>
            <td>
            <input type="text" name="email" id="email"/>
            </td>
             <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				Example: alex@example.com
			</td>
            </tr>
            <tr>
            <td>
            Password:
            </td>
            <td>
            <input type="password" name="password" id="password" maxlength="30"/>
            </td>
            <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				Minimal length: 5 symbols
			</td>
            </tr>
            <tr>
            <td>
            Confirm password:
            </td>
            <td>
            <input type="password" name="confirmpass" id="confirmpass" maxlength="30"/>
            </td>
             <td align="left" style="font-size: 10px;
				padding-left: 5px;">
				Repeat password
			</td>
            </tr>
            </table>
            <br>
            <input type="button" name="create" id="submitt"
					value="Create account" class="button" /> <br>
					<div id="loading"></div>
            </form>
            
  </div>   
  </div>
  </div>
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />