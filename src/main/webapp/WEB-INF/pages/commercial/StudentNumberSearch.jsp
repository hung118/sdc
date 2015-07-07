<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="student.title"/></title>
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <s:form  onsubmit="return studentNumberValidate()" cssClass="noBorders" id="addStudentNumber" namespace="/commercial" action="studentNumberSearch" >
                        <s:textfield label="Student Number" id="studentNumber" name="studentNumber"/>
                        <s:hidden name="classroomPk" />
                        <s:submit  onclick="return studentNumberValidate()" cssStyle="display:none"/>                    
                </s:form>
        </body>
</html>
