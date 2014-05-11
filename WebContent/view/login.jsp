<jsp:include page="../WEB-INF/jsphf/header.jsp" />

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
  <center>
  <form action="j_security_check" method=post>
    <div id="loginBox">
          <h3> E-mail: </h3>
            <input type="text" size="20" name="j_username">

          <h3> Password: </h3>
            <input type="password" size="20" name="j_password">

        <p><input type="submit" value="Log in" class="button"></p>
    </div>
</form>
</center>
  </div>
  </div>
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />