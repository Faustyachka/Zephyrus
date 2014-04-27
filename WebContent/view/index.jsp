<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="resources/javascript/placing-map.js"></script>
<script src="resources/javascript/jquery-placingRequest.js"></script>

<div class="navigation"></div>
<div class="main">
	<div id="map-canvas"></div>
	<div id="location">
			Your desired location: 
			<input type="text" name="address" id="address" size="50" />
			<input type="button" value="Accept" id="submit" />  
			<form action="/Zephyrus/proceedOrder" method="POST">
			<input type="hidden" name="latitude" id="latitude" /> 
			<input type="hidden" name="longitude" id="longitude" /> 
			<div id="somediv">No service available</div>
			<input type="submit" value="Proceed to order" id="proceed" />
		</form>
	</div>

</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />