<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<table width="90%" border="0">
  <tr>
    <td vAlign=top width="20">&nbsp;</td>
    <td vAlign=top>
      <table width="100%" border="0" cellspacing="0" cellpadding="3">
        <tbody>
          <tr>
            <td vAlign=bottom colspan="2">
              <html:img page="/imgs/jamm_logo.gif" width="230"
                        height="48" alt="" border="0"/>
                
              <br />
              <span class=header>ALIAS ADMINISTRATION</span>
              <br />
              <span class=title><bean:write name="mail"/></span>
              <html:link forward="change_password" paramId="mail"
                         name="passwordParameters">
                Change Password
              </html:link>

              <!--
              <form>
                <table>
                  <tr>
                    <td align="right">Full Name</td>
                    <td><input type="text" value="Joe User"/></td>
                    <td><input type="submit" value="Change"/></td>
                  </tr>
                </table>
              </form>
              -->
            </td>
          </tr>
          <tr>
            <td colspan="2"><html:errors/>&nbsp;</td>
          </tr>
          <tr>
            <th align="left">Destination</th>
            <td align="right" width=79>
              <html:img page='/imgs/delete_alias.gif'
                        alt='Delete Alias'
                        width='79' height='23'/>
            </td>
          </tr>
          <html:form action="/private/update_alias">
            <tr>
              <td bgcolor="#FFFFFF" colspan=2>
                <html:hidden property="mail"/>

                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <logic:iterate indexId="i" id="destination" name="alias"
                                 property="destinations"
                                 type="java.lang.String">
                    <tr class="datarow"
                        onmouseover='OverRow(this, "<%=destination%>")'
                        onmouseout='OutRow(this, "<%=destination%>")'>
                      <td>&nbsp;<bean:write name="destination"/></td>
                      <td align="left" width="6" bgcolor="#FFFFFF">
                        <html:img page='/imgs/sm_arrow.gif'
                                  width="6" height="9"
                                  border="0" align="middle"
                                  imageName='<%=destination%>'/>
                      </td>
                      <td align="center" width="79" class="multibox">
                        <html:multibox property="deleted">
                          <bean:write name="destination"/>
                        </html:multibox>
                      </td>
                    </tr>
                  </logic:iterate>
                </table>
                <p>
                  List email addresses to add as destinations:
                </p>
                <p>
                  <html:textarea rows="6" cols="35" property="added"/>
                </p>
                <p>
                  <html:submit styleClass="button">Update Destinations</html:submit>
                </p>
              </td>
            </tr>
          </html:form>
        </tbody>
      </table>
    </td>
  </tr>
</table>
