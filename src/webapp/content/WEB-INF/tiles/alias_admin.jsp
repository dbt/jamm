<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>ALIAS ADMINISTRATION for <bean:write name="mail"/></span>
<br />
<html:link forward="change_password" paramId="mail"
           name="passwordParameters">
  Change Password
</html:link>

<html:errors/>
<html:form action="/private/update_alias">
  <p>
    Full Name: <html:text property="commonName" name="alias" size="50"/>
  </p>
  <html:hidden property="mail"/>
  <table width="100%" border="0">
    <tr>
      <th align="left">Destination</th>
      <td align="left" width="6">&nbsp;</td>
      <td align="right" width="79">
        <html:img page="/imgs/delete_alias.gif"
                  alt="Delete Alias"
                  width="79" height="23"/>
      </td>
    </tr>
    <logic:iterate indexId="count" id="destination" name="alias"
                   property="mailDestinations"
                   type="String">
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
    <html:submit styleClass="mbutton">Update Destinations</html:submit>
  </p>
</html:form>
