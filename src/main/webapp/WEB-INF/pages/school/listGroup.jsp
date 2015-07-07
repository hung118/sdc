<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="group.index.title"/></title>
		<script type="text/javascript">
function backToSearch()
{
	document.listForm.action = "searchGroup.do";
	document.listForm.submit();
}

function inactivate(group)
{
	document.listForm.action = "deleteGroup-"+group+".do";
	document.listForm.submit();
}

function activate(group)
{
	document.listForm.action = "undeleteGroup-"+group+".do";
	document.listForm.submit();
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="group.results.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>

		<form name="listForm" action="list.do">
	        <a href="javascript:backToSearch();">
	        	<img src="/sdc/images/btn_search.gif" alt="Back To Search" />
	        </a>
	        <br/>
	        <br/>
			<s:set name="jobz" value="availableGroupList" scope="request" />
			<display:table id="displayTagTable" name="jobz" pagesize="25" sort="list" class="simple" requestURI="listGroup.do" >
				<display:column property="schoolName" title="School Name" sortable="true"   />
				<display:column property="alias" title="Group Name" sortable="true"/>
				<display:column property="classroomNumber" title="Group Number" sortable="false" class="tdCenter" />
<s:if test="#attr.displayTagTable.classroomClosed == 1">
				<display:column title="Action" sortable="false" headerClass="tdCenter" class="tdCenter"><a onclick="return confirm('Are You Sure You Want to Activate this Group?')" href="javascript:activate(<s:property value='#attr.displayTagTable.classroomPk'/>);">ACTIVATE</a></display:column>
</s:if>
<s:else>
				<display:column title="Action" sortable="false" headerClass="tdCenter" class="tdCenter"><a onclick="return confirm('Are You Sure You Want to Inctivate this Group?')" href="javascript:inactivate(<s:property value='#attr.displayTagTable.classroomPk'/>);">INACTIVATE</a></display:column>
</s:else>
        	</display:table>
        	<s:hidden name="searchGroupName" />
        	<s:hidden name="searchSchoolFk" />
        	<s:hidden name="activeFlag" />
        </form>
    </body>
</html>
