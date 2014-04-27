
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                listAction: '/Zephyrus/customersupport?action=list',

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
                       return '<input type="radio" name="radiobutton" class="radiobutton" value="'+data.record.id+'"/>';                   	 
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
			<center>
				<input name="reports" type="button" value="Reports" class="button" />
				<br /> <br /> <input name="users" type="button" value="Users"
					class="button" />
			</center>
		</div>
		<div class="main">
			<form id="createdevice" name="form3" method="post"
				action="/Zephyrus/changepass">
				 <div id="PersonTableContainer"></div>
				<%-- <label>Group Users by: </label><select name="usersparameter">
					<option value="1">Parameter1</option>
					<option value="2">Parameter2</option>
				</select>
				<p>
					<c:forEach var="user" items="${users}">
						<label> <input type="radio" name="radios"
							value="${user.id}" id="${user.id}" /> ${user.firstName}
						</label>
						<br />
					</c:forEach>
				</p>
				<br /> --%> 
				<br /> <input type="button" name="view_si" id="view_si"
					value="Service Instances" /> 
					 <input type="button" name="view_so" id="view_so"
					value="Service Orders" /> 
					<input type="button" name="button" id="changepass" value="Change password" class="button" />
			</form>
		</div>
		<div class="footer"></div>
	</div>

	<script>
	$(document).on('click', '#view_si', function(){
		 if( $(":radio[name=radiobutton]").filter(":checked").val()) {
	       	  var userId=$('input[name=radiobutton]:checked').val();
	       	  alert(userId);
	   	   	document.location.href="support/viewUserSI.jsp?id="+userId;	 
	         } else {
	       	  alert("Check user");
	         }   
	    }); 
	$(document).on('click', '#view_so', function(){
		 if( $(":radio[name=radiobutton]").filter(":checked").val()) {
	       	  var userId=$('input[name=radiobutton]:checked').val();
	       	  alert(userId);
	   	   	document.location.href="support/viewUserSO.jsp?id="+userId;	 
	         } else {
	       	  alert("Check user");
	         }   
	    }); 
	$(document).on('click', '#changepass', function(){
		 if( $(":radio[name=radiobutton]").filter(":checked").val()) {
       	  var userId=$('input[name=radiobutton]:checked').val();
       	  alert(userId);
   	   	document.location.href="support/changepass.jsp?id="+userId;	 
         } else {
       	  alert("Check user");
         }   	 			
	    });
	    
	</script>
</body>
</html>
