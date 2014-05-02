<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script type="text/javascript" src="/Zephyrus/resources/javascript/dataValidation.js" > </script>

<div class="navigation"></div>
  <div class="main"> 
  <div id="registerBox">
            <h3> Registration </h3>
            <form action="/Zephyrus/register" method="post">
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
            <input type="password" name="password" id="pass"/>
            </td>
            </tr>
            <tr>
            <td>
            Confirm password:
            </td>
            <td>
            <input type="password" name="cpassword" id="confirmpass" />
            </td>
            <td>
            <span id="validpass"></span>
            </td>
            </tr>
            </table>
            <br>
            <input type="submit" value="Create an account" class="button"/>
            </form>
            
  </div>   
  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />