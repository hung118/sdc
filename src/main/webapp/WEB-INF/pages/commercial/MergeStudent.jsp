<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title><s:text name="student.title"/></title>
    <script type="text/javascript">
        YAHOO.namespace("example.container");
                function init() {
					
			// Define various event handlers for Dialog
			var handleYes = function() {
			this.hide();
                        var site = '<s:url namespace="/commercial" action="editClassroom-"><s:param name="classroomPk"/></s:url>';
                       window.location.href=site;
		};
			var handleNo = function() {
			this.hide();
			};

			// Instantiate the Dialog
			YAHOO.example.container.simpledialog1 = new YAHOO.widget.SimpleDialog("simpledialog1", 
			{ width: "300px",
			fixedcenter: true,
			visible: false,
                         modal:true,
			draggable: false,
			close: true,
			text: "Return to Classroom?",
			icon: YAHOO.widget.SimpleDialog.ICON_HELP,
			constraintoviewport: true,
			buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
			{ text:"No",  handler:handleNo } ]
			} );
			YAHOO.example.container.simpledialog1.setHeader("Return to Classroom?");
            
                        // Render the Dialog
			YAHOO.example.container.simpledialog1.render(document.body);
                        
                       YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);
                       
                       }
            YAHOO.util.Event.addListener(window, "load", init);
 
    </script>    
    
</head>
<body>
<s:url id="url" namespace="/commercial" action="editClassroom-"><s:param name="classroomPk" value="classroomPk"/></s:url>
<s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To Classroom"/></s:a>
<br/>
<s:actionerror/>
<s:actionmessage/>
<h4><s:text name="commercial.student.merge.header"/></h4>
<br/>
<logic:notPresent role="GUEST">
    
    <div id="mergeForm" style="display:block;">
    <s:form cssClass="noBorders" name="editForm" namespace="/commercial" action="mergeFileNumber" >
        <tr>
            <s:if test="mergeDbStudent.size > 0"> 
                
                    <td>
                       <span style="font-weight:bold">Current Student Info:</span>
                        <br/> <br/>
                        <table>
                            <tr>
                                <th>SDC</th>
                            </tr>
                            <s:iterator value="mergeDbStudent">
                                <tr>
                                    <td><s:property/></td>
                                </tr>
                            </s:iterator>
                        </table>
                    </td>
                </s:if> 
                
                <s:if test="mergeWebStudent.size > 0"> 
                <td>
                       
                        <span style="font-weight:bold">Possible Student Match:</span>
                        <br/> <br/>
                        <table>
                            <tr>
                                <th>Driver License</th>
                            </tr>
                            <s:iterator value="mergeWebStudent">
                                <tr>
                                    <td><s:property /></td>
                                </tr>
                            </s:iterator>
                        </table>
                    </td>
                </s:if> 
                </tr>
                <s:submit type="button" value="%{getText('cancel')}" id="cancel" onclick="window.location.href='%{url}';return false;" />
                    
        <s:submit value="%{getText('registration.merge')}" />
        <s:hidden name="classroomPk"/>
        <s:hidden name="studentPk"/>
        <s:hidden name="studentNumber"/>
        <s:hidden name="fileNumber"/>
    </s:form>
    </div>
    
</logic:notPresent>

<button id="show1" style="display:none"/> 
</body>


</html>
