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
    	    var valueX = $("#password").val();
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
     $('#password').change(function() {
    	    var valueX = $("#password").val();
    	    var valueY = $("#confirmpass").val();
    	    if (valueX != valueY) {
    	    	$('#confirmpass').css({'border' : '1px solid #ff0000'});
    	    	$('#validpass').text('Dont matches');
    	    } else {
    	    	$('#confirmpass').css({'border' : '1px solid #569b44'});
    	    	$('#validpass').text('Ok');
    	    }
     });
 });
 $(document).ready(function() {
     $('#serialNum').change(function() {
    	 if($(this).val() != '') {
             var pattern = /^([A-Za-z]){3}([0-9]){4}([A-Za-z0-9]){4}$/;
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
     $('#from').change(function() {
    	 if($(this).val() != '') {
             var pattern = /^([0-9]){4}-([0-9]){2}-([0-9]){2}$/;
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
     $('#to').change(function() {
    	 if($(this).val() != '') {
             var pattern = /^([0-9]){4}-([0-9]){2}-([0-9]){2}$/;
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