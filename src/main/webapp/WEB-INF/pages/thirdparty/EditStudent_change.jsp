<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="thirdparty.student.title"/></title>
        
		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
        
        <script type="text/javascript">
        YAHOO.namespace("example.container");
                function init() {
					
			// Define various event handlers for Dialog
			var handleYes = function() {
			this.hide();
                        var site = '<s:url namespace="/thirdparty" action="thirdPartyFront"/>';
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
			text: "Do you want to search again?",
			icon: YAHOO.widget.SimpleDialog.ICON_HELP,
			constraintoviewport: true,
			buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
			{ text:"No",  handler:handleNo } ]
			} );
			YAHOO.example.container.simpledialog1.setHeader("Return to search?");
            
                        // Render the Dialog
			YAHOO.example.container.simpledialog1.render(document.body);
                        
                       YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);
                       
                       }
            YAHOO.util.Event.addListener(window, "load", init);
            
            function validate(){
            	var now = new Date();
            	var todayStr = (now.getMonth() + 1) + "/" + now.getDate() + "/" + now.getFullYear();
            	var rtCompletionDate = document.getElementById("roadTestCompletionDate").value;
            	if (compareDates(rtCompletionDate, todayStr) == 1) {
            		alert("Road Test Completion Date cannot be a future date!");
            		return false;
            	} 
            	
            	return validateCommercialTimes('roadTest'); 
            }
            
            function dialog(){
             var d = document.forms[0];
             d.target = "_blank";
             document.getElementById("show1").click();
            }
        </script>    
        
    </head>
    <body>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        
        <h1><s:text name="thirdparty.student.header"/> - Update Change</h1>
        <br/>
        <logic:notPresent role="GUEST">
        <s:form name="editForm" namespace="/thirdparty" action="update_change"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <s:label cssClass="actionMessage" label="%{getText('student.fileNumber')}" name="currentStudent.fileNumber" />
            <s:hidden name="fileNumber" value="%{currentStudent.fileNumber}"/>
            <s:hidden name="currentStudent.fileNumber" value="%{currentStudent.fileNumber}"/>
            <s:if test="currentStudent.studentNumber != null">
                <s:label label="%{getText('student.studentNumber')}" name="currentStudent.studentNumber" />
            </s:if>
            <s:if test="currentStudent.eligibilityDate != null">
                <s:date format="MM/dd/yyyy" name="currentStudent.eligibilityDate" id="label_currentStudent.eligibilityDate"/>
                <s:label label="%{getText('student.eligibilityDate')}" value="%{label_currentStudent.eligibilityDate}"/>
                <s:hidden name="currentStudent.eligibilityDate" value="%{currentStudent.eligibilityDate}"/>
            </s:if>
        <s:if test="currentStudent.lastName == null">
        <s:textfield label="%{getText('student.firstName')}" name="currentStudent.firstName" required="true"/>
        <s:textfield label="%{getText('student.lastName')}" name="currentStudent.lastName" required="true"/>
        </s:if>
        <s:else>
        <s:label label="%{getText('student.firstName')}" name="currentStudent.firstName"/>
        <s:label label="%{getText('student.lastName')}" name="currentStudent.lastName" />
        <s:hidden name="firstName" value="%{currentStudent.firstName}"/>
        <s:hidden name="lastName" value="%{currentStudent.lastName}"/>
        </s:else>
        <s:hidden name="currentStudent.firstName" value="%{currentStudent.firstName}"/>
        <s:hidden name="currentStudent.lastName" value="%{currentStudent.lastName}"/>
        <s:if test="currentStudent.dob == null">

        <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="dobFormat"/>
        <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="currentStudent.dob" id="dob" cssClass="datepicker" maxlength="10"/>
        
        </s:if>
        <s:else>
        <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="label_currentStudent.dob"/>
        <s:label label="%{getText('student.dob')}" value="%{label_currentStudent.dob}"/>
        <s:hidden name="dob" value="%{currentStudent.dob}"/>
        </s:else>
        <s:hidden name="currentStudent.dob" value="%{currentStudent.dob}"/>
        
        <tr>
        	<td>School:</td>
        	<td>
        		<s:select theme="simple" name="schoolFk" list="roadSearchSchoolList" listKey="schoolPk" listValue="schoolName" onchange="$('#schoolAudit').val('Y')" />
				<s:date format="MM/dd/yyyy" name="school_datestamp" id="school_datestampFormat"/>
                <span class="studentAudit" ><s:property value="school_userid"/> <s:property value="%{school_datestampFormat}"/></span>
        	</td>
        </tr>
      	<tr>
      		<td>Road Test Completion Date (MM/DD/YYYY):</td>
      		<td>
		        <s:hidden name="currentStudent.roadTestCompletionDate" />
		        <s:date format="MM/dd/yyyy" name="roadTestCompletionDate" id="roadTestCompletionDateFormat"/>
		        <s:textfield theme="simple" name="roadTestCompletionDate" value="%{roadTestCompletionDateFormat}" id="roadTestCompletionDate" cssClass="datepicker" maxlength="10" onchange="$('#completionDateAudit').val('Y')" />
				<s:date format="MM/dd/yyyy" name="completion_date_datestamp" id="completion_date_datestampFormat"/>
                <span class="studentAudit" ><s:property value="completion_date_userid"/> <s:property value="%{completion_date_datestampFormat}"/></span>
      		</td>
      	</tr>
      
	        <s:textfield label="%{getText('thirdparty.roadTestStartTime')}" maxlength="5" size="5" id="roadTestStartTime" name="roadTestStartTime" required="true"/>
	        <s:textfield label="%{getText('thirdparty.roadTestEndTime')}" maxlength="5" size="5" id="roadTestEndTime" name="roadTestEndTime" required="true"/>
		<tr>
			<td>Road Score (points missed):
				<img alt="Road Score should be between 0 - 20. This should not be a percentage, it is a points reduction score" 
					title="Road Score should be between 0 - 20. This should not be a percentage, it is a points reduction score" src="/sdc/images/information.png">
			</td>
			<td>
	        	<s:textfield theme="simple" id="roadTestScore" name="roadTestScore" size="2" maxlength="3" 
	        		tooltip="Road Score should be between 0 - 20.  This should not be a percentage, it is a points reduction score"  required="true" onchange="$('#scoreAudit').val('Y')" />
				<s:date format="MM/dd/yyyy" name="score_datestamp" id="score_datestampFormat"/>
                <span class="studentAudit" ><s:property value="score_userid"/> <s:property value="%{score_datestampFormat}"/></span>
			</td>
		
		</tr>	        	        
	        <s:textfield id="routeNumber" label="%{getText('thirdparty.routeNumber')}" name="routeNumber" size="2" maxlength="2" tooltip="Two digit route number"  required="true"/>
	        <s:textfield id="routeArea" label="%{getText('thirdparty.routeArea')}" name="routeArea" maxlength="45" tooltip="City or area of road test" required="true"/>
		<tr>
			<td>Road Test Instructor:</td>
			<td>
				<s:select theme="simple" name="roadInstructorPk" list="roadInstructorList" listKey="personPk" listValue="fullName" required="true" onchange="$('#instructorAudit').val('Y')" />
				<s:date format="MM/dd/yyyy" name="instructor_datestamp" id="instructor_datestampFormat"/>
                <span class="studentAudit" ><s:property value="instructor_userid"/> <s:property value="%{instructor_datestampFormat}"/></span>
				
			</td>
		</tr>
	        <s:select label="%{getText('thirdparty.vehicle')}" name="vehicleFk" list="roadVehicleList" listKey="vehiclePk" listValue="vehiclePlate" required="true"/>
	        
	    	<s:if test="notes.length() == 0">
				<s:textarea label="Notes" name="notes" id="noteId" cols="45" rows="5"  onkeydown="textCounter(this.id,'counterId',1000);" onkeyup="textCounter(this.id,'counterId',1000);" onchange="$('#notesAudit').val('Y')" />
				<tr>
					<td colspan="2" style="text-align:right;">
						<b><span id="counterId">1000</span></b> Remaining Characters
					</td>
				</tr>
			</s:if>
			<s:else>
			    <logic:present role="ADMIN">
					<s:textarea label="Notes" name="notes" id="noteId" cols="45" rows="5"  onkeydown="textCounter(this.id,'counterId',1000);" onkeyup="textCounter(this.id,'counterId',1000);" onchange="$('#notesAudit').val('Y')" />
					<tr>
						<td colspan="2" style="text-align:right;">
							<b><span id="counterId">1000</span></b> Remaining Characters
						</td>
					</tr>			    
			    </logic:present>
    		    <logic:notPresent role="ADMIN">
			    	<s:if test="notes_userid == authPerson.email">
						<s:textarea label="Notes" name="notes" id="noteId" cols="45" rows="5"  onkeydown="textCounter(this.id,'counterId',1000);" onkeyup="textCounter(this.id,'counterId',1000);" onchange="$('#notesAudit').val('Y')" />
						<tr>
							<td colspan="2" style="text-align:right;">
								<b><span id="counterId">1000</span></b> Remaining Characters
							</td>
						</tr>		
			    	</s:if>
			    </logic:notPresent>
			</s:else>
	        <s:submit type="button" value="%{getText('student.update')}" onclick="return validate();" action="update_change" />

        <s:hidden name="studentPk" value="%{currentStudent.studentPk}"/>
        <s:hidden name="licenseType" value="%{currentStudent.licenseType}"/>
        <s:hidden name="currentStudent.licenseType" value="%{currentStudent.licenseType}"/>
        <s:hidden name="currentStudent.studentPk" value="%{currentStudent.studentPk}"/>
        <s:hidden name="completionRoadPk" />
        
        <s:hidden name="schoolAudit" id="schoolAudit"/>
        <s:hidden name="completionDateAudit" id="completionDateAudit"/>
        <s:hidden name="scoreAudit" id="scoreAudit"/>
        <s:hidden name="instructorAudit" id="instructorAudit"/>
        <s:hidden name="notesAudit" id="notesAudit"/>
        </s:form>
        </logic:notPresent>
        <button id="show1" style="display:none"></button>
        
        <script type="text/javascript">
	        showInitCount('noteId','counterId',1000);
			$("input.datepicker").livequery(function() {
					$(this).datepicker({
						changeMonth: true,
						changeYear: true,
						showOtherMonths: true,
						selectOtherMonths: true,
						constrainInput: true,
						dateFormat: 'mm/dd/yy',
						showOn: "button",
						buttonImage: "/sdc/images/calbtn.gif",
						buttonImageOnly: true});
					$(this).mask("99/99/9999");
			});
			$("#roadTestStartTime").mask("99:99");
			$("#roadTestEndTime").mask("99:99");		
		</script>

    </body>
    
    
</html>
