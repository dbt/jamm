<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">Admin Menu <bean:write name="domainName"/></h1>

<html:form action="/private/domain_account">
  <logic:iterate id="account" name="domainAccountForm"
                 property="originalActiveAccounts" type="String">
    <html:hidden property="originalActiveAccounts" value="<%=account%>"/>
  </logic:iterate>

  <logic:iterate id="account" name="domainAccountForm"
                 property="originalAdminAccounts" type="String">
    <html:hidden property="originalAdminAccounts" value="<%=account%>"/>
  </logic:iterate>

  <table width="80%" border="1" cellspacing="0" cellpadding="3"
         align="center">
    <tr>
      <th>Account</th>
      <th>Delete</th>
      <th>Active</th>
      <th>Admin</th>
    </tr>

    <logic:iterate indexId="i" id="account" name="accounts"
                   type="jamm.backend.AccountInfo">
      <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
        <td><bean:write name="account" property="name"/></td>
        <td align="center">
          <html:multibox property="accountsToDelete">
            <bean:write name="account" property="name"/>
          </html:multibox>
        </td>
        <td align="center">
          <html:multibox property="activeAccounts">
            <bean:write name="account" property="name"/>
          </html:multibox>
        </td>
        <td align="center">
          <html:multibox property="adminAccounts">
            <bean:write name="account" property="name"/>
          </html:multibox>
        </td>
      </jamm:tr>
    </logic:iterate>
  </table>
  <html:submit>
    Submit Changes
  </html:submit>
  <html:reset>Reset</html:reset>
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
      <td><bean:write name="alias" property="name"/></td>
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
