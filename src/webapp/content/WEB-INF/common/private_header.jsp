<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<table width="90%" border="0">
  <tr>
    <td vAlign=top width="20">&nbsp;</td>
    <td vAlign=top>
      <table width="100%" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td>
            <p>
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
            <html:img page="/imgs/jamm_logo.gif" width="230"
                      height="48" alt="" border="0"/> %VERSION%
            <br />
