<!DOCTYPE html
          PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
          "DTD/xhtml11-strict.dtd">
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<html:html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <html:base/>
  </head>

  <body bgcolor="#FFFFFF">
    <tiles:insert attribute="header"/>
    <tiles:insert attribute="body"/>
    <tiles:insert attribute="footer"/>
  </body>
</html:html>
