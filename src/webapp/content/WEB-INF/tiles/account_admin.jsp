<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Account Admin for <bean:write name="mail"/>
</h1>

<form>
  <table>
    <tr>
      <td align="right">Full Name</td>
      <td><input type="text" value="Joe User"/></td>
      <td><input type="submit" value="Change"/></td>
    </tr>
  </table>
</form>

<html:link page="/private/change_password.jsp" paramId="mail"
           paramName="mail">
  <bean:message key="home.change_password"/>
</html:link>
