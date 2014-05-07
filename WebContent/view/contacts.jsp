<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="resources/javascript/placing-map.js"></script>
<script src="resources/javascript/jquery-placingRequest.js"></script>
<style type ="text/css" >
</style>
<div class="navigation">
<a href="/Zephyrus/about"> 
<input type="button"	value="About Us" class="navibutton" /></a>
<a href="/Zephyrus/services">
<input type="button"	value="Services" class="navibutton" /></a>
<a href="/Zephyrus/contacts">
<input type="button"	value="Contacts" class="navibutton" /></a><a href="/Zephyrus/start">
<input type="button"	value="Get connected" class="meganavibutton" /></a>
  </div>
<div class="main">
	<div id="map-canvas"></div>
	<br>
	<div style="text-align:center">Contact us on +38(044)432-16-63 or zephyrus.info@gmail.com.</div>

</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />