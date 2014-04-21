<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<div class="navigation">
<a href="Zephyrus/view/login" class="current">
   <input type="button" value="Return Home" />
   </a>
   <br>
   <br>
   Export report:
   <br>
   <form action="Zephyrus/view/exportReport" method="post">
   <select name="exportType">
    <option value="1">To Excel</option>
    <option value="2">To CSV</option>
  </select>
  <br>
   <input type="submit" value="Export" />
   </form>
  </div>
  <div class="main">
  <div id="reportTable">
  <br>
  <table align="center" border="2px">
	<tr>
    <td>
    ID
    </td>
    <td>
    User
    </td>
    <td>
    Location
    </td>
    <td>
    Service
    </td>
    <td>
    Date
    </td>
    </tr>
    <tr>
    <td>
    1
    </td>
    <td>
    User1
    </td>
    <td>
    Address1
    </td>
    <td>
    Black internet
    </td>
    <td>
    30/10/2013
    </td>
    </tr>
</table>
 </div>
  </div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />