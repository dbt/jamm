<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
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
<html:errors/>
<html:form action="/private/update_alias">
  <html:hidden property="mail"/>
  <table width="100%" border="0">
    <tr>
      <th align="left">Destination</th>
      <td align="left" width="6">&nbsp;</td>
      <td align="right" width="79">
        <html:img page='/imgs/delete_alias.gif'
                  alt='Delete Alias'
                  width='79' height='23'/>
      </td>
    </tr>
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
</html:form>
