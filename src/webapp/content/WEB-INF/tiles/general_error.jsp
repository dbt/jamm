<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Error Page
</h1>

<html:messages header="general.error.header"
               footer="general.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
