<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>DOMAIN ADMINISTRATION for <bean:write name="domain"/></span>
<br />
<html:errors/>

<html:link forward="change_password" paramId="mail"
           name="postmasterPasswordParameters">
  <bean:message key="domain_admin.link.change_postmaster_pw"/>
</html:link>
<br />

Catch-All:
<logic:notEmpty name="catchAllAlias">
  <b><bean:message key="catch_all.active"/></b>
  Destination: <b><bean:write name="catchAllAlias"/></b>
</logic:notEmpty>
<logic:empty name="catchAllAlias">
  <b><bean:message key="catch_all.inactive"/></b>
</logic:empty>
<html:link forward="catch_all_admin" paramId="domain" paramName="domain">
  Edit Catch-All
</html:link>
<br />

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
  <table width="100%" border="0">
    <tr>
      <td>
        <span class=title>MANAGE ACCOUNTS</span>
        <logic:equal name="canEditAccounts" value="true">
          <html:link forward="add_account" paramId="domain"
                     paramName="domain">
            <bean:message key="domain_admin.link.add_account"/>
          </html:link>
        </logic:equal>
      </td>
      <td width="6">&nbsp;</td>

      <logic:equal name="canEditAccounts" value="true">
        <td align=middle width=79>
          <html:img page='/imgs/delete_account.gif'
                    alt='Delete Account' width='79' height='23'/>
        </td>
        <td align=middle width=81>
          <html:img page='/imgs/account_is_active.gif'
                    alt='Account is active' width='79'
                    height='23' hspace='2'/>
        </td>
        <logic:equal name="canEditPostmasters" value="true">
          <td align=middle width=79>
            <html:img page='/imgs/postmaster.gif' alt='Postmaster'
                      width='79' height='23'/>
          </td>
        </logic:equal>
      </logic:equal>
    </tr>
    <logic:iterate indexId="i" id="account" name="accounts"
                   type="jamm.backend.AccountInfo">
      <tr class="datarow"
          onmouseover='OverRow(this, "<%=account.getName()%>")'
          onmouseout='OutRow(this, "<%=account.getName()%>")'>
        
        <td>
          &nbsp;<html:link page="/private/account_admin.do"
                           paramId="mail"
                           paramName="account" paramProperty="name">
            <bean:write name="account" property="name"/><br>
          </html:link>
        </td>
        <td align="left" width="6" bgcolor="#FFFFFF">
          <html:img page='/imgs/sm_arrow.gif' width="6" height="9"
                    border="0"
                    imageName='<%=account.getName()%>'/>
        </td>
        <logic:equal name="canEditAccounts" value="true">
          <td align="center" width="79" class="multibox">
            <html:multibox property="itemsToDelete" onclick="Toggle(this)">
              <bean:write name="account" property="name"/>
            </html:multibox>
          </td>
          <td align="center" width="79" class="multibox">  
            <html:multibox property="activeItems">
              <bean:write name="account" property="name"/>
            </html:multibox>
          </td>
          <logic:equal name="canEditPostmasters" value="true">
            <td align="center" width="79" class="multibox">
              <html:multibox property="adminItems">
                <bean:write name="account" property="name"/>
              </html:multibox>
            </td>
          </logic:equal>
        </logic:equal>
      </tr>
    </logic:iterate>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td>
        <tr>
          <td align=right>
            <logic:equal name="canEditAccounts" value="true">
              <html:submit styleClass="mbutton">
                <bean:message key="domain_admin.button.submit"/>
              </html:submit>
              <html:reset styleClass="button">
                <bean:message key="button.reset"/> </html:reset>
            </logic:equal>
          </td>
        </tr>
      </td>
    </tr>
  </table>
</html:form>

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

  <table width="100%" border="0">
    <tr>
      <td>
        <span class=title>MANAGE ALIASES</span>
        <html:link forward="add_alias" paramId="domain"
                   paramName="domain">
          <bean:message key="domain_admin.link.add_alias"/>
        </html:link>
      </td>
      <td width="6">&nbsp;</td>
      <td align="middle" width="79">
        <html:img page='/imgs/delete_alias.gif'
                  alt='Delete Account' width='79' height='23'/>
      </td>
      <td align="middle" width="81">
        <html:img page='/imgs/alias_is_active.gif'
                  alt='Account is active' width='79'
                  height='23' hspace='2'/>
      </td>
      <logic:equal name="canEditPostmasters" value="true">
        <td align=middle width=79>
          <html:img page='/imgs/postmaster.gif' alt='Postmaster'
                    width='79' height='23'/>
        </td>
      </logic:equal>
    </tr>
    <logic:iterate indexId="i" id="alias" name="aliases"
                   type="jamm.backend.AliasInfo">
      <tr class="datarow"
          onmouseover='OverRow(this, "<%=alias.getName()%>")'
          onmouseout='OutRow(this, "<%=alias.getName()%>")'>
        <td>
          &nbsp;<html:link page="/private/account_admin.do" paramId="mail"
                           paramName="alias" paramProperty="name">
            <bean:write name="alias" property="name"/>
          </html:link>
          <div class=destinations>&nbsp;Destinations:
            <jamm:join limit="3" name="alias" property="mailDestinations"/>
          </div>
        </td>
        <td align="left" width="6" bgcolor="#FFFFFF">
          <html:img page='/imgs/sm_arrow.gif' width="6" height="9"
                    border="0"
                    imageName='<%=alias.getName()%>'/>
        </td>
        <td align="center" width="79" class="multibox">
          <html:multibox property="itemsToDelete">
            <bean:write name="alias" property="name"/>
          </html:multibox>
        </td>
        <td align="center" width="79" class="multibox">
          <html:multibox property="activeItems">
            <bean:write name="alias" property="name"/>
          </html:multibox>
        </td>
        <logic:equal name="canEditPostmasters" value="true">
          <td align="center" width="79" class="multibox">
            <html:multibox property="adminItems">
              <bean:write name="alias" property="name"/>
            </html:multibox>
          </td>
        </logic:equal>
      </tr>
    </logic:iterate>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="4"><tr><td>
        <tr>
          <td align=right>
            <html:submit styleClass="mbutton">
              <bean:message key="domain_admin.button.submit"/>
            </html:submit>
            <html:reset styleClass="button">
              <bean:message key="button.reset"/> </html:reset>
        </tr>
  </table>
</html:form>
