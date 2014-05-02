<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation"></div>
  <div class="main"> 
  <center>
  <form action="j_security_check" method=post>
    <div id="loginBox">
          <h3> Login: </h3>
            <input type="text" size="20" name="j_username">

          <h3> Password: </h3>
            <input type="password" size="20" name="j_password">

        <p><input type="submit" value="Log in" class="button"></p>
    </div>
</form>
</center>
  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />