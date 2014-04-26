$(document).ready(function() {
        $('#email').change(function() {
            if($(this).val() != '') {
                var pattern = /^([a-z0-9_\.-])+@[a-z0-9-]+\.([a-z]{2,4}\.)?[a-z]{2,4}$/i;
                if(pattern.test($(this).val())){
                    $(this).css({'border' : '1px solid #569b44'});
                    $('#valid').text('Ok');
                } else {
                    $(this).css({'border' : '1px solid #ff0000'});
                    $('#valid').text('Not valid');
                }
            } else {
                $(this).css({'border' : '1px solid #ff0000'});
                $('#valid').text('Can not be empty');
            }
        });
    }); 
 $(document).ready(function() {
     $('#confirmpass').change(function() {
   	 $(".error").hide();
    	    var valueX = $("#pass").val();
    	    var valueY = $("#confirmpass").val();
    	    if (valueX != valueY) {
    	    	$(this).css({'border' : '1px solid #ff0000'});
    	    	$('#validpass').text('Dont matches');
    	    } else {
    	    	$(this).css({'border' : '1px solid #569b44'});
    	    	$('#validpass').text('Ok');
    	    }
     });
 });
 $(document).ready(function() {
     $('#serialNum').change(function() {
    	 if($(this).val() != '') {
             var pattern = /^([A-Z0-9]){11}$/;
             if(pattern.test($(this).val())){
                 $(this).css({'border' : '1px solid #569b44'});
                 $('#valid').text('Ok');
             } else {
                 $(this).css({'border' : '1px solid #ff0000'});
                 $('#valid').text('Not valid');
             }
         } else {
             $(this).css({'border' : '1px solid #ff0000'});
             $('#valid').text('Can not be empty');
         }
     });
 });