<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="done" name="done" value="/"/>
<html:errors/>
<html:form action="/change_password" focus="password">
  <html:hidden property="mail"/>
  <table border="0">
    <tr>
      <td align="right"> <bean:message key="change_password.user_info"/> </td>
      <td> <b><bean:write name="changePasswordForm" property="mail"/></b> </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="change_password.prompt.password"/>
      </td>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="change_password.prompt.retype_password"/>
      </td>
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
          <bean:message key="button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
