<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">Admin Menu <bean:write name="domain"/></h1>

<html:errors/>

<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
      <html:form action="/private/domain_account">
        <html:hidden property="domain"/>
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
            <th> <bean:message key="domain_admin.header.account"/> </th>
            <th> <bean:message key="domain_admin.header.delete"/> </th>
            <th> <bean:message key="domain_admin.header.active"/> </th>
            <th> <bean:message key="domain_admin.header.admin"/> </th>
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
          <bean:message key="domain_admin.button.submit"/>
        </html:submit>
        <html:reset> <bean:message key="button.reset"/> </html:reset>
      </html:form>

      <br/>

      <html:form action="/private/domain_alias">
        <html:hidden property="domain"/>
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
            <th> <bean:message key="domain_admin.header.alias"/> </th>
            <th> <bean:message key="domain_admin.header.destinations"/> </th>
            <th> <bean:message key="domain_admin.header.delete"/> </th>
            <th> <bean:message key="domain_admin.header.active"/> </th>
            <th> <bean:message key="domain_admin.header.admin"/> </th>
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
          <bean:message key="domain_admin.button.submit"/>
        </html:submit>
        <html:reset> <bean:message key="button.reset"/> </html:reset>
      </html:form>

      <br/>
      
      <table width="100%">
        <tr>
          <td>
            <html:link forward="add_account" paramId="domain"
                       paramName="domain">
              <bean:message key="domain_admin.link.add_account"/>
            </html:link>
          </td>
          <td>
            <html:link forward="add_alias" paramId="domain"
                       paramName="domain">
              <bean:message key="domain_admin.link.add_alias"/>
            </html:link>
          </td>
          <td align="center">
            <html:form action="/private/update_catchall">
              <html:hidden property="domain"/>
              <table>
                <tr>
                  <th>
                    Catch-All
                  </th>
                  <th>
                    destination
                  </th>
                </tr>
                <tr>
                  <td>
                    On <html:radio property="status" value="on"/>
                    Off <html:radio property="status" value="off"/>
                  </td>
                  <td>
                    <html:text property="destination" />
                  </td>
                </tr>
              </table>
              <br />
              <html:submit>
                <bean:message key="domain_admin.button.submit_catch_all"/>
              </html:submit>
            </html:form>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>
