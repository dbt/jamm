<% String ROOT = getServletContext().getServletContextName(); %>
<html>
  <head>
    <title>Change Password</title>
  </head>
  <body>
    <h1 align="center">Change your password</h1>

    <form action="<%= ROOT %>/change" method="post">
    <table>
      <tr>
        <td align="right">User:</td>
	<td><input type="text" name="user"></td>
      </tr>

      <tr>
        <td align="right">Old Password:</td>
	<td><input type="password" name="old_password"></td>
      </tr>

      <tr>
        <td align="right">New Password:</td>
	<td><input type="password" name="new_password_1"></td>
      </tr>

      <tr>
        <td align="right">Retype New Password:</td>
	<td><input type="password" name="new_password_2"></td>
      </tr>

      <tr>
        <td><br></td>
	<td><input type="submit" value="Change Password"></td>
    </table>
    </form>
  </body>
</html>
