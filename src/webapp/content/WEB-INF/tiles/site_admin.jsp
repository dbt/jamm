<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>SITE ADMINISTRATION</span>
<html:errors/>
<html:form action="/private/site_config">
  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditAccounts" type="String">
    <html:hidden property="originalAllowEditAccounts" value="<%=domain%>"/>
  </logic:iterate>

  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalAllowEditPostmasters" type="String">
    <html:hidden property="originalAllowEditPostmasters" value="<%=domain%>"/>
  </logic:iterate>

  <logic:iterate id="domain" name="siteConfigForm"
                 property="originalActive" type="String">
    <html:hidden property="originalActive" value="<%=domain%>"/>
  </logic:iterate>

  <table width="100%" border="0">
    <tr>
      <td>
        <span class=title>MANAGE DOMAINS</span>
        <html:link forward="add_domain">
          <bean:message key="site_admin.link.add_domain"/>
        </html:link>
      </td>
      <td width="6">&nbsp;</td>
      <td width="79">
        <html:img page="/imgs/edit_accounts.gif"
                  width="79" height="23" alt="" border="0"/>
      </td>
      <td width="81">
        <html:img page="/imgs/appoint_postmasters.gif"
                  width="79" height="23" alt="" border="0"
                  hspace="1"/>
      </td>
      <td width="79">
        <html:img page="/imgs/domain_is_active.gif"
                  width="79" height="23" alt="" border="0"/>
      </td>
    </tr>
    <logic:iterate indexId="i" id="domain" name="domains"
                   type="jamm.backend.DomainInfo">
      <tr class="datarow"
          onmouseover='OverRow(this, "<%=domain.getName()%>")'
          onmouseout='OutRow(this, "<%=domain.getName()%>")'>
        <td>
          <html:link page="/private/domain_admin.do" paramId="domain"
                     paramName="domain" paramProperty="name">
            <bean:write name="domain" property="name"/>
          </html:link>
        </td>
        <td align="left" width="6" bgcolor="#FFFFFF">
          <html:img page='/imgs/sm_arrow.gif' width="6" height="9"
                    border="0" align="middle"
                    imageName='<%=domain.getName()%>'/>
        </td>
        <td align="center" width=79 class="multibox">
          <html:multibox property="allowEditAccounts" onclick="Toggle(this)">
            <bean:write name="domain" property="name"/>
          </html:multibox>
        </td>
        <td align="center" width="81" class="multibox">
          <html:multibox property="allowEditPostmasters">
            <bean:write name="domain" property="name"/>
          </html:multibox>
        </td>
        <td align="center" width=79 class="multibox">
          <html:multibox property="active">
            <bean:write name="domain" property="name"/>
          </html:multibox>
        </td>
      </tr>
    </logic:iterate>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="4"><tr><td>
        <tr>
          <td align=right>
            <html:submit styleClass="button">
              <bean:message key="site_admin.button.submit"/>
            </html:submit>
          </td>
        </tr>
  </table>
</html:form>
