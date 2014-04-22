<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
  <form action="j_security_check" method=post>
    <div id="loginBox">
          <h3> Login: </h3>
            <input type="text" size="20" name="j_username">

          <h3> Password: </h3>
            <input type="password" size="20" name="j_password">

        <p><input type="submit" value="Log in"></p>
    </div>
</form>
  </div>
  <div class="main"> 
  <div id="registerBox">
            <h3> Registration </h3>
            <form action="Zephyrus/register" method="post">
            <table align="center" id="registerTable" >
            <tr>
            <td>
            First name:
            </td>
            <td>
            <input type="text" name="fname" id="fname" value="${firstname}" />
            </td>
            </tr>
            <tr>
            <td>
            Last name:
            </td>
            <td>
            <input type="text" name="lname" id="sname" value="${lastname}" />
            </td>
            </tr>
            <tr>
            <td>
            E-mail:
            </td>
            <td>
            <input type="text" name="email" id="email"/><span id="valid"></span>
            </td>
            </tr>
            <tr>
            <td>
            Password:
            </td>
            <td>
            <input type="text" name="password" id="pass"/>
            </td>
            </tr>
            <tr>
            <td>
            Confirm password:
            </td>
            <td>
            <input type="text" name="cpassword" id="confirmpass" /><span id="validpass"></span>
            </td>
            </tr>
            </table>
            <br>
            <input type="submit" value="Create an account" />
            </form>
            
  </div>   
  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />