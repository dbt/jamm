<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<logic:present name="user">
  <p>
    <bean:message key="home.welcome"/>
    <tt><bean:write name="user" property="username"/></tt>.
    <ul>
      <li>
        <html:link forward="user_home">
          <bean:message key="home.link.user_home"/>
        </html:link>
      </li>
      <li>
        <html:link forward="logout">
          <bean:message key="home.logout"/>
        </html:link>
      </li>
    </ul>
  </p>
</logic:present>

<logic:notPresent name="user">
  <p>
    <html:link forward="user_home"><bean:message key="home.login"/></html:link>
  </p>
</logic:notPresent>
