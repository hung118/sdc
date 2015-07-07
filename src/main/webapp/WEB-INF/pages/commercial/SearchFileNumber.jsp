<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="commercial.index.title"/></title>
    </head>
    <body>
        <br/>
        <h4><s:text name="commercial.index.heading"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
<%-- set session param to null --%>
<s:if test="commercialAjaxMessages != null">
    <s:set scope="session" id="commercialAjaxMessages" value="null"/>
</s:if>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="student.lookupStudentby"/> <s:text name="student.fileNumber"/>
            <br/> <br/>
            <s:form namespace="/commercial" action="fileNumberSearch">
                <s:textfield label="%{getText('student.fileNumber')}" name="fileNumber" required="true"/>
                <logic:present role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolPk" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" required="true"/>
                </logic:present>
                <logic:notPresent role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolPk" list="commercialSchoolList" listKey="schoolPk" listValue="schoolName" required="true"/>
                </logic:notPresent>
                <s:submit value="%{getText('student.submit')}" />
                 <s:hidden name="classroomPk" value="0"/>
            </s:form>
        </div>
    </body>
    
</html>
