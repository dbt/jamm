<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="done" name="done" value="/"/>
<html:errors/>
<html:form action="/change_password" focus="password">
  <bean:message key="change_password.user_info"/>
  <b><bean:write name="changePasswordForm" property="mail"/></b>
  <html:hidden property="mail"/>
  <table border="0">
    <tr>
      <th align="right">
        <bean:message key="change_password.prompt.password"/>
      </th>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        <bean:message key="change_password.prompt.retype_password"/>
      </th>
      <td align="left">
        <html:password property="retypedPassword" size="30"/>
      </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit>
          <bean:message key="change_password.button.submit"/>
        </html:submit>
        <html:cancel>
          <bean:message key="change_password.button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
