<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<table width="90%" border="0">
  <tr>
    <td vAlign=top width=20>&nbsp;</td>
    <td vAlign=top>
      <table width="100%" border="0" cellspacing="0" cellpadding="3">
        <tbody>
          <tr>
            <td vAlign=bottom>
              <html:img page="/imgs/jamm_logo.gif" width="230"
                        height="48" alt="" border="0"/>
              <br />
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

            </td>
          </tr>
        </tbody>
      </table>
    </td>
  </tr>
</table>
