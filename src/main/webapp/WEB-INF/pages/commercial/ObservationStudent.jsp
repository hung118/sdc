<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="student.title"/></title>
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="commercial.observation.header"/></h1>
        <br/>
        <s:form name="editForm" namespace="/commercial" action="inputObservationTime"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <s:checkboxlist id="observationArray" tooltip="Choose Up To Three Students" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="nameDob" name="studentArray" />
            
            <s:hidden name="classroomPk"/>
            <s:submit value="%{getText('commercial.next')}" onclick="return observationValidate()"/>
            </s:form>
    </body>
</html>
