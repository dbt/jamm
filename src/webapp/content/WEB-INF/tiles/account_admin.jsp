<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>ACCOUNT ADMINISTRATION for <bean:write name="mail"/></span>
<br />
<html:link forward="change_password" paramId="mail"
           name="passwordParameters">
  Change Password
</html:link>

<%--
<form>
  <table>
    <tr>
      <td align="right">Full Name</td>
      <td><input type="text" value="Joe User"/></td>
      <td><input type="submit" value="Change"/></td>
    </tr>
  </table>
</form>
--%>
