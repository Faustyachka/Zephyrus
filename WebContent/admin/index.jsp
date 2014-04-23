<jsp:include page="../WEB-INF/jsphf/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/demo_table.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/demo_table_jui.css" rel="stylesheet"
	type="text/css" />
<script src="resources/javascript/jquery.dataTables.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#users").dataTable({
			"sPaginationType" : "full_numbers",
			"bJQueryUI" : true
		});
	});
</script>
	<div class="navigation">
		<center>
			<br /> <input name="reports" type="button" value="Reports"
				class="button" /> <br /> <br /> <input name="accounts"
				type="button" value="Accounts" class="button" />
		</center>
	</div>
	<div class="main">
		<div>
			<table id="users" class="display">
				<thead>
					<tr>
						<th><u>First name</u></th>
						<th><u>Second name</u></th>
						<th><u>Role</u></th>
						<th><u>Blocked</u></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="customerUser" items="${users}">
						<tr>
							<td>${customerUser.firstName }</td>
							<td>${customerUser.lastName }</td>
							<td>${customerUser.role.roleName}</td>
							<td><c:choose>
									<c:when test="${customerUser.status == 1}">
										<input type="checkbox" class="serialcheck" value="${customerUser.id}"
											checked="checked" />
									</c:when>
									<c:otherwise>
										<input type="checkbox" class="serialcheck" value="${customerUser.id}" />
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<a href="admin/accountCreation.jsp"> <input type="button"
			value="Create account" class="button" />
		</a>
	</div>
	<script>
		$('.serialcheck').click(function() {
			var id = $(this).val();
			$.post('blocking', {
				id : id
			}, function(rsp) {
			});
		});
	</script>
	<jsp:include page="../WEB-INF/jsphf/footer.jsp" />