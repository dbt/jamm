<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
  Site Admin
</h1>

<ul>
  <logic:iterate id="domain" name="domains">
    <li>
      <html:link page="/private/domain_admin.do" paramId="domain"
                 paramName="domain">
        <bean:write name="domain"/>
      </html:link>
    </li>
  </logic:iterate>
</ul>
