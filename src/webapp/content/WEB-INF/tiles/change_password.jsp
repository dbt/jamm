<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="done" name="done" value="/"/>
<html:errors/>
<html:form action="/change_password" focus="password1">
  <table border="0">
    <tr>
      <th align="right">
        <bean:message key="change_password.prompt.password1"/>
      </th>
      <td align="left">
        <html:password property="password1" size="30"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        <bean:message key="change_password.prompt.password2"/>
      </th>
      <td align="left">
        <html:password property="password2" size="30"/>
      </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit>
          <bean:message key="change_password.button.submit"/>
        </html:submit>
      </td>
    </tr>
  </table>
</html:form>
