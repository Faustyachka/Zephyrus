<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Main index</title>
<link href="../resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link href="css/metro/crimson/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.10.4.custom.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.10.4.custom.js" type="text/javascript"></script>
<script src="js/jquery.jtable.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //initialize jTable
        $('#PersonTableContainer').jtable({
            title: 'Table of people',
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'Name ASC',
            actions: {
                listAction: '/Zephyrus/admin?action=list',

            },
            recordsLoaded: function(event, data) {
                $('.serialcheck').click(function() {
                	var id = parseInt($(this).val(), 10);
                	alert(id);
                	$.post('blocking',{id:id},function(rsp){
	                         
                    });
                       	                         
                       
                });
            },
            fields: {
                id: {
                    key: true,
                    list: false
                },
                firstName: {
                    title: 'Customer Name',
                    width: '30%'
                },
                lastName: {
                    title: 'Customer Second Name',
                    width: '30%'
                },
                email: {
                    title: 'Email',
                    width: '30%'
                },
                
                status: {
                    title: 'Blocked',                   
                    display: function (data) {
                    	if (data.record.status==1) {
                       return '<input type="checkbox" class = "serialcheck" name="blockstatus" value="'+data.record.id+'"/>';
                    	} else {
                    		return '<input type="checkbox" class = "serialcheck" name="blockstatus" checked="checked" value="'+data.record.id+'"/>';
                    	}
                    },
                    create: false,
                    edit: false

                },
                roleId: {
                    title: 'Role',
                    width: '20%',
                    
                }
                
            }
        });  
        $('#PersonTableContainer').jtable('load');
    });
</script>
</head>
<body>
<div class="container">
 <div class="header">
 <input type="button" name="button" value="Log out" class="logout" />
 </div>
 <div class="navigation">
 <center><input name="reports" type="button" value="Reports" class="button" />
 <br />
 <br />
 <input name="users" type="button" value="Users" id="accounts" class="button" />
 <br />
 <br />
 <input name="users" type="button" value="Tasks" class="button" />
 </center></div>
 <div class="main">
 <form id="createdevice" name="form3" method="post" action="/Zephyrus/blocking">

 <div id="PersonTableContainer"></div>

  <input type="button" name="newuser" id="newuser" value="Create Account" class="button"/>

</form>
</div>

<div class="footer"></div>
</div>
<script >

 $(document).on('click', '#newuser', function(){
	$.ajax({
	    url: "/Zephyrus/admin/accountcreation.jsp",
	    success:function(result){
	        document.location.href="/Zephyrus/admin/accountcreation.jsp";
	    }});
    });
 
$(document).on('click', '#accounts', function(){
	$.ajax({
	    url: "/Zephyrus/admin",
	    success:function(result){
	        document.location.href="/Zephyrus/admin";
	    }});
    });
</script>
</body>
</html>
