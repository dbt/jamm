<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>MAIL ADMINISTRATION</span>
<br />

<bean:parameter id="done" name="done" value="/"/>
<html:errors/>
<html:form action="/login" focus="username">
  <html:hidden property="done" value="<%= done %>"/>
  <table border="0">
    <tr>
      <td align="right">
        <bean:message key="login.prompt.username"/>
      </td>
      <td align="left">
        <html:text property="username" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="login.prompt.password"/>
      </td>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit styleClass="mbutton">
          <bean:message key="login.button.submit"/>
        </html:submit>
        <html:cancel styleClass="button">
          <bean:message key="login.button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
