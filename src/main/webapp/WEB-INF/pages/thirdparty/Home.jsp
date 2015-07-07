<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="thirdparty.index.title"/></title>
    </head>
    <body>
        <br/>
        <h4><s:text name="thirdparty.index.heading"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="student.lookupStudentby"/> <s:text name="student.fileNumber"/>
            <br/> <br/>
            <s:form namespace="/thirdparty" action="webSearch">
                <s:textfield label="%{getText('student.fileNumber')}" name="fileNumber" required="true"/>
                <logic:present role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" required="true"/>
                </logic:present>
                <logic:notPresent role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="commercialSchoolList" listKey="schoolPk" listValue="schoolName" required="true"/>
                </logic:notPresent>
                <s:submit value="%{getText('student.submit')}" />
                <s:submit action="inputCreate" value="%{getText('student.createStudentRecord')}"/>
            </s:form>
        </div>
    </body>
    
</html>
