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

<form>
  <table width="85%" border="1" cellspacing="0">
    <tr>
      <th>Destination</th>
      <th>Delete</th>
    </tr>

    <logic:iterate indexId="i" id="destination" name="destinations">
      <jamm:tr index="i" evenColor="#FFFFFF" oddColor="#6495ED">
        <td><bean:write name="destination"/></td>
        <td align="center">
          D
        </td>
      </jamm:tr>
    </logic:iterate>
  </table>
  <p>
    List email addresses to add as destinations:
  </p>
  <p>
    <textarea rows="6" cols="35"></textarea>
  </p>
  <p>
    <input type="submit" value="Update destinations"/>
  </p>
</form>

<html:link page="/private/change_password.jsp" paramId="mail"
           paramName="mail">
  <bean:message key="home.change_password"/>
</html:link>
