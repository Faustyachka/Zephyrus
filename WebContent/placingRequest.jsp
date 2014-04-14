<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Placing request</title>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&sensor=false&language=en"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script>
var geocoder;
var map;
var marker;
//

function initialize() {
			var input = document.getElementById('address');
			var options = {
					types: ['(cities)'],
					  componentRestrictions: {country: 'ua'}
			};

			
  geocoder = new google.maps.Geocoder();
  var latlng = new google.maps.LatLng(50.4020355, 30.5326905);
  var mapOptions = {
    zoom: 8,
    center: latlng
  }
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  google.maps.event.addListener(map, 'click', function (event) {
      placeMarker(event.latLng);
  });

  autocomplete = new google.maps.places.Autocomplete(input, options);
  
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


google.maps.event.addDomListener(window, 'load', initialize);

    </script>
    <script>
        $().ready(function(){
            $('#submit').click(function(){
                var latituded = $('#latitude').val();
                var longituded = $('#longitude').val();
                $.post('mapping',{latitude:latituded,longitude:longituded},function(rsp){
                	//var $ul = $('<ul>').appendTo($('#somediv')); // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
                    $("#somediv").empty();
                	$.each(rsp, function(key, productcatalog) { 
                    	$("#somediv").append("<input type='radio' name='services' id='"+key+"' value = '"+key+"'> "+ productcatalog.productName+" <br>");
                    	                         
                    });

                });

                return false;
            });

        });
        $(document).ready(function(){
            $('#address').keypress(function(e){
              if(e.keyCode==13)
              codeAddress();
            });
        }); 
        $().ready(function(){
            $("#proceed").click(function(){       
                  if( $(":radio[name=services]").filter(":checked").val()) {
                	  alert($('input[name=services]:checked').val());
                  } else {
                	  alert("Check the service");
                  }        
        });
        });
    </script>
  </head>
  <body>
    <table border="2" bordercolor="black">
        <tr>
            <td><div id="map-canvas" style="width:500px;height:380px;"></div></td>
           <td>         
               Your desired location:
                <input type="text" name="address" id="address" size="66"/>               
                <input type="button" value="Accept" id="submit"/>                
                <div id="somediv"></div>                
                <input type="button" value="Proceed to order" id="proceed"/>              
           </td>
        </tr>
    </table>
    <input type="hidden" name="latitude" id="latitude" size="66"/>
    <input type="hidden" name="longitude" id="longitude" size="66"/>     
  </body>
</html>
