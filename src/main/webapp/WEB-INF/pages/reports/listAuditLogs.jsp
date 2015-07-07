<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name="logs.index.title" /></title>
</head>
<body>
<br />
<h4><s:text name="logs.results.title" /></h4>
<br />
<s:actionerror />
<s:actionmessage />

<a href="/sdc/reports/searchAuditLogs.do"> <img src="/sdc/images/btn_search.gif" alt="Back To Search" /> </a> <br />
<br />

<display:table id="displayTagTable" name="sessionScope.logsList" decorator="gov.utah.dts.util.DisplayDecorator" pagesize="25" sort="list" class="simple" requestURI="listLogs.do">
	<display:column property="logDate" title="Log Date" sortable="false" headerClass="tdCenter" defaultorder="descending" />
	<display:column property="userId" title="User Email" sortable="true" headerClass="tdCenter" />
	<display:column property="logId" title="ID" sortable="true" headerClass="tdCenter" class="tdCenter" />
	<display:column property="logEntry" title="Action" sortable="true" headerClass="tdCenter" />
	<display:column property="userComment" title="Name & Value Pairs" sortable="false" headerClass="tdCenter" />
</display:table>

</body>
</html>
