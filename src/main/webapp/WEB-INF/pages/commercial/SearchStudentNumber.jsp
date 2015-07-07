<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="commercial.studentNumberSearch.title"/></title>

    </head>
    <body>
        <br/>
        <h4><s:text name="commercial.studentNumberSearch.heading"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <%-- set session param to null --%>
        <s:if test="commercialAjaxMessages != null">
            <s:set scope="session" id="commercialAjaxMessages" value="null"/>
        </s:if>
        
        <div id="basicSearchForm" style="display:block;">
            <s:text name="student.lookupStudentby"/> <s:text name="student.studentNumber"/>
            <br/> <br/>
            <s:form namespace="/commercial" action="studentNumberSearch">
                <s:textfield label="%{getText('student.studentNumber')}" name="studentNumber" required="true"/>
                <s:submit value="%{getText('student.submit')}" />
                <s:hidden name="classroomPk" value="0"/>
            </s:form>
        </div>
        
        <br/>
    </body>
    
</html>
