<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Alias Admin for <bean:write name="mail"/>
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

<html:errors/>

<html:form action="/private/update_alias">
  <html:hidden property="mail"/>
  <table width="85%" border="1" cellspacing="0">
    <tr>
      <th>Destination</th>
      <th>Delete</th>
    </tr>

    <logic:iterate indexId="i" id="destination" name="alias"
                   property="destinations">
      <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
        <td><bean:write name="destination"/></td>
        <td align="center">
          <html:multibox property="deleted">
            <bean:write name="destination"/>
          </html:multibox>
        </td>
      </jamm:tr>
    </logic:iterate>
  </table>
  <p>
    List email addresses to add as destinations:
  </p>
  <p>
    <html:textarea rows="6" cols="35" property="added"/>
  </p>
  <p>
    <input type="submit" value="Update destinations"/>
  </p>
</html:form>

<html:form action="/private/change_password">
  <html:hidden property="mail"/>
  <html:hidden property="done" value="account_admin"/>
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
        <html:submit>
          <bean:message key="change_password.button.submit"/>
        </html:submit>
      </td>
    </tr>
  </table>
</html:form>
