<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link href="/Zephyrus/resources/css/metro/crimson/jtable.css"
	rel="stylesheet" type="text/css" />
<link href="/Zephyrus/resources/css/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" type="text/css" />
<script src="/Zephyrus/resources/javascript/jquery-1.10.2.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.custom.js"
	type="text/javascript"></script>
<script src="/Zephyrus/resources/javascript/jquery.jtable.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//initialize jTable
						$('#PersonTableContainer')
								.jtable(
										{
											title : 'Table of people',
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : true, //Enable sorting
											defaultSorting : 'Name ASC',
											actions : {
												listAction : '/Zephyrus/customersupport?action=list',

											},

											fields : {
												id : {
													key : true,
													list : false
												},
												firstName : {
													title : 'Customer Name'
												},
												lastName : {
													title : 'Customer Second Name'
												},
												email : {
													title : 'Email'
												},

												status : {
													title : 'Choose',
													display : function(data) {
														return '<input type="radio" name="radiobutton" class="radiobutton" value="'+data.record.id+'"/>';
													},
													create : false,
													edit : false

												},
												roleId : {
													title : 'Role',
													display : function(data) {
														return data.record.role.roleName;
													}

												}

											}
										});
						$('#PersonTableContainer').jtable('load');
					});
</script>
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
		<br /> <input type="button" name="view_si" id="view_si"
			value="Service Instances and Service Orders" /> <input type="button"
			name="button" id="changepass" value="Change password" class="button" />
	</form>
</div>
<div class="footer"></div>

<script>
	$(document).on('click', '#changepass', function() {
		if ($(":radio[name=radiobutton]").filter(":checked").val()) {
			var userId = $('input[name=radiobutton]:checked').val();
			document.location.href = "support/changepass.jsp?id=" + userId;
		} else {
			alert("Check user");
		}
	});
</script>
<jsp:include page="../WEB-INF/jsphf/footer.jsp" />
