<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<html:html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <html:base/>
  </head>

  <body>
    <tiles:insert attribute="header"/>
    <tiles:insert attribute="body"/>
    <tiles:insert attribute="footer"/>
  </body>
</html:html>
