<html>
  <head>
    <title>Change Password - Error</title>
  </head>
  <body>
    <p>
      An error occured while changing your password:
      <%= session.getAttribute("error") %>
    </p>
  </body>
</html>
