<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>
  CATCH-ALL ADMIN for domain <bean:write name="domain"/>
</span>
<html:errors/>
<html:form action="/private/update_catch_all">
  <html:hidden property="domain"/>
  <table border="0">
    <tr>
      <td align="right" valign="top">
        <bean:message key="catch_all_admin.status"/>:
      </td>
      <td>
        <html:radio property="status" value="on"/>
        <bean:message key="catch_all.active"/>
        <br />
        <html:radio property="status" value="off"/>
        <bean:message key="catch_all.inactive"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="catch_all_admin.destination"/>:
      </td>
      <td>
        <html:text property="destination" />
      </td>
    </tr>
  </table>
  <br />
  <html:submit styleClass="mbutton">
    <bean:message key="catch_all_admin.button.submit"/>
  </html:submit>
  <html:cancel styleClass="button">
    <bean:message key="button.cancel"/>
  </html:cancel>
</html:form>
