<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=en"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mapping request</title>


    <script>
        var map;
        var geocoder;
        var myCenter = new google.maps.LatLng(50.4020355, 30.5326905);
        var marker;

        function initialize() {

            geocoder = new google.maps.Geocoder();
            var mapProp = {
                center: myCenter,
                zoom: 10,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

            google.maps.event.addListener(map, 'click', function (event) {
                placeMarker(event.latLng);
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
                    $("#gaddress").val(results[0].formatted_address);
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
                $.post('MapingServlet',{latitude:latituded,longitude:longituded},function(rsp){
                    $('#myresp').text(rsp);

                });

                return false;
            });

        });
    </script>
</head>
<body>

        
 <form id="myform" method="post">
    <table border="2" bordercolor="black">
        <tr>
            <td><div id="googleMap" style="width:500px;height:380px;"></div></td>
           <td>
               Address
                <input type="text" name="gaddress" id="gaddress" size="66"/>
                    <input type="submit" value="Submit" id="submit"/>
           </td>
        </tr>

    </table>
    <input type="hidden" name="latitude" id="latitude" size="66"/>
    <input type="hidden" name="longitude" id="longitude" size="66"/>
    <div id="myresp"></div>
</form>
</body>
</html>



