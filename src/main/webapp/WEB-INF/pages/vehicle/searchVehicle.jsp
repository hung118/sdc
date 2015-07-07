<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="vehicle.index.title"/></title>
		<script type="text/javascript">
function clearForm()
{
	document.searchForm.searchVehiclePlate.value = "";
	document.searchForm.searchSchoolFk.value = "";
	document.searchForm.activeFlag[0].checked = true;
	return true;
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="vehicle.search.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="vehicle.lookupBy"/>
            <br/> <br/>
            <s:form name="searchForm" namespace="/vehicle" action="list">
		        <table>
					<s:textfield label="%{getText('vehicle.vehiclePlate')}" name="searchVehiclePlate" maxLength="10"/>
					<s:select label="%{getText('vehicle.highschools')}" name="searchSchoolFk" id="searchSchoolFk" multiple="false" list="commercialSchoolListAll" headerKey="" headerValue="Select School" listKey="schoolPk" listValue="schoolName" size="1"/>
	        		<tr>
	        			<td class="tdLabel">Active Filter:</td>
	        			<td class="tdClass">
<s:if test="%{activeFlag == null}">
	        				<input type="radio" name="activeFlag" value="0">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="" checked="checked">All</input>
</s:if>
<s:elseif test="%{activeFlag == '0'}">
	        				<input type="radio" name="activeFlag" value="0" checked="checked">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="">All</input>
</s:elseif>
<s:elseif test="%{activeFlag == '1'}">
	        				<input type="radio" name="activeFlag" value="0">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1" checked="checked">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="">All</input>
</s:elseif>
<s:else>
	        				<input type="radio" name="activeFlag" value="0">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="" checked="checked">All</input>
</s:else>
	        			</td>
	        		</tr>
                	<tr>
                		<td style="border-right-width: 0px;" align="left">
                			<input align="left" type="button" id="btn_0" value="Clear" onclick="clearForm();"/>
                		</td>
                		<td style="border-left-width: 0px;" align="right">
                			<input align="right" type="submit" id="btn_1" value="Search"/>
                		</td>
                	</tr>
                	<s:hidden name="vehiclePk" value=""/>
	        	</table>
            </s:form>
        </div>
    </body>
    
</html>
