<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<logic:present name="user">
  <p>
    <bean:message key="home.welcome"/>
    <tt><bean:write name="user" property="username"/></tt>.<br>
    <html:link forward="logout"><bean:message key="home.logout"/></html:link>
  </p>
</logic:present>

<logic:notPresent name="user">
  <p>
    <html:link forward="login"><bean:message key="home.login"/></html:link>
  </p>
</logic:notPresent>
