<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
  <table width="90%" border="0">
    <tr>
      <td vAlign=top width="20">&nbsp;</td>
      <td vAlign=top>
  
        <table width="100%" border="0" cellspacing="0" cellpadding="3">
          <tbody>
            <tr>
              <td vAlign=bottom>
                <html:img page="/imgs/jamm_logo.gif" width="230" height="48" alt="" border="0"/>

                    <br>
                      <span class=header>SITE ADMINISTRATION</span>
                      <br>

                        <span class=title>MANAGE DOMAINS</span>
                  <html:link forward="add_domain">
                    <bean:message key="site_admin.link.add_domain"/>
                  </html:link>

              </td>
              <td vAlign=bottom align=right>

                <!--<img src="imgs/site_header.gif" width="270" height="19" alt="" border="0">-->
                <table cellpadding=0 cellspacing=0>
                <tr>
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
              </table>
              </td>
            </tr>
<tr><td bgcolor="#FFFFFF" colspan="2">


<!-- <h1 align="center">
  Site Admin
</h1> -->

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

<table width="100%" border="0" cellspacing="1" cellpadding="0">
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
        <td align="center" width=79 class="multibox">
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
<br />
<html:link forward="add_domain">Add Domain</html:link>

          
      </td>
    </tr>
        </table>
      </td>
    </tr>
  </table>
