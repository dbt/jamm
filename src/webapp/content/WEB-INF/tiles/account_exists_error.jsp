<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  JAMM Account or Alias Error Page
</h1>

<html:messages header="account_exists.error.header"
               footer="account_exists.error.footer" id="error">
  <bean:write name="error"/>
</html:messages>
