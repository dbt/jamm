<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">Admin Menu <bean:write name="domainName"/></h1>

<html:form action="/private/domain_account" method="GET">
  <table width="80%" border="1" cellspacing="0" cellpadding="3"
         align="center">
    <tr>
      <th>Account</th>
      <th>Delete</th>
      <th>Active</th>
      <th>Admin</th>
    </tr>
    <logic:iterate id="account" name="domainAccountForm" property="originalAccounts">
      <html:hidden property="originalAccounts"
      value="<%=account.toString()%>"/>
    </logic:iterate>
    <logic:iterate indexId="i" id="account" name="accounts">
      <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
        <td><bean:write name="account"/></td>
        <td align="center">
          <html:multibox property="accountsToDelete">
            <bean:write name="account"/>
          </html:multibox>
        </td>
        <td align="center">
          <html:multibox property="accountsToActivate">
            <bean:write name="account"/>
          </html:multibox>
        </td>
        <td align="center">
          <html:multibox property="accountsToAdmin">
            <bean:write name="account"/>
          </html:multibox>
        </td>
      </jamm:tr>
    </logic:iterate>
  </table>
  <html:submit>
    Submit Changes
  </html:submit>
</html:form>

<br/>

<table width="80%" border="1" cellspacing="0" cellpadding="3"
       align="center">
  <tr>
    <th>Alias</th>
    <th>Destinations</th>
    <th>Delete</th>
    <th>Active</th>
    <th>Admin</th>
  </tr>
  <logic:iterate indexId="i" id="alias" name="aliases">
    <jamm:tr index="i" evenColor="#E6E6FA" oddColor="#FFDEAD">
      <td><bean:write name="alias" property="alias"/></td>
      <td>
        <logic:iterate id="destination" name="alias"
                       property="destinations">
          <bean:write name="destination"/><br>
        </logic:iterate>
      </td>
      <td align="center">D</td>
      <td align="center">A</td>
      <td align="center">Y/N</td>
    </jamm:tr>
  </logic:iterate>
</table>

<p>
  Do something for catch all.
</p>
