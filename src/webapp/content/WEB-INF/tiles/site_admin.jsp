<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Site Admin
</h1>

<html:form action="/private/site_config">
  
  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditAliases" type="String">
    <html:hidden property="originalAllowEditAliases" value="<%=domain%>"/>
  </logic:iterate>

  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditAccounts" type="String">
    <html:hidden property="originalAllowEditAccounts" value="<%=domain%>"/>
  </logic:iterate>

  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditPostmasters" type="String">
    <html:hidden property="originalAllowEditPostmasters" value="<%=domain%>"/>
  </logic:iterate>
  
  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditCatchalls" type="String">
    <html:hidden property="originalAllowEditCatchalls" value="<%=domain%>"/>
  </logic:iterate>
  
  <table width="80%" border="1" cellspacing="0" cellpadding="3" align="center">
    <tr>
      <th> <bean:message key="site_admin.header.domains" /> </th>
      <th> <bean:message key="site_admin.header.edit_aliases" /> </th>
      <th> <bean:message key="site_admin.header.edit_accounts" /> </th>
      <th> <bean:message key="site_admin.header.appoint_postmasters" /> </th>
      <th> <bean:message key="site_admin_header.edit_catchall" /> </th>
    </tr>

    <logic:iterate indexId="i" id="domain" name="siteConfigForm"
                   property="domains" type="String">
      <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
        <td>
          <html:link page="/private/domain_admin.do" paramId="domain"
                     paramName="domain">
            <bean:write name="domain"/>
          </html:link>
        </td>
        <td align="center">
          X
        </td>
        <td align="center">
          X
        </td>
        <td align="center">
          X
        </td>
        <td align="center">
          X
        </td>
      </jamm:tr>
    </logic:iterate>
  </table>
  <center>
    <html:submit>
      CLICK!
    </html:submit>
  </center>
</html:form>
<br />
<html:link forward="add_domain">Add Domain</html:link>
