<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@ include file="/WEB-INF/common/all_definitions.jsp" %>  -->
<h1 align="center">
    JAMM Access Error Page
</h1>

<html:errors />

<logic:present name="exception.stacktrace" scope="request">
<!--
<bean:write name="exception.stacktrace" scope="request"/>
-->
</logic:present>
