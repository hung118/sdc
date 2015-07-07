<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="vehicle.index.title"/></title>
        <script type='text/javascript' src='/sdc/common/sorttable.js'></script>
		<script type="text/javascript">
function backToSearch()
{
	document.listForm.action = "search.do";
	document.listForm.submit();
}

function inactivate(vehicle)
{
	document.listForm.action = "delete-"+vehicle+".do";
	document.listForm.submit();
}

function activate(vehicle)
{
	document.listForm.action = "undelete-"+vehicle+".do";
	document.listForm.submit();
}

function edit(vehicle)
{
	document.listForm.action = "edit-"+vehicle+".do";
	document.listForm.submit();
}
		</script>
	</head>
    <body>
        <br/>
        <h4><s:text name="vehicle.results.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="listForm" action="search">
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
									<th style="text-align:center;">Plate</th>
									<th style="text-align:center;">State</th>
									<th style="text-align:center;">School</th>
									<th class="sorttable_nosort" style="text-align:center;">Action</th>
									<th class="sorttable_nosort" style="text-align:center;">Edit Vehicle</th>
								</tr>
							</thead>
							<tbody>
<s:if test="availableList.size > 0">
	<s:iterator value="availableList">
								<tr>
									<td><s:property value="vehiclePlate"/></td>
									<td><s:property value="vehicleState"/></td>
									<td><s:property value="schoolName"/></td>
		<s:if test="deleted == 1">
									<td align="center">
										<a onclick="return confirm('Are You Sure You Want to Activate This Vehicle?')" href="javascript:activate(<s:property value="vehiclePk"/>);">ACTIVATE</a>
									</td>
		</s:if>
		<s:else>
									<td align="center">
										<a onclick="return confirm('Are You Sure You Want to Inactivate This Vehicle?')" href="javascript:inactivate(<s:property value="vehiclePk"/>);">INACTIVATE</a>
									</td>
		</s:else>
		<s:if test="deleted == 0">
									<td align="center"> 
										<a href="javascript:edit(<s:property value="vehiclePk"/>);">EDIT</a>
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
									<td colspan="5" align="center">No vehicle results to display</td>
								</tr>
</s:else>
							</tbody>
						</table>
                    </td>  
               </tr>
        	</table>
			<p><a href="javascript:edit('');">Create New Vehicle</a></p>
			<s:hidden name="searchVehiclePlate"/>
			<s:hidden name="searchSchoolFk"/>
			<s:hidden name="activeFlag"/>
			<s:hidden name="editAction" value="Y"/>
		</s:form>
    </body>
</html>
