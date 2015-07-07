<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
       <title><s:text name="student.title"/></title>
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="commercial.training.header"/></h1>
        <br/>
        <s:form name="editForm" namespace="/commercial" action="inputTrainingTime" tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <tr><td>Select/Unselect All</td><td><input type="checkbox" id="selectAll" onClick="checkAll()"/></td></tr>
            <%--
            <s:select id="trainingArray" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="nameDob" name="studentArray" multiple="true" required="true"/>
            --%>
            <s:checkboxlist id="trainingArray" tooltip="Choose Up To Thirty Students" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="nameDob" name="studentArray" />
            <s:hidden name="classroomPk"/>
            <s:submit value="%{getText('commercial.next')}" onclick="return trainingValidate()"/>
            </s:form>
    </body>
</html>
