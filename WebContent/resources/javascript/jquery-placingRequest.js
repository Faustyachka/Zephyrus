$().ready(function(){
            $('#submit').click(function(){
                var latituded = $('#latitude').val();
                var longituded = $('#longitude').val();
                var addresss = $('#address').val();
                $.post('mapping',{latitude:latituded,longitude:longituded, address:addresss},function(rsp){
                    $("#somediv").empty();
                	$.each(rsp, function(key, prodcatalogserv) { 
                    	$("#somediv").append("<input type='radio' name='services' id='"+prodcatalogserv.id+"' value = '"+prodcatalogserv.id+"'> "+ prodcatalogserv.serviceName + ", " + prodcatalogserv.price + "$ month" +" <br>");
                    	                         
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