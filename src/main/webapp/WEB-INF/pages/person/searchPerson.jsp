<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="person.index.title"/></title>

<script type="text/javascript">
function clearForm() {
	document.searchForm.searchInstructor[0].checked = true;
	document.searchForm.searchFirstName.value = "";
	document.searchForm.searchLastName.value = "";
	document.searchForm.searchSchoolName.value = "";
	document.searchForm.searchInstructorNum.value = "";
	document.searchForm.activeFlag[0].checked = true;
	return true;
}
</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="person.search.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="person.lookupBy"/>
            <br/>
            <br/>
            <s:form name="searchForm" namespace="/person" action="list.do">
            	<table>
	        		<tr>
	        			<td class="tdLabel">User Type:</td>
	        			<td class="tdClass">
<s:if test="%{searchInstructor == null}">
	        				<input type="radio" name="searchInstructor" value="" checked="checked">All Users</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="Y">Instructor</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="N">Non-instructor</input>
</s:if>
<s:elseif test="%{searchInstructor == 'Y'}">
	        				<input type="radio" name="searchInstructor" value="">All Users</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="Y" checked="checked">Instructor</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="N">Non-instructor</input>
</s:elseif>
<s:elseif test="%{searchInstructor == 'N'}">
	        				<input type="radio" name="searchInstructor" value="">All Users</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="Y">Instructor</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="N" checked="checked">Non-instructor</input>
</s:elseif>
<s:else>
	        				<input type="radio" name="searchInstructor" value="" checked="checked">All Users</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="Y">Instructor</input>&nbsp;&nbsp;
	        				<input type="radio" name="searchInstructor" value="N">Non-instructor</input>
</s:else>
	        			</td>
	        		</tr>
	                <s:textfield label="%{getText('person.firstName')}" name="searchFirstName" size="30"/>
    	            <s:textfield label="%{getText('person.lastName')}" name="searchLastName" size="30"/>
        	        <s:textfield label="%{getText('person.schoolName')}" name="searchSchoolName" size="40"/>
            	    <s:textfield label="%{getText('person.instructornum')}" name="searchInstructorNum"/>
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
                	<s:hidden name="personPk" value=""/>
	        	</table>
            </s:form>
        </div>
    </body>
    
</html>
