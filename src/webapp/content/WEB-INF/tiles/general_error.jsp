<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Error Page
</h1>

<html:messages header="general.error.header"
               footer="general.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
<logic:present name="exception.stacktrace" scope="request">
<!--
<bean:write name="exception.stacktrace" scope="request"/>
-->
</logic:present>
