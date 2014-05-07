<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>
<script>
	 $().ready(function(){
	     $('#submitt').click(function(){
	    	 var fname = $('#firstname').val();
	    	 var sname = $('#secondname').val();
	         var email = $('#email').val();
	         var pass = $('#password').val();
	         var cpass = $('#confirmpass').val();
	         $.post('/Zephyrus/register',{firstname:fname,secondname:sname,email:email,password:pass,confirmpass:cpass},function(rsp){
	             $("#somediv").empty();
	             $('#somediv').text(rsp);	 
	             if (rsp == 'Account created!') {
	            	 window.location = "view/successfullRegister.jsp";
	             }
	             });
	         });

	         return false;
	     });
	     </script>
  <div class="navigation">
<a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a>
<a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a>
<a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a>
<a href="/Zephyrus/start">
<input type="button"	value="Get connected" class="meganavibutton" /></a>
  </div>
  <div class="main"> 
  <div id="registerBox">
            <h3> Registration </h3>
            <form action="" method="post" id="form1">
            <div id="somediv"></div>
            <table align="center" id="registerTable" >
            <tr>
            <td>
            First name:
            </td>
            <td>
            <input type="text" name="firstname" id="firstname" value="${firstname}" />
            </td>
            </tr>
            <tr>
            <td>
            Last name:
            </td>
            <td>
            <input type="text" name="secondname" id="secondname" value="${lastname}" />
            </td>
            </tr>
            <tr>
            <td>
            E-mail:
            </td>
            <td>
            <input type="text" name="email" id="email"/>
            </td>
            <td>
            <span id="valid"></span>
            </td>
            </tr>
            <tr>
            <td>
            Password:
            </td>
            <td>
            <input type="password" name="password" id="password"/>
            </td>
            </tr>
            <tr>
            <td>
            Confirm password:
            </td>
            <td>
            <input type="password" name="confirmpass" id="confirmpass" />
            </td>
            <td>
            <span id="validpass"></span>
            </td>
            </tr>
            </table>
            <br>
            <input type="button" name="create" id="submitt"
					value="Create account" class="button" />
            </form>
            
  </div>   
  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />