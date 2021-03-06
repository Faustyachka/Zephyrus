<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="/Zephyrus/resources/javascript/placing-map.js"></script>
<script src="/Zephyrus/resources/javascript/jquery-placingRequest.js"></script>
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
	<div id="map-canvas"></div>
	<div id="location">
			Your desired location: 
			<input type="text" name="address" id="address" size="50" />
			<input type="button" value="Find services" id="submit" disabled="disabled" class="button"/>  
			<form action="/Zephyrus/proceedOrder" method="POST">
			<input type="hidden" name="latitude" id="latitude" /> 
			<input type="hidden" name="longitude" id="longitude" /> 
			<center>
			<div id="loading"></div>
			</center>		
			<div id="somediv" class="somediv"></div>			
			<input type="submit" value="Proceed to order" id="proceed" disabled="disabled" class="button"/>
		</form>
	</div>

</div>
</div>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />