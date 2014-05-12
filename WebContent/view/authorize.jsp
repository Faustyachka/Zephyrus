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
  <div style="text-align:center">
  You successfully chose the service! 
  <br>
  <br>
  Now, to save your order, please, log in the system.
  <br>
  <br>
  <a href="/Zephyrus/login"><input type="button" value="Log in existing account" class="button" /></a> or
  <a href="/Zephyrus/view/register.jsp">
<input type="button"	value="Register in the system" class="button" /></a>
  </div>
  </div>
  </div>
  
  <jsp:include page="../WEB-INF/jsphf/footer.jsp" />