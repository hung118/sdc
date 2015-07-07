<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/struts-dojo-tags" prefix="dojo" %>

<html>
    <head>
        <title>SDC Home Study Score</title>       
    </head>
    <body>
        <br/>
        <h4>SDC Home Study Score</h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editStudent-">
                <s:param name="classroomPk" value="classroomPk"/>
                <s:param name="studentPk" value="studentPk"/>
            </s:url>
            <s:a href="%{url}">Back To Student</s:a>
        </h2>
        <br/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="updateStudentOcsScore">
                <s:token />
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Home Study Score</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:textfield label="Home Study Score" name="ocsScore" size="3" maxlength="3" />
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <s:submit value="%{getText('submit')}" />
                <s:hidden name="studentPk"/>
            </s:form>
        </div>
        
        <br/>
    </body>
    
</html>