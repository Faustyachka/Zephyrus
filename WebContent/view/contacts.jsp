<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="/Zephyrus/resources/javascript/placing-map.js"></script>
<script src="/Zephyrus/resources/javascript/jquery-placingRequest.js"></script>
<style type ="text/css" >
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
	<div id="map-canvas"></div>
	<br>
	<div style="text-align:center">Contact us on +38(044)432-16-63 or zephyrus.info@gmail.com.</div>

</div>
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />