<jsp:include page="../WEB-INF/jsphf/header.jsp" />

<link rel="stylesheet" href="/Zephyrus/resources/css/jquery-ui-1.10.4.min.css">
<script src="/Zephyrus/resources/javascript/jquery-ui-1.10.4.min.js"></script>
<script src="/Zephyrus/resources/javascript/datePicker.js"></script>

<div class="navigation">
<a href="Zephyrus/view/chooseReport" class="current">
   <input type="button" value="Report" />
   </a>
</div>
<div class="main">
<div id="chooseReportBox">
  <form action="Zephyrus/view/generateReport" method="post">
  <table align="center">
  <tr>
  <td>
  Report type
  </td>
  <td>
  <select name="serviceType">
    <option value="1">SI report</option>
    <option value="2">RI report</option>
  </select>
  </td>
  </tr>
  <tr>
  <td>
  Report subtype
  </td>
  <td>
  <select name="serviceSubType">
    <option value="1">New orders per period</option>
    <option value="2">Deleted orders per period</option>
    <option value="3">Most profitable router</option>
    <option value="4">CIA report</option>
  </select>
  </td>
  </tr>
    <tr>
  <td>
  Date from
  </td>
  <td>
<input type="text" id="fromdate"  name="fromDate" value="">
  </td>
  </tr>
      <tr>
  <td>
  Date to
  </td>
  <td>
<input type="text" id="todate" name="toDate" value="">
  </td>
  </tr>
  <tr>
  <td />
  <td>
  <br>
  <input type="submit" value="Generate report" class="button">
  </td>
  </tr>
</table>
<br>
 </form>
 </div>
</div>

<jsp:include page="../WEB-INF/jsphf/footer.jsp" />