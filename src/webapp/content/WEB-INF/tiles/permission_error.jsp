<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Permission Error Page
</h1>

<html:messages header="permission.error.header"
               footer="permission.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
