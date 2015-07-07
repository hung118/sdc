<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
       <title><s:text name="commercial.studentremove.header"/></title>
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="commercial.studentremove.header"/></h1>
        <br/>
        <s:form id="removeStudentsForm" name="editForm" namespace="/commercial" action="removeStudents" >
            <tr><td>Select/Unselect All</td><td><input type="checkbox" id="selectAll" onClick="checkAll()"/></td></tr>
            <s:checkboxlist id="trainingArray" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="studentFullName" name="studentArray" />
            <s:hidden name="classroomPk"/>
            <s:submit value="%{getText('commercial.next')}" onclick="return removeStudentValidate()"/>
            </s:form>
    </body>
</html>
