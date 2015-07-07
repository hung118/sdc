<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="commercial.student.title"/></title>
    </head>
    <body>
        <s:url id="urlHome" namespace="/commercial" action="commercialFront"></s:url>
        <s:a href="%{urlHome}">Back To <s:text name="commercial.classroom"/> List</s:a>
        <br/><br/>
        <s:actionerror/>
        <s:actionmessage/>
        <h4><s:text name="commercial.student.multiple.header"/></h4>
        <br/>
        <logic:notPresent role="GUEST">
            
            <div id="multipleForm" style="display:block;">
                <s:form cssClass="noBorders" name="editForm" namespace="/commercial" action="mergeFileNumber" >
                    <tr>
                        <s:if test="multipleClassroomList.size > 0"> 
                            
                            <td>
                                <span style="font-weight:bold"><s:text name="commercial.classrooms"/> Student Is Enrolled In:</span>
                                <br/> <br/>
                                <table>
                                    <tr>
                                        <th><s:text name="commercial.classrooms"/></th>
                                    </tr>
                                    <s:iterator value="multipleClassroomList">
                                        <tr>
                                            <td>
                                                <s:url id="url" namespace="/commercial" action="editStudent-">
                                                    <s:param name="studentPk" value="studentPk"/>
                                                    <s:param name="classroomPk"><s:property/></s:param>
                                                </s:url>
                                                <s:a href="%{url}"><s:text name="commercial.classroom"/> <s:property/></s:a>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                    <tr>
                                        <td>
                                            <s:url id="url" namespace="/commercial" action="noClassStudent-"><s:param name="studentPk" value="studentPk"/></s:url>
                                            <s:a href="%{url}">View All</s:a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </s:if> 
                    </tr>
                    <s:submit type="button" value="%{getText('cancel')}" id="cancel" onclick="window.location.href='%{urlHome}';return false;" />
                    
                    
                    <s:hidden name="studentPk"/>
                    <s:hidden name="studentNumber"/>
                    <s:hidden name="fileNumber"/>
                </s:form>
            </div>
            
        </logic:notPresent>
        
        <button id="show1" style="display:none"/> 
    </body>
    
    
</html>
