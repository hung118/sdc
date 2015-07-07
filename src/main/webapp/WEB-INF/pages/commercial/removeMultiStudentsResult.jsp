<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title><s:text name="commercial.studentremove.header"/></title>		
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h4>SDC Delete Students from <s:property value="currentClassroom.getSchoolName()" /> - <s:property value="currentClassroom.getAlias()" /></h4>
        <br/>
        <s:form id="removeStudentsForm" name="editForm" namespace="/commercial" action="removeStudents" >
	        <tr>
	            <td colspan="2">
	                <table>
			           <tr>
			           	<td>
			           		&nbsp;
			           	</td>
			           	<td>
			           		<span id="checkAllId" style="color:red"><s:property value="resultMsg" /></span>
			           	</td>
			           </tr>
			          	<tr>
			          		<td>
			          			Students: 
			          		</td>
			          		<td>
								<s:iterator value="studentFromStudentArray">
			          				<s:property value="nameDob"/> <br />
			          			</s:iterator>
			          		</td>
			          	</tr>
			          	<tr><td colspan="2">
			          		<div style="text-align:right">
			          			<input type="button" onclick="window.print()" value="Print">
							</div>
			          	</td></tr>
	                </table>
	            </td>
	        </tr>
		</s:form>
    </body>
</html>
