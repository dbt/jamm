<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="domain" name="domain"/>
<span class=header>ADD ALIAS for domain <bean:write name="domain"/></span>
<html:errors/>
<html:form action="/private/add_alias" focus="name">
  <html:hidden property="domain"/>
  <table border="0">
    <tr>
      <td align="right">
        <bean:message key="add_alias.prompt.alias_name"/>
      </td>
      <td align="left">
        <html:text property="name" size="30"/>@<bean:write name="domain"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_alias.prompt.common_name"/>
      </td>
      <td align="left">
        <html:text property="commonName" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_alias.prompt.destinations"/>
      </td>
      <td align="left">
        <html:textarea rows="6" cols="35" property="destinations"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_alias.prompt.password"/>
      </td>
      <td align="left">
        <html:password property="password" size="30"/>
        <bean:message key="add_alias.prompt.optional"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_alias.prompt.retype_password"/>
      </td>
      <td align="left">
        <html:password property="retypedPassword" size="30"/>
        <bean:message key="add_alias.prompt.optional"/>
      </td>
    </tr>
    <tr>
      <td align="right" valign="top">
        <b><bean:message key="add_alias.note"/></b>
      </td>
      <td> <bean:message key="add_alias.note.content"/> </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit styleClass="mbutton">
          <bean:message key="add_alias.button.submit"/>
        </html:submit>
        <html:cancel styleClass="button">
          <bean:message key="button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
