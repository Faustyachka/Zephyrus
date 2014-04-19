$().ready(function(){
            $('#submit').click(function(){
                var latituded = $('#latitude').val();
                var longituded = $('#longitude').val();
                $.post('mapping',{latitude:latituded,longitude:longituded},function(rsp){
                    $("#services").empty();
                	$.each(rsp, function(key, productcatalog) { 
                    	$("#services").append("<input type='radio' name='services' id='"+key+"' value = '"+key+"'> "+ productcatalog.productName+" <br>");
                    	                         
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