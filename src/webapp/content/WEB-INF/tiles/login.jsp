<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="done" name="done" value="/private/"/>
<html:errors/>
<html:form action="/login" focus="username">
  <html:hidden property="done" value="<%= done %>"/>
  <table border="0">
    <tr>
      <th align="right">
        <bean:message key="login.prompt.username"/>
      </th>
      <td align="left">
        <html:text property="username" size="30"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        <bean:message key="login.prompt.password"/>
      </th>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit>
          <bean:message key="login.button.submit"/>
        </html:submit>
      </td>
    </tr>
  </table>
</html:form>
