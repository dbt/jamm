<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<span class=header>ACCOUNT ADMINISTRATION for <bean:write name="mail"/></span>
<br />
<html:link forward="change_password" paramId="mail"
           name="passwordParameters">
  Change Password
</html:link>

<html:errors/>
<html:form action="/private/update_account">

  <p>
    Full Name: <html:text property="commonName" name="account" size="50"/>
  </p>
  <html:hidden property="mail"/>
  <p>
    <html:submit styleClass="mbutton">Submit</html:submit>
  </p>
</html:form>
