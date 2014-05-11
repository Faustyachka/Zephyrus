<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="resources/javascript/placing-map.js"></script>
<script src="resources/javascript/jquery-placingRequest.js"></script>
<style type ="text/css" >
#loader {
display: none;
opacity: 0; 
}
</style>
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
Welcome to the website of internet service provider Zephyrus!
<br>
<br>
Here you can find all the information you need about our company and its services.
	

</div></div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />