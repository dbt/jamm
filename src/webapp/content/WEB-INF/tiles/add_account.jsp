<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="domain" name="domain"/>
<span class=header>ADD ACCOUNT for domain <bean:write name="domain"/></span>
<br />
<html:errors/>
<html:form action="/private/add_account" focus="name">
  <html:hidden property="domain"/>
  <table border="0">
    <tr>
      <td align="right">
        <bean:message key="add_account.prompt.account_name"/>
      </td>
      <td align="left">
        <html:text property="name" size="30"/>@<bean:write name="domain"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_account.prompt.common_name"/>
      </td>
      <td align="left">
        <html:text property="commonName" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_account.prompt.password"/>
      </td>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_account.prompt.retype_password"/>
      </td>
      <td align="left">
        <html:password property="retypedPassword" size="30"/>
      </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit styleClass="mbutton">
          <bean:message key="add_account.button.submit"/>
        </html:submit>
        <html:cancel styleClass="button">
          <bean:message key="button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
