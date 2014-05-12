$().ready(function(){
            $('#submit').click(function(){
            	
                var latituded = $('#latitude').val();
                var longituded = $('#longitude').val();
                var addresss = $('#address').val();  
                $("#somediv").empty(); 
                $('#submit').attr("disabled", true); 
                $('#proceed').attr("disabled", true); 
                $("#loading").append("<img src='/Zephyrus/resources/css/images/animation.gif'/>");
                $.post('/Zephyrus/mapping',{latitude:latituded,longitude:longituded, address:addresss},function(rsp){ 
                	$('#submit').attr("disabled", false); 
                	$("#loading").empty();
                	if (rsp=='noServices') {
                		$("#somediv").append('<font color="red">No services available. You are too far from provider location.</font>');
                	}                                      
                	$.each(rsp, function(index, prodcatalog) { 
                		var serviceName = prodcatalog.serviceType;
                    	$("#somediv").append("<input type='radio' class='radio' name='services' id='"+prodcatalog.id+"' value = '"+prodcatalog.id+"'> "+ serviceName.serviceType + ", " + prodcatalog.price + "$ month" +" <br>");
                    	                         
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
        $(document).ready(function(){
        $('.somediv').on('click', '.radio', function() {
            if (this.getAttribute('checked')) {
                $(this).removeAttr('checked');
                $(this).siblings('.radio').attr('checked', false);
            } else {
                $(this).attr('checked', true);
                $(this).siblings('.radio').removeAttr('checked');
            }
            var inp = document.getElementsByName('services');
            var counter=0;
            for (var i = 0; i < inp.length; i++) {
                if (inp[i].type == "radio" && inp[i].checked) {
                	counter=counter+1;
                } 
            } if (counter>0) {
            	$('#proceed').attr("disabled", false); 
            } else {
            	$('#proceed').attr("disabled", true); 
            }
            /*if ($('input[name="services"]:checked')) {
            	alert ("cheked");
            }*/
        });
        });