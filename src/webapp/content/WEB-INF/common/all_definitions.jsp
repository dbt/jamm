<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tld/jamm.tld" prefix="jamm" %>
<logic:present name="user" scope="session">
  <jsp:useBean id="user" scope="session" class="jamm.webapp.User"/>
</logic:present>
<% String ROOT = request.getContextPath(); %>
<% String CSS = ROOT + "/common.css"; %>
<% String JS = ROOT + "/common.js"; %>
