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
function backToSearch()
{
	document.listForm.action = "search.do";
	document.listForm.submit();
}

function inactivate(news)
{
	document.listForm.action = "delete-"+news+".do";
	document.listForm.submit();
}

function activate(news)
{
	document.listForm.action = "undelete-"+news+".do";
	document.listForm.submit();
}

function edit(news)
{
	document.listForm.action = "edit-"+news+".do";
	document.listForm.editAction.value = "Y";
	document.listForm.submit();
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="news.results.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="listForm" action="search">
	        <a href="javascript:backToSearch();">
	        	<img src="/sdc/images/btn_search.gif" alt="Back To Search" />
	        </a>
	        <br/>
	        <br/>
			<table class="sortable">
				<thead>
					<tr>
						<th>News Item</th>
						<th>Intended For</th>
						<th class="sorttable_nosort" style="text-align:center;">Action</th>
						<th class="sorttable_nosort" style="text-align:center;">Edit News</th>
					</tr>
				</thead>
				<tbody>
<s:if test="availableList.size > 0">
	<s:iterator value="availableList">
					<tr>
						<td><s:property value="description"/></td>
						<td><s:property value="roleName"/></td>
		<s:if test="deleted == 0">
						<td align="center">
							<a onclick="return confirm('Are You Sure You Want to Inactivate This News Item?')" href="javascript:inactivate(<s:property value="newsPk" />);">INACTIVATE</a>
						</td>
						<td align="center">
							<a href="javascript:edit(<s:property value="newsPk" />);">EDIT</a>
						</td>
		</s:if>
		<s:else>
						<td align="center">
							<a onclick="return confirm('Are You Sure You Want to Activate This News Item?')" href="javascript:activate(<s:property value="newsPk" />);">ACTIVATE</a>
						</td>
						<td>&nbsp;</td>
		</s:else>
					</tr>
            </s:iterator>
</s:if>
<s:else>
</s:else>
				</tbody>
			</table>
	        <p><a href="javascript:edit('');">Create Additional News</a></p>
	        <s:hidden name="searchDescription"/>
	        <s:hidden name="searchRoleTypesFk"/>
	        <s:hidden name="activeFlag"/>
	        <s:hidden name="editAction"/>
		</s:form>
    </body>
</html>
