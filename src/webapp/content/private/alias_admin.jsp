<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<html:html>
  <head>
    <title>Jamm - Alias Admin</title>
  </head>

  <body bgcolor="#FFFFFF">
    <h1 align="center">Alias Admin Menu yahoo@serious.com</h1>

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
        <tr>
          <td>user@example.domain</td>
          <td align="center">D</td>
        </tr>
        <tr>
          <td>user2@example.com</td>
          <td align="center">D</td>
        </tr>
      </table>
      <p>
        <input type="submit" value="Delete checked"/>
      </p>
    </form>

    <form>
      <p>
        List email addresses to add as destinations:
      </p>
      <p>
        <textarea rows="6" cols="35"/>
      </p>
      <p>
        <input type="submit" value="Add Addresses"/>
      </p>
    </form>

    <a href="password">Change Password</a>

  </body>
</html:html>
