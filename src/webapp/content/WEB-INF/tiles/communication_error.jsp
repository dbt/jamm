<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Communication Error Page
</h1>

<html:messages header="communication.error.header"
               footer="communication.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
