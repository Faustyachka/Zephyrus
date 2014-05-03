var geocoder;
var map;
var marker;
//

function initialize() {
			var input = document.getElementById('address');
			
  geocoder = new google.maps.Geocoder();
  var latlng = new google.maps.LatLng(50.4020355, 30.5326905);
  var mapOptions = {
    zoom: 8,
    center: latlng
  };
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  google.maps.event.addListener(map, 'click', function (event) {
      placeMarker(event.latLng);
  });
  
  getProviders();
  autocomplete = new google.maps.places.Autocomplete(input);
  
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
	  codeAddress();
	    });
  
}

function codeAddress() {
  var address = document.getElementById('address').value;
  geocoder.geocode( { 'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      map.setCenter(results[0].geometry.location);
      placeMarker(results[0].geometry.location);
      map.setZoom(16);         
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}

function placeMarker(location) {
  	if (marker) {
          marker.setPosition(location);
      } else {
          marker = new google.maps.Marker({
              position: location,
              map: map
          });
      }
  	$("#longitude").val(location.lng());
    $("#latitude").val(location.lat());

    geocoder.geocode({ 'latLng': location}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            $("#address").val(results[0].formatted_address);
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

function getProviders(){
	$.post('/Zephyrus/providerLocations',function(rsp){
    	$.each(rsp, function(key, provider) { 
    		var coord = provider.locationCoord;
    		var arr = coord.split(',');
    		var lat = parseFloat(arr[0]);
    		var lng = parseFloat(arr[1]);
    		var position = new google.maps.LatLng(lat, lng);
    		
    		marker2 = new google.maps.Marker({
                position: position,
                map: map,
                icon: 'resources/javascript/blue_MarkerP.png'
            });
        	                         
        });

    });
}


google.maps.event.addDomListener(window, 'load', initialize);