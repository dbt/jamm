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
                      <span class=header>DOMAIN ADMINISTRATION</span>
                      <br>

                        <span class=title>MANAGE ACCOUNTS</span>
            <html:link forward="add_account" paramId="domain"
                       paramName="domain">
              <bean:message key="domain_admin.link.add_account"/>
            </html:link>

              </td>
              <td vAlign=bottom align=right>

                <!--<img src="imgs/site_header.gif" width="270" height="19" alt="" border="0">-->
                <table cellpadding=0 cellspacing=0><tr>
                  <td align=middle width=79>
                    <html:img page='/imgs/delete_account.gif'
                         alt='Delete Account' width=79 height=23/>
                  </td>
                  <td align=middle width=81>
                    <html:img page='/imgs/account_is_active.gif'
                              alt='Account is active' width=79 height=23 hspace=2/>
                  </td>
                  <td align=middle width=79>
                    <html:img page='/imgs/postmaster.gif' alt='Postmaster'
                         width=79 height=23/>
                  </td>
<!--
                    <td width=79 align="center">DEL</td>
                    <td width=81 align="center">ACT</td>
                    <td width=79 align="center">ADM</td>
-->
                  </tr></table>
              </td>
            </tr>
<tr><td bgcolor=#FFFFFF colspan=2>

<!-- <h1 align="center">Admin Menu <bean:write name="domain"/></h1> -->

<html:errors/>

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

<table width="100%" border="0" cellspacing="1" cellpadding="0">

<!--
<table width="80%" border="1" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td> -->

<!--
        <table width="100%" border="1" cellspacing="0" cellpadding="3"
               align="center">
          <tr>
            <th> <bean:message key="domain_admin.header.account"/> </th>
            <th> <bean:message key="domain_admin.header.delete"/> </th>
            <th> <bean:message key="domain_admin.header.active"/> </th>
            <th> <bean:message key="domain_admin.header.admin"/> </th>
          </tr> -->

          <logic:iterate indexId="i" id="account" name="accounts"
                         type="jamm.backend.AccountInfo">
<!--            <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
            -->
        <tr>
         <td colspan=5>
	<table cellpadding=0 cellspacing=0 border=0 width=100%>
	<tr class=datarow  onmouseover="this.style.backgroundColor='#eff7ff';" onmouseout="this.style.backgroundColor='#E4ECF6';" >
         
        <td onmouseover="OverRow(this)" onmouseout="OutRow(this)">
                <html:link page="/private/account_admin.do" paramId="mail"
                           paramName="account" paramProperty="name">
                  <bean:write name="account" property="name"/>
                </html:link>
              </td>
              
		<td align=left width=13><html:img page='/imgs/arrow.gif' width=13 height=25 border=0 vspace=0 hspace=0 align=right/>&nbsp;</td>
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
              <td align="center" width="79" class="multibox">
                <html:multibox property="adminItems">
                  <bean:write name="account" property="name"/>
                </html:multibox>
              </td>
<!--            </jamm:tr> -->
        </tr></table></td>
  </tr>
          </logic:iterate>
<!--        </table> -->
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4"><tr><td>
<tr>
<td align=right>
        <html:submit styleClass="button">
          <bean:message key="domain_admin.button.submit"/>
        </html:submit>
        <html:reset styleClass="button">
 <bean:message key="button.reset"/> </html:reset>
</tr>
</table>
      </html:form>

              </td>
          </tr>
          <tr>
            <td>

                        <span class=title>MANAGE ALIASES</span>
            <html:link forward="add_alias" paramId="domain"
                       paramName="domain">
              <bean:message key="domain_admin.link.add_alias"/>
            </html:link>
            </td>
            <td valign="bottom" align="right">
                <table cellpadding=0 cellspacing=0><tr>
                  <td align=middle width=79>
                    <html:img page='/imgs/delete_alias.gif'
                         alt='Delete Account' width=79 height=23/>
                  </td>
                  <td align=middle width=81>
                    <html:img page='/imgs/account_is_active.gif'
                              alt='Account is active' width=79 height=23 hspace=2/>
                  </td>
                  <td align=middle width=79>
                    <html:img page='/imgs/postmaster.gif' alt='Postmaster'
                         width=79 height=23/>
                  </td>
                  </tr></table>
            </td>
            </tr>
          <tr>
            <td colspan=2>

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

<table width="100%" border="0" cellspacing="1" cellpadding="0">
<!--
        <table width="100%" border="1" cellspacing="0" cellpadding="3"
               align="center">
          <tr>
            <th> <bean:message key="domain_admin.header.alias"/> </th>
            <th> <bean:message key="domain_admin.header.destinations"/> </th>
            <th> <bean:message key="domain_admin.header.delete"/> </th>
            <th> <bean:message key="domain_admin.header.active"/> </th>
            <th> <bean:message key="domain_admin.header.admin"/> </th>
          </tr> -->
          <logic:iterate indexId="i" id="alias" name="aliases">
<!--            <jamm:tr index="i" evenColor="#E6E6FA"
oddColor="#FFDEAD"> -->
        <tr>
         <td colspan=5>
	<table cellpadding=0 cellspacing=0 border=0 width=100%>
	<tr class=datarow  onmouseover="this.style.backgroundColor='#eff7ff';" onmouseout="this.style.backgroundColor='#E4ECF6';" >

              <td>
                <html:link page="/private/account_admin.do" paramId="mail"
                           paramName="alias" paramProperty="name">
                  <bean:write name="alias" property="name"/>
                </html:link>
            <div class=destinations>Destinations:
<%
   // FIX ME: Put this in a custom tag

   jamm.backend.AliasInfo dd_alias = (jamm.backend.AliasInfo) alias;
   java.util.List dd_destinations = dd_alias.getDestinations();
   for (int dd_j = 0; dd_j < dd_destinations.size(); dd_j++) {
        if (dd_j == 3)
        {
          out.println(", ...");
        }
        else if (dd_j > 3)
        {
        }
        else if (dd_j == 0)
        {
          out.print(dd_destinations.get(dd_j));
        }
        else
        {
          out.print(", " + dd_destinations.get(dd_j));
        }
  }
%>
                                </div>
              </td>
<!--
              <td>
                <logic:iterate id="destination" name="alias"
                               property="destinations">
                  <bean:write name="destination"/><br>
                </logic:iterate>
              </td> -->
		<td align=left width=13><html:img page='/imgs/arrow_alias.gif' width=13 height=32 border=0 vspace=0 hspace=0 align=right/>&nbsp;</td>
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
              <td align="center" width="79" class="multibox">
                <html:multibox property="adminItems">
                  <bean:write name="alias" property="name"/>
                </html:multibox>
              </td>
        </tr></table></td>
  </tr>
<!--            </jamm:tr> -->
          </logic:iterate>
        </table>
<table width="100%" border="0" cellspacing="0" cellpadding="4"><tr><td>
<tr>
<td align=right>
        <html:submit styleClass="button">
          <bean:message key="domain_admin.button.submit"/>
        </html:submit>
        <html:reset styleClass="button">
 <bean:message key="button.reset"/> </html:reset>
</tr>
</table>
      </html:form>

      <br/>
      
            <html:link forward="change_password" paramId="mail"
                       name="postmasterPasswordParameters">
              <bean:message key="domain_admin.link.change_postmaster_pw"/>
            </html:link>
       <br />

     Catch-All:
     <logic:equal name="catchAllForm"
                  property="catchAllOn" value="true"><b>Active</b> <br />
      Destination: <b><bean:write name="catchAllForm"
                                  property="destination" /></b>
     </logic:equal>
     <logic:notEqual name="catchAllForm"
                  property="catchAllOn" value="true">Inactive</logic:notEqual>

<a href="">Edit Catch-All</a>
<br />
<br />
            <html:form action="/private/update_catchall">
              <html:hidden property="domain"/>
              <table border="1" cellspacing="0" cellpadding="0">
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
                    <html:radio property="status" value="on"/> On
                    <html:radio property="status" value="off"/> Off
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
      </td>
    </tr>
  </table>
                
