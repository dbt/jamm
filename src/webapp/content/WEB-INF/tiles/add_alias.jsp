<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Add Alias
</h1>

<html:errors/>
<html:form action="/private/add_alias" focus="name">
  <html:hidden property="domain"/>
  <table border="0">
    <tr>
      <th align="right">
        Account Name
      </th>
      <td align="left">
        <html:text property="name" size="30"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        Destinations
      </th>
      <td align="left">
        <html:textarea rows="6" cols="35" property="destinations"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        Password
      </th>
      <td align="left">
        <html:password property="password" size="30"/>
      </td>
    </tr>
    <tr>
      <th align="right">
        Retype Password
      </th>
      <td align="left">
        <html:password property="retypedPassword" size="30"/>
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
