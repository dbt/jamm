<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<p>
  Header :
  <html:link forward="logout">
    <bean:message key="home.logout"/>
  </html:link> :
  <logic:present name="breadCrumbs">
    <logic:iterate id="breadCrumb" name="breadCrumbs"
                   type="jamm.webapp.BreadCrumb">
      <html:link page="<%= breadCrumb.getLink() %>">
        <%= breadCrumb.getText() %>
      </html:link> :
    </logic:iterate>
  </logic:present>
</p>
