<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Account or Alias Error Page
</h1>

<html:messages header="domain_exists.error.header"
               footer="domain_exists.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
