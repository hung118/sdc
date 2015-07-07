<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="news.index.title"/></title>
        <script type='text/javascript' src='/sdc/common/sorttable.js'></script>
		<script type="text/javascript">
function remove(item) {
	document.listForm.action = "delete-"+item+".do";
	document.listForm.submit();
}

function edit(item) {
	document.listForm.action = "editCompletionSlip-"+item+".do";
	if (item != '') {
		document.listForm.editAction.value = "Y";	
	} else {
		document.listForm.editAction.value = "N";
	}
	document.listForm.submit();
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="others.completionSlip.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="listForm" action="editCompletionSlip">
	        <br/>
			<table>
				<thead>
					<tr>
						<th>Completion Slip</th>
						<th>Total</th>
						<th>Description</th>
						<th style="text-align:center;">Action</th>
					</tr>
				</thead>
				<tbody>
<s:if test="availableList.size > 0">
	<s:iterator value="availableList">
					<tr>
						<td><s:property value="name"/></td>
						<td><s:property value="value"/></td>
						<td><s:property value="description"/></td>
						<td align="center">
							<a href="javascript:edit(<s:property value="namevaluePk" />);">Edit</a>
						</td>
					</tr>
            </s:iterator>
</s:if>
<s:else>
</s:else>
				</tbody>
			</table>
			<s:hidden name="editAction"/>
	        <p><a href="javascript:edit('');">Create Completion Slip</a></p>
		</s:form>
    </body>
</html>
