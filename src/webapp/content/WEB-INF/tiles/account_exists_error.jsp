<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h2 align="center">
  Account or Alias already exists.
</h2>

<p>
  <font color="red">An account or alias for <bean:write name="mail"/>
  already exists.</font>
</p>

<p>
  <logic:present name="aliasDomain">
    <html:link forward="add_alias" paramName="aliasDomain" paramId="domain">
      Please try again.
    </html:link>
  </logic:present>

  <logic:present name="accountDomain">
    <html:link forward="add_account" paramName="accountDomain"
               paramId="domain">
      Please try again.
    </html:link>
  </logic:present>
</p>
  
<logic:present name="exception.stacktrace" scope="request">
<!--
<bean:write name="exception.stacktrace" scope="request"/>
-->
</logic:present>
