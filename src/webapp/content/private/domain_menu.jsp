<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<html>
  <head>
    <title></title>
  </head>

  <body bgcolor="#FFFFFF">
    <h1 align="center">Admin Menu <bean:write name="domainName"/></h1>

    <table width="80%" border="1" cellspacing="0" cellpadding="3"
           align="center">
      <tr>
        <th>Account</th>
        <th>Delete</th>
        <th>Active</th>
      </tr>
      <logic:iterate indexId="i" id="account" name="accounts">
        <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
          <td><bean:write name="account"/></td>
          <td align="center">D</td>
          <td align="center">A</td>
        </jamm:tr>
      </logic:iterate>
    </table>

    <br></br>

    <table width="80%" border="1" cellspacing="0" cellpadding="3"
           align="center">
      <tr>
        <th>Alias</th>
        <th>Destinations</th>
        <th>Delete</th>
        <th>Active</th>
      </tr>
      <logic:iterate indexId="i" id="alias" name="aliases">
        <jamm:tr index="i" evenColor="#E6E6FA" oddColor="#FFDEAD">
          <td><bean:write name="alias"/></td>
          <td>user@domain1.example<br>user@domain2.example</td>
          <td align="center">D</td>
          <td align="center">A</td>
        </jamm:tr>
      </logic:iterate>
    </table>

  </body>
</html>
