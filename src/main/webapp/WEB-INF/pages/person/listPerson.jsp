<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="person.index.title"/></title>
        <script type='text/javascript' src='/sdc/common/sorttable.js'></script>
        <script type="text/javascript">
function backToSearch()
{
	document.listForm.action = "search.do";
	document.listForm.submit();
}

function inactivate(person)
{
	document.listForm.action = "delete-"+person+".do";
	document.listForm.submit();
}

function activate(person)
{
	document.listForm.action = "undelete-"+person+".do";
	document.listForm.submit();
}

function edit(person)
{
	document.listForm.action = "edit-"+person+".do";
	document.listForm.submit();
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="person.results.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <br/>
		<form name="listForm" action="search.do">
	        <a href="javascript:backToSearch();">
	        	<img src="/sdc/images/btn_search.gif" alt="Back To Search" />
	        </a>
       		<br/>
       		<br/>
			<table>
				<tr>
					<td style="border-width:0px;">
	        			<table class="sortable">
							<thead>
								<tr>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
									<th class="sorttable_nosort" style="text-align:center;">Action</th>
									<th class="sorttable_nosort" style="text-align:center;">Edit User</th>
								</tr>
							</thead>
               				<tbody>
<s:if test="availableList.size > 0">
	<s:iterator value="availableList">            
								<tr>
									<td><s:property value="firstName"/></td>
									<td><s:property value="lastName"/></td>
									<td><s:property value="email"/></td>
		<s:if test="deleted == 0">
									<td align="center">
										<a onclick="return confirm('Are You Sure You Want to Inactivate This Person?')" href="javascript:inactivate(<s:property value="personPk"/>);">INACTIVATE</a> 
									</td>
		</s:if>
		<s:else>
									<td align="center">
										<a onclick="return confirm('Are You Sure You Want to Activate This Person?')" href="javascript:activate(<s:property value="personPk"/>);">ACTIVATE</a> 
									</td>
		</s:else>
		<s:if test="deleted == 0">
									<td align="center">
										<a href="javascript:edit(<s:property value="personPk"/>);">EDIT</a>
									</td>
		</s:if>
		<s:else>
									<td>&nbsp;</td>
		</s:else>
								</tr>
	</s:iterator>
</s:if>
<s:else>
								<tr>
									<td colspan="5" align="center">No user results to display</td>
								</tr>
</s:else>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<p><a href="javascript:edit('');">Create New User</a></p>
			<s:hidden name="searchInstructor" />
			<s:hidden name="searchFirstName" />
			<s:hidden name="searchLastName" />
			<s:hidden name="searchSchoolName" />
			<s:hidden name="searchInstructorNum" />
			<s:hidden name="activeFlag" />
			<s:hidden name="editAction" value="Y"/>
		</form>
    </body>
</html>
