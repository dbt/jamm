<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html
          PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
          "DTD/xhtml11-strict.dtd">
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<html:html>
  <head>
    <meta http-equiv="Content-Type" content="test/html;charset=UTF-8" />
    <title><tiles:getAsString name="title"/></title>
    <link rel="STYLESHEET" type="text/css" href="<%= CSS %>">
    <script>
    <!--
     ROOT = "<%= ROOT %>";
     //-->
    </script>
    <script type="text/javascript" LANGUAGE="Javascript1.2"
            SRC="<%= JS %>"></script>
    <html:base/>
  </head>

  <body bgcolor="#FFFFFF">
    <tiles:insert attribute="header"/>
    <tiles:insert attribute="body"/>
    <tiles:insert attribute="footer"/>
  </body>
</html:html>
