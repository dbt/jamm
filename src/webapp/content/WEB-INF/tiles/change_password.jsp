<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="mail" name="mail" value=""/>
<span class=header>CHANGE PASSWORD for <bean:write name="mail"/></span>
<html:errors/>
<html:form action="/private/change_password" focus="password">
  <html:hidden property="mail"/>
  <html:hidden property="done"/>
  <table border="0">
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
        <html:submit styleClass="mbutton">
          <bean:message key="change_password.button.submit"/>
        </html:submit>
        <html:submit property="clear" styleClass="button">
          Clear Password
        </html:submit>
        <html:cancel styleClass="button">
          <bean:message key="button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
