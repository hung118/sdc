<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="school.index.title"/></title>
<script type="text/javascript">
function clearForm()
{
	document.searchForm.searchSchoolType[0].checked = true;
	document.searchForm.searchSchoolName.value = "";
	document.searchForm.searchSchoolNumber.value = "";
	document.searchForm.searchCity.value = "";
	document.searchForm.activeFlag[0].checked = true;
	return true;
}
</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="school.search.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="person.lookupBy"/>
            <br/> <br/>
            <s:form name="searchForm" namespace="/school" action="list">
		        <table>
	        		<tr>
	        			<td class="tdLabel">School Type:</td>
	        			<td class="tdClass">
<s:if test="%{searchSchoolType == null}">
	        				<input type="radio" name="searchSchoolType" value="" checked="checked">All School Types</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="0">High Schools</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="1">Commercial</input>
</s:if>
<s:elseif test="%{searchSchoolType == '0'}">
	        				<input type="radio" name="searchSchoolType" value="">All School Types</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="0" checked="checked">High Schools</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="1">Commercial</input>
</s:elseif>
<s:elseif test="%{searchSchoolType == '1'}">
	        				<input type="radio" name="searchSchoolType" value="">All School Types</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="0">High Schools</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="1" checked="checked">Commercial</input>
</s:elseif>
<s:else>
	        				<input type="radio" name="searchSchoolType" value="" checked="checked">All School Types</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="0">High Schools</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchSchoolType" value="1">Commercial</input>
</s:else>
	        			</td>
	        		</tr>
	                <s:textfield label="%{getText('school.schoolName')}" name="searchSchoolName" size="40"/>
    	            <s:textfield label="%{getText('school.schoolNumber')}" name="searchSchoolNumber"/>
        	        <s:textfield label="%{getText('school.city')}" name="searchCity" size="30"/>
	        		<tr>
	        			<td class="tdLabel">Active Filter:</td>
	        			<td class="tdClass">
<s:if test="%{activeFlag == null}">
	        				<input type="radio" name="activeFlag" value="0">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="" checked="checked">All</input>
</s:if>
<s:elseif test="%{activeFlag == 0}">
	        				<input type="radio" name="activeFlag" value="0" checked="checked">Active</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="1">Inactive</input>&nbsp;&nbsp;
	        				<input type="radio" name="activeFlag" value="">All</input>
</s:elseif>
<s:elseif test="%{activeFlag == 1}">
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
                	<s:hidden name="schoolPk" value=""/>
	        	</table>
            </s:form>
        </div>
    </body>
    
</html>
