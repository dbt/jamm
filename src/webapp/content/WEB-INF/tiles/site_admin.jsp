<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<script>
<!--
function Toggle(e)
{
    Highlight(e);
}

function Highlight(e)
{
  var r = null;
  if (e.parentNode && e.parentNode.parentNode) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement) {
    r = e.parentElement.parentElement;
  }

  //alert(e.parentNode.parentNode.className);
  if (r) {
    if (r.className == "datarow" || r.className == "datarowon") {
      r.className = "datarowhigh";
    }
  }
}
function OverRow(e)
{
  var r = null;
  if (e.parentNode && e.parentNode.parentNode.className) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement.className) {
    r = e.parentElement.parentElement;
  }
  else if (e.parentNode) {
    r = e.parentNode;
  }
  else if (e.parentElement) {
    r = e.parentElement;
  }
  if (r) {
    if (r.className == "datarow") {
      r.className = "datarowon";
    }
    if (r.className == "datarowhigh") {
      r.className = "datarowhighon";
    }
  }
}
function OutRow(e)
{
  var r = null;
  if (e.parentNode && e.parentNode.parentNode.className) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement.className) {
    r = e.parentElement.parentElement;
  }
  else if (e.parentNode) {
    r = e.parentNode;
  }
  else if (e.parentElement) {
    r = e.parentElement;
  }
  if (r) {
    if (r.className == "datarowon") {
      r.className = "datarow";
    }
    if (r.className == "datarowhighon") {
      r.className = "datarowhigh";
    }
  }
}
//-->
</script>

  <table width=80% border=0>
    <tr>
      <td vAlign=top width=20>&nbsp;</td>
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
                <table cellpadding=0 cellspacing=0><tr>
                    <td width=79><html:img page="/imgs/edit_accounts.gif" width="79" height="23" alt="" border="0"/></td>
                    <td width=81><html:img page="/imgs/edit_catch-all.gif" width="79" height="23" alt="" border="0" hspace=1/></td>
                    <td width=79><html:img page="/imgs/appoint_postmasters.gif" width="79" height="23" alt="" border="0"/></td>
                  </tr></table>
              </td>
            </tr>
<tr><td bgcolor=#FFFFFF colspan=2>


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
<!--
  <table width="65%" border="1" cellspacing="0" cellpadding="3" align="center">
    <tr>
      <th> <bean:message key="site_admin.header.domains" /> </th>
      <th> <bean:message key="site_admin.header.edit_accounts" /> </th>
      <th> <bean:message key="site_admin.header.appoint_postmasters" /> </th>
      <th> <bean:message key="site_admin.header.active" /> </th>
    </tr>
-->

    <logic:iterate indexId="i" id="domain" name="domains"
                   type="jamm.backend.DomainInfo">
<!--       <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED"> -->
        <tr>
         <td colspan=5>
	<table cellpadding=0 cellspacing=0 border=0 width=100%>
	<tr class=datarow  onmouseover="this.style.backgroundColor='#eff7ff';" onmouseout="this.style.backgroundColor='#E4ECF6';" >
         
        <td onmouseover="OverRow(this)" onmouseout="OutRow(this)">
          <html:link page="/private/domain_admin.do" paramId="domain"
                     paramName="domain" paramProperty="name">
            <bean:write name="domain" property="name"/>
          </html:link>
        </td>
		<td align=left width=13><html:img page='/imgs/arrow.gif' width=13 height=25 border=0 vspace=0 hspace=0 align=right/>&nbsp;</td>
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
        </tr></table></td>
  </tr>
<!--      </jamm:tr> -->
    </logic:iterate>
<!--  </table> -->
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
