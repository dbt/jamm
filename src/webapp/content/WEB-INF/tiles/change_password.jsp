<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<bean:parameter id="mail" name="mail" value=""/>
<table width="90%" border="0">
  <tr>
    <td vAlign=top width="20">&nbsp;</td>
    <td vAlign=top>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="3">
        <tbody>
          <tr>
            <td vAlign=bottom>
              <html:img page="/imgs/jamm_logo.gif" width="230"
                        height="48" alt="" border="0"/>

              <br />

<html:errors/>
<html:form action="/private/change_password" focus="password">
  <html:hidden property="mail"/>
  <html:hidden property="done"/>
  <table border="0">
    <tr>
      <td align="right"> <bean:message key="change_password.user_info"/> </td>
      <td> <b><bean:write name="changePasswordForm" property="mail"/></b> </td>
    </tr>
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
        <html:submit styleClass="button">
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


            </td>
          </tr>
        </tbody>
      </table>
    </td>
  </tr>
</table>
