<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">Admin Menu <bean:write name="domainName"/></h1>

<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
      <html:form action="/private/domain_account">
        <logic:iterate id="account" name="domainAccountForm"
                       property="originalActiveItems" type="String">
          <html:hidden property="originalActiveItems" value="<%=account%>"/>
        </logic:iterate>

        <logic:iterate id="account" name="domainAccountForm"
                       property="originalAdminItems" type="String">
          <html:hidden property="originalAdminItems" value="<%=account%>"/>
        </logic:iterate>

        <table width="100%" border="1" cellspacing="0" cellpadding="3"
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
              <td>
                <html:link page="/private/account_admin.do" paramId="mail"
                           paramName="account" paramProperty="name">
                  <bean:write name="account" property="name"/>
                </html:link>
              </td>
              
              <td align="center">
                <html:multibox property="itemsToDelete">
                  <bean:write name="account" property="name"/>
                </html:multibox>
              </td>
              <td align="center">
                <html:multibox property="activeItems">
                  <bean:write name="account" property="name"/>
                </html:multibox>
              </td>
              <td align="center">
                <html:multibox property="adminItems">
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

      <html:form action="/private/domain_alias">
        <logic:iterate id="alias" name="domainAliasForm"
                       property="originalActiveItems" type="String">
          <html:hidden property="originalActiveItems" value="<%=alias%>"/>
        </logic:iterate>

        <logic:iterate id="alias" name="domainAliasForm"
                       property="originalAdminItems" type="String">
          <html:hidden property="originalAdminItems" value="<%=alias%>"/>
        </logic:iterate>

        <table width="100%" border="1" cellspacing="0" cellpadding="3"
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
              <td>
                <html:link page="/private/account_admin.do" paramId="mail"
                           paramName="alias" paramProperty="name">
                  <bean:write name="alias" property="name"/>
                </html:link>
              </td>
              <td>
                <logic:iterate id="destination" name="alias"
                               property="destinations">
                  <bean:write name="destination"/><br>
                </logic:iterate>
              </td>
              <td align="center">
                <html:multibox property="itemsToDelete">
                  <bean:write name="alias" property="name"/>
                </html:multibox>
              </td>
              <td align="center">
                <html:multibox property="activeItems">
                  <bean:write name="alias" property="name"/>
                </html:multibox>
              </td>
              <td align="center">
                <html:multibox property="adminItems">
                  <bean:write name="alias" property="name"/>
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
      
      <table width="100%">
        <tr>
          <td>
            <html:link forward="add_account" paramId="domain"
                       paramName="domainName">Add Account</html:link>
          </td>
          <td>
            <html:link forward="add_alias" paramId="domain"
                       paramName="domainName">Add Alias</html:link>
          </td>
          <td> Do something for catch all.</td>
        </tr>

    </td>
  </tr>
</table>
