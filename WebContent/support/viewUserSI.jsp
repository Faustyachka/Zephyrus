<%@page import="java.util.ArrayList"%>
<%@page import="com.zephyrus.wind.model.ServiceOrder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Main index</title>
<link href="style.css" rel="stylesheet" type="text/css" />
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
                listAction: '/Zephyrus/getUsersSI?action=list',

            },
            
            fields: {
                id: {
                    key: true,
                    list: false
                },
                SIStatus: {
                    title: 'SI Status',
                    width: '30%'
                },
                product: {
                    title: 'Prodiuct',
                    width: '40%'
                },
                startDate: {
                    title: 'Start date',
                    width: '30%'
                },
    
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
			<center>
				<h2>List of user's service orders</h2>
			</center>
			<label>${error}</label>
			<form id="changepassword" name="form2" method="post" action="/Zephyrus/createnewpass">
				
			</form>
		</div>
		<div class="footer"></div>
	</div>
</body>
</html>