<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="thirdparty.studentNumberSearch.title"/></title>

    </head>
    <body>
        <br/>
        <h4><s:text name="thirdparty.studentNumberSearch.heading"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <%-- set session param to null --%>
        <s:if test="thirdPartyAjaxMessages != null">
            <s:set scope="session" id="thirdPartyAjaxMessages" value="null"/>
        </s:if>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="student.lookupStudentby"/> <s:text name="student.studentNumber"/>
            <br/> <br/>
            <s:form namespace="/thirdparty" action="studentNumberSearch">
                <s:textfield label="%{getText('student.studentNumber')}" name="studentNumber" required="true"/>
                <logic:present role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" headerKey="0" headerValue="Select a School"/>
                </logic:present>
                <logic:notPresent role="ADMIN">
                    <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="commercialSchoolList" listKey="schoolPk" listValue="schoolName" headerKey="0" headerValue="Select a School" />
                </logic:notPresent>
                <s:submit value="%{getText('student.submit')}" />
                <s:hidden name="classroomPk" value="0"/>
            </s:form>
        </div>
        
        <br/>
    </body>
    
</html>
