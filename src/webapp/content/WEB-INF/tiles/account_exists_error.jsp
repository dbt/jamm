<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h2 align="center">
  Account or Alias already exists.
</h2>

<html:messages header="account_exists.error.header"
               footer="account_exists.error.footer" id="error">
  <bean:write name="mail"/> <bean:write name="error"/>
</html:messages>

<%-- <html:link page="<%= goBack %>"><%= goBack %></html:link> --%>
<bean:write name="goBack"/>

<%
// TODO finish me!
%>
