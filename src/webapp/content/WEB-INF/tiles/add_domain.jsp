<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Add Domain
</h1>

<html:errors/>
<html:form action="/private/add_domain" focus="domain">
  <table border="0">
    <tr>
      <td align="right">
        <bean:message key="add_domain.prompt.domain_name"/>
      </td>
      <td align="left">
        <html:text property="domain" size="30"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_domain.prompt.postmaster_password"/>
      </td>
      <td align="left">
        <html:password property="password" size="30"/>
        <bean:message key="add_domain.prompt.optional"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <bean:message key="add_domain.prompt.retype_postmaster_password"/>
      </td>
      <td align="left">
        <html:password property="retypedPassword" size="30"/>
        <bean:message key="add_domain.prompt.optional"/>
      </td>
    </tr>
    <tr>
      <td align="right"><b><bean:message key="add_domain.note"/></b></td>
      <td> <bean:message key="add_domain.note.content"/> </td>
    </tr>
    <tr>
      <td><br></td>
      <td>
        <html:submit>
          <bean:message key="add_domain.button.submit"/>
        </html:submit>
        <html:cancel>
          <bean:message key="button.cancel"/>
        </html:cancel>
      </td>
    </tr>
  </table>
</html:form>
