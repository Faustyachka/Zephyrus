$().ready(function(){
            $('#submit').click(function(){
                var latituded = $('#latitude').val();
                var longituded = $('#longitude').val();
                var addresss = $('#address').val();
                $.post('/Zephyrus/mapping',{latitude:latituded,longitude:longituded, address:addresss},function(rsp){
                    $("#somediv").empty();
                	$.each(rsp, function(key, prodcatalog) { 
                		var serviceName = prodcatalog.serviceType;
                    	$("#somediv").append("<input type='radio' name='services' id='"+prodcatalog.id+"' value = '"+prodcatalog.id+"'> "+ serviceName.serviceType + ", " + prodcatalog.price + "$ month" +" <br>");
                    	                         
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