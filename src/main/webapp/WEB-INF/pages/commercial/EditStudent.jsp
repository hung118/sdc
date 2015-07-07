<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name="student.title" /></title>
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
                        var site = '<s:url namespace="/commercial" action="editClassroom-"><s:param name="classroomPk" value="classroomPk"/></s:url>';
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
			text: "Return to <s:text name="commercial.classroom"/> ?",
			icon: YAHOO.widget.SimpleDialog.ICON_HELP,
			constraintoviewport: true,
			buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
			{ text:"No",  handler:handleNo } ]
			} );
			YAHOO.example.container.simpledialog1.setHeader("Return to <s:text name="commercial.classroom"/>?");
            
                        // Render the Dialog
			YAHOO.example.container.simpledialog1.render(document.body);
                        
                       YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);
                       
                       }
            YAHOO.util.Event.addListener(window, "load", init);
            
 function validate(){
    var retVal = true;
    return retVal;
 }
            
            function update(action){
               var d = document.forms[0];
               if(action == "generateFullCompletion") {
               <s:url action="generateFullCompletion" id="submitAction2"/>
                d.action = "<s:property value='%{submitAction2}'/>";
                d.target = "_blank";
                document.getElementById("show1").click();
                }else {
                return false;
                }
            }
           
     function format(dt) {
       if(YAHOO.lang.isString(dt)) {
         var parts = dt.split("/");
         var month = parts[0];
         var day = parts[1];
         var year = parts[2];
         return  month + "/" + day + "/" +year ;
       } else {
         var month = dt.getMonth();
         var day = dt.getDate();
         var year = dt.getFullYear();
         return  month + "/" + day + "/" +year ;
       }
     }
     
     function currentDate() {
         var today = new Date();
         alert(today);
     }

     function validateOcsScore() {
		if (document.getElementById("updateStudentOcsScore_ocsScore").value == "") {
			alert("Please enter Home Study Score.");
			return false;
		} else {
			return true;
		}
     }
     
     function setAuditFields(field) {
     	$("#" + field + "_userid").val("${authPerson.email}");
     	$("#" + field + "_datestamp").val($.datepicker.formatDate('mm/dd/yy', new Date()));
     }
     
  </script>

</head>
<body>
<s:url id="url" namespace="/commercial" action="editClassroom-">
	<s:param name="classroomPk" value="classroomPk" />
</s:url>
<s:a href="%{url}">
	<img src="/sdc/images/btn_classroom.gif" alt="Back To <s:text name="commercial.classroom"/>" />
</s:a>
<br />
<s:actionerror />
<s:actionmessage />

<h4><s:text name="commercial.editstudent.header" /></h4>
<br />
<logic:notPresent role="GUEST">
	<div class="labelFormat">
<s:if test="currentStudent.fileNumber != null">
		<s:label cssClass="actionMessage" label="%{getText('student.fileNumber')}" name="currentStudent.fileNumber" />
		<br />
</s:if> 
		<s:label label="%{getText('student.studentNumber')}" name="currentStudent.studentNumber" /> <br />
<s:if test="schoolName != null">
		<s:label label="%{getText('student.schoolName')}" name="schoolName" />
		<br />
</s:if>
<s:if test="currentStudent.eligibilityDate != null">
		<s:date format="MM/dd/yyyy" name="currentStudent.eligibilityDate" id="label_currentStudent.eligibilityDate" />
		<s:label label="%{getText('student.eligibilityDate')}" value="%{label_currentStudent.eligibilityDate}" />
		<br />
</s:if>
		<s:label cssClass="boldText" label="%{getText('student.lastName')}" name="currentStudent.lastName" />
		<s:label label="%{getText('student.firstName')}" name="currentStudent.firstName" />
<s:if test="currentStudent.middleName != null && currentStudent.middleName != ''">
		<s:label label="%{getText('student.middleName')}" name="currentStudent.middleName" />
</s:if>
		<br />
		<s:date format="MM/dd/yyyy" name="currentStudent.dob" id="label_currentStudent.dob" />
		<s:label label="%{getText('student.dobNoFormat')}" value="%{label_currentStudent.dob}" />
<s:if test="currentStudent.address1 != null">
		<br />
		<s:label label="%{getText('student.address1')}" name="currentStudent.address1" />
</s:if>
<s:if test="currentStudent.address2 != null">
		<br />
		<s:label label="%{getText('student.address2')}" name="currentStudent.address2" />
</s:if>
<s:if test="currentStudent.city != null">
		<br />
		<s:label label="%{getText('student.city')}" name="currentStudent.city" />
		<s:label label="%{getText('student.state')}" name="currentStudent.state" />
		<s:label label="%{getText('student.zip')}" name="currentStudent.zip" />
</s:if>
	</div>

<s:if test="commercialAjaxMessages != null">
	<div id="ajaxMessages" class="errorMessage" style="text-align: left">
		<s:iterator value="commercialAjaxMessages">
			<s:property escape="false" />
			<br />
		</s:iterator>
		<s:set scope="session" id="commercialAjaxMessages" value="null" />
	</div>
</s:if>

<s:if test="currentStudent.fileNumber == null">
	<a href="<s:url action="viewUpdateStudentInformation-%{studentPk}-%{classroomPk}" />">Edit Student Information</a>
	<div class="greenTable">
		<s:form cssClass="noBorders" name="editForm" namespace="/commercial" tooltipDelay="500" tooltipIconPath="/images/information.png">
			<s:textfield label="%{getText('student.fileNumber')}" name="fileNumber" />
			<s:submit type="button" value="%{getText('student.updateFileNumber')}" action="commercialSyncFileNumber" />
			<s:hidden name="classroomPk" />
			<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
			<s:hidden name="studentPk" />
		</s:form>
	</div>	
</s:if>

<s:if test="currentStudent.fileNumber != null && (observationCompletionDate != null || trainingCompletionDate != null)">
	<div class="greenTable">
		<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
			<s:date format="MM/dd/yyyy" name="currentStudent.eligibilityDate" id="label_currentStudent.eligibilityDate" />
			<s:label cssClass="actionMessage" label="%{getText('student.eligibilityDate')}" value="%{label_currentStudent.eligibilityDate}" />
			<br />
			<s:submit type="button" value="%{getText('student.generateFullCompletion')}" id="generateFullCompletion" onclick="return update('generateFullCompletion');" />
			<s:hidden name="classroomPk" />
			<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
			<s:hidden name="schoolPk" />
			<s:hidden name="studentPk" />
		</s:form>
	</div>
</s:if>

	<div id="observationForm" style="display: block;">
		<br />
		<h4>Observation</h4>
		<br />
		<s:if test="observationHours >= 6">
			<s:date format="MM/dd/yyyy" name="observationCompletionDate" id="observationCompletionDateFormat" />
			<s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.observationCompletionDate_datestamp" id="observationCompletionDate_datestampFormat"/>
			<s:if test="fileNumber !=null">
				<s:if test="observationCompletionDate != null">
					<logic:notPresent role="ADMIN">
						<div class="labelFormat">
							<s:label label="%{getText('student.observationCompletionDateNoFormat')}" value="%{observationCompletionDateFormat}" />
							<span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionDate_userid"/> <s:property value="%{observationCompletionDate_datestampFormat}"/></span>
						</div>							
					</logic:notPresent>
					<logic:present role="ADMIN">
						<div class="greenTable">
							<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
								<tr>
									<td>Observation Completion Date (MM/DD/YYYY):</td>
									<td>
										<s:textfield theme="simple" value="%{observationCompletionDateFormat}" name="observationCompletionDate"  id="observationCompletionDate" cssClass="datepicker" maxlength="10"/>
									</td>
								</tr>
								<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionDate_userid"/> <s:property value="%{observationCompletionDate_datestampFormat}"/></span></td>						
								<s:submit type="button" value="Update Completion Date" action="insertObservationCompletion" />
								<s:hidden name="fileNumber" />
								<s:hidden name="classroomPk" />
								<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
								<s:hidden name="studentPk" />
							</s:form>
						</div>
					</logic:present>
				</s:if>
				<s:else>
					<div class="greenTable">
						<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
							<tr>
								<td>Observation Completion Date (MM/DD/YYYY):</td>
								<td>
									<s:textfield theme="simple" value="%{observationCompletionDateFormat}" name="observationCompletionDate"  id="observationCompletionDate" cssClass="datepicker" maxlength="10"/>
								</td>
							</tr>
							<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionDate_userid"/> <s:property value="%{observationCompletionDate_datestampFormat}"/></span></td>
							<s:submit type="button"	value="%{getText('commercial.submit.completion')}" action="insertObservationCompletion" />
							<s:hidden name="fileNumber" />
							<s:hidden name="classroomPk" />
							<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
							<s:hidden name="studentPk" />
						</s:form>
					</div>
				</s:else>
			</s:if>
			<s:else>
				<div class="greenTable">
					<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
						<tr>
							<td>Observation Completion Date (MM/DD/YYYY):</td>
							<td>
								<s:textfield theme="simple" value="%{observationCompletionDateFormat}" name="observationCompletionDate"  id="observationCompletionDate" cssClass="datepicker" maxlength="10"/>
							</td>
						</tr>
						<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionDate_userid"/> <s:property value="%{observationCompletionDate_datestampFormat}"/></span></td>
						<s:submit type="button" value="%{getText('commercial.submit.completion')}" action="insertObservationCompletion" />
						<s:hidden name="fileNumber" />
						<s:hidden name="classroomPk" />
						<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
						<s:hidden name="studentPk" />
					</s:form>
				</div>
			</s:else>
		</s:if> 
		<s:form namespace="/commercial" onsubmit="return validateCommercialTimes('observation')" action="singleInsertObservation">
			<s:token />
			<tr>
				<td colspan="2">
					<table class="noBorders">
						<tr>
							<th style="text-align: center;">Date</th>
							<th style="text-align: center;">Start Time</th>
							<th style="text-align: center;">End Time</th>
							<th style="text-align: center;">Instructor</th>
							<th style="text-align: center;">Vehicle</th>
							<th style="text-align: center;">Branch</th>
						</tr>
						<tr>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:textfield name="observationDate"  id="observationDate" cssClass="datepicker" maxlength="10"/>
<%--						 		<input type="hidden" property="observationDate"/>
--%>
								</table>
							</td>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:textfield maxlength="5" size="5" id="observationStartTime" name="observationStartTime" />
								</table>
							</td>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:textfield maxlength="5" size="5" id="observationEndTime" name="observationEndTime" />
								</table>
							</td>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:select name="instructorFk" list="instructorList" listKey="personPk" listValue="fullName" />
								</table>
							</td>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:select name="vehicleFk" list="vehicleList" listKey="vehiclePk" listValue="vehiclePlate" />
								</table>
							</td>
							<td class="standardcell" style="text-align: center;">
								<table>
									<s:select name="branchFk" list="branchList" listKey="branchPk" listValue="address1" headerKey="" headerValue="Main Office" />
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<s:submit id="observationSubmitButton" value="%{getText('submit')}" />
			<s:hidden name="classroomPk" />
			<s:hidden name="studentPk" />
			<s:hidden name="editStudentSubmit" value="true" />
<%-- if left as a hidden field struts will not convert this to an array  --%>
			<table class="noBorders">
				<s:select multiple="true" id="studentArray" list="%{studentPk}" value="studentPk" name="studentArray" cssStyle="display:none;" />
			</table>
		</s:form>
		<br />
	<s:if test="observationList.size > 0">
		<h3>Observation List: <s:if test="observationHours != 0">
			<s:if test="observationHours >= 6">
				<span class="actionMessage">Total Observation Hours <s:property
					value="%{observationHours}" /> </span>
			</s:if>
			<s:else>
				<span class="actionError">Total Observation Hours <s:property
					value="%{observationHours}" /></span>
			</s:else>
		</s:if></h3>
		<br />
		<table>
			<tr>
				<th style="text-align: center;">Branch</th>
				<th style="text-align: center;">Start Time</th>
				<th style="text-align: center;">End Time</th>
				<th style="text-align: center;">Instructor</th>
				<th style="text-align: center;">Vehicle</th>
				<th style="text-align: center;">Audit</th>
				<th style="text-align: center;">Delete</th>
				<th style="text-align: center;">Edit</th>
			</tr>
			<s:iterator value="observationList">
				<tr>
					<td style="text-align: center;"><s:property value="branchName" /></td>
					<td style="text-align: center;"><s:date name="startTime"
						format="MM/dd/yyyy  hh:mm a" /></td>
					<td style="text-align: center;"><s:date name="endTime"
						format="MM/dd/yyyy  hh:mm a" /></td>
					<td style="text-align: center;"><s:property
						value="instructorFullName" /></td>
					<td style="text-align: center;"><s:property
						value="vehicleFullDesc" /></td>
					<td style="text-align: center;" class="studentAudit">
						<s:date format="MM/dd/yyyy" name="observation_datestamp" id="observation_datestampFormat"/>
						<s:if test="observation_userid != null">
							<s:property value="observation_userid" /> <s:property value="%{observation_datestampFormat}" />
						</s:if>
					</td>
					<td style="text-align: center;">
						<logic:present role="ADMIN">
							[<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="singleDeleteObservation-%{timePk}-%{studentPk}-%{classroomPk}" />">X</a>]
						</logic:present>
						<logic:notPresent role="ADMIN">
							<logic:present role="COMMERCIAL">
								NA
							</logic:present>
						</logic:notPresent>
					</td>
					<td style="text-align: center;">
						<logic:present role="ADMIN">
							<a href="<s:url action="singleViewUpdateObservation-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
						</logic:present>
						<logic:notPresent role="ADMIN">
							<logic:present role="COMMERCIAL">
								<s:if test="editable != 0">
									<a href="<s:url action="singleViewUpdateObservation-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
								</s:if>
								<s:else>
									NA
								</s:else>
							</logic:present>
						</logic:notPresent>
					</td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
	</div>

	<div id="trainingForm" style="display: block;"><br />
	<h4><s:text name="commercial.training" /></h4>
	<br />
	
	<s:if test="trainingHours >= 18 || (currentStudent.ocsScore >= 80 && currentStudent.ocsScore <= 100)">
		<s:date format="MM/dd/yyyy" name="trainingCompletionDate" id="trainingCompletionDateFormat"/>
		<s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.classroomCompletionDate_datestamp" id="classroomCompletionDate_datestampFormat"/>
		<s:if test="fileNumber !=null">
			<s:if test="trainingCompletionDate != null">
				<logic:notPresent role="ADMIN">
					<div class="labelFormat">
						<s:label label="%{getText('student.trainingCompletionDateNoFormat')}" value="%{trainingCompletionDateFormat}" />
						<span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionDate_userid"/> <s:property value="%{classroomCompletionDate_datestampFormat}"/></span>
					</div>
				</logic:notPresent>
				<logic:present role="ADMIN">
					<div class="greenTable">
						<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
							<tr>
								<td>Classroom Training Completion Date (MM/DD/YYYY):</td>
								<td>
									<s:textfield theme="simple" value="%{trainingCompletionDateFormat}" name="trainingCompletionDate" id="trainingCompletionDate" cssClass="datepicker" maxlength="10"/>
								</td>
							</tr>
							<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionDate_userid"/> <s:property value="%{classroomCompletionDate_datestampFormat}"/></span></td>
							<s:submit type="button" value="Update Completion Date" action="insertClassroomCompletion" />
							<s:hidden name="fileNumber" />
							<s:hidden name="classroomPk" />
							<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
							<s:hidden name="studentPk" />
						</s:form>
					</div>
				</logic:present>
			</s:if>
			<s:else>
				<div class="greenTable"><s:form cssClass="noBorders" name="editForm" namespace="/commercial">
					<tr>
						<td>Classroom Training Completion Date (MM/DD/YYYY):</td>
						<td>
							<s:textfield theme="simple" value="%{trainingCompletionDateFormat}" name="trainingCompletionDate" id="trainingCompletionDate" cssClass="datepicker" maxlength="10"/>
						</td>
					</tr>
					<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionDate_userid"/> <s:property value="%{classroomCompletionDate_datestampFormat}"/></span></td>
					<s:submit type="button" value="%{getText('commercial.submit.completion')}" action="insertClassroomCompletion" />
					<s:hidden name="fileNumber" />
					<s:hidden name="classroomPk" />
					<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
					<s:hidden name="studentPk" />
				</s:form></div>
			</s:else>			
		</s:if>
		<s:else>
			<div class="greenTable"><s:form cssClass="noBorders" name="editForm" namespace="/commercial">
				<tr>
					<td>Classroom Training Completion Date (MM/DD/YYYY):</td>
					<td>
						<s:textfield theme="simple" value="%{trainingCompletionDateFormat}" name="trainingCompletionDate" id="trainingCompletionDate" cssClass="datepicker" maxlength="10"/>
					</td>
				</tr>
				<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionDate_userid"/> <s:property value="%{classroomCompletionDate_datestampFormat}"/></span></td>
				<s:submit type="button" value="%{getText('commercial.submit.completion')}" action="insertClassroomCompletion" />
				<s:hidden name="fileNumber" />
				<s:hidden name="classroomPk" />
				<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
				<s:hidden name="studentPk" />
			</s:form></div>
		</s:else>
	</s:if>
	
	<s:form namespace="/commercial"
		onsubmit="return validateCommercialTimes('training')"
		action="singleInsertTraining">
		<s:token />
		<tr>
			<td colspan="2">
			<table class="noBorders">
				<tr>
					<th style="text-align: center;">Date</th>
					<th style="text-align: center;">Start Time</th>
					<th style="text-align: center;">End Time</th>
					<th style="text-align: center;">Class</th>
					<th style="text-align: center;">Instructor</th>
					<th style="text-align: center;">Branch</th>
				</tr>
				<tr>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:textfield name="trainingDate" id="trainingDate" cssClass="datepicker" maxlength="10"/>
					</table>
					</td>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:textfield maxlength="5" size="5" id="trainingStartTime"
							name="trainingStartTime" />
					</table>
					</td>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:textfield maxlength="5" size="5" id="trainingEndTime"
							name="trainingEndTime" />
					</table>
					</td>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:textfield name="section" size="3" maxlength="3" />
					</table>
					</td>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:select name="instructorFk" list="instructorList"
							listKey="personPk" listValue="fullName" />
					</table>
					</td>
					<td class="standardcell" style="text-align: center;">
					<table>
						<s:select name="branchFk" list="branchList" listKey="branchPk"
							listValue="address1" headerKey="" headerValue="Main Office" />
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<s:submit id="trainingSubmitButton" value="%{getText('submit')}" />
		<s:hidden name="classroomPk" />
		<s:hidden name="studentPk" />
		<s:hidden name="commercialTimes" value="true" />
		<%-- if left as a hidden field struts will not convert this to an array  --%>
		<table class="noBorders">
			<s:select multiple="true" id="studentArray" list="%{studentPk}"
				value="studentPk" name="studentArray" cssStyle="display:none;" />
		</table>
	</s:form> <br />
	<s:if test="trainingList.size > 0">
		<h3><s:text name="commercial.training.list" />: <s:if
			test="trainingHours != 0">
			<s:if test="trainingHours >= 18">
				<span class="actionMessage">Total Training Hours <s:property
					value="%{trainingHours}" /> </span>
			</s:if>
			<s:else>
				<span class="actionError">Total Training Hours <s:property
					value="%{trainingHours}" /></span>
			</s:else>
		</s:if></h3>
		<br />
		<table>
			<tr>
				<th style="text-align: center;">Branch</th>
				<th style="text-align: center;">Class</th>
				<th style="text-align: center;">Start Time</th>
				<th style="text-align: center;">End Time</th>
				<th style="text-align: center;">Instructor</th>
				<th style="text-align: center;">Audit</th>
				<th style="text-align: center;">Delete</th>
				<th style="text-align: center;">Edit</th>
			</tr>
			<s:iterator value="trainingList">
				<tr>
					<td style="text-align: center;"><s:property value="branchName" /></td>
					<td style="text-align: center;"><s:property value="section" /></td>
					<td style="text-align: center;"><s:date name="startTime"
						format="MM/dd/yyyy  hh:mm a" /></td>
					<td style="text-align: center;"><s:date name="endTime"
						format="MM/dd/yyyy  hh:mm a" /></td>
					<td style="text-align: center;"><s:property
						value="instructorFullName" /></td>
					<td style="text-align: center;" class="studentAudit">
						<s:date format="MM/dd/yyyy" name="training_datestamp" id="training_datestampFormat"/>
						<s:if test="training_userid != null">
							<s:property value="training_userid" /> <s:property value="%{training_datestampFormat}" />
						</s:if>
					</td>
					<td style="text-align: center;">
						<logic:present role="ADMIN">
							[<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="singleDeleteTraining-%{timePk}-%{studentPk}-%{classroomPk}" />">X</a>]
						</logic:present>
						<logic:notPresent role="ADMIN">
							<logic:present role="COMMERCIAL">
								NA
							</logic:present>
						</logic:notPresent>
					</td>
					<td style="text-align: center;">
						<logic:present role="ADMIN">
							<a href="<s:url action="singleViewUpdateTraining-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
						</logic:present>
						<logic:notPresent role="ADMIN">
							<logic:present role="COMMERCIAL">
								<s:if test="editable != 0">
									<a href="<s:url action="singleViewUpdateTraining-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
								</s:if>
								<s:else>
									NA
								</s:else>
							</logic:present>
						</logic:notPresent>
					</td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
	</div>

	<s:if test="homeStudy == 1">
		<div id="homestudyForm" style="display: block;"><br />
		<h4><s:text name="Home Study" /></h4>
		<br />
		<div class="greenTable" id="ocsScoreId">
			<s:form cssClass="noBorders" namespace="/commercial"
			onsubmit="return validateOcsScore()"
			action="updateStudentOcsScore">
			<s:token />
			<s:textfield label="Home Study Score" name="ocsScore" id="ocsScore" value="%{currentStudent.ocsScore}" size="3" maxlength="3" />
			<tr>
				<td colspan="2" class="studentAudit"> 
					<s:date format="MM/dd/yyyy" name="currentStudent.ocs_score_datestamp" id="ocs_score_datestampFormat"/>
					<s:property value="currentStudent.ocs_score_userid" /> <s:property value="%{ocs_score_datestampFormat}" />
				</td>
			</tr>
			<s:submit value="Submit" />
			<s:hidden name="classroomPk" />
			<s:hidden name="studentPk" />
		</s:form></div>
		</div>
	</s:if>

	<s:if test="currentStudent.licenseType == 'LRN'">
		<div id="behindTheWheelForm" style="display: block;"><br />
		<h4>Behind The Wheel</h4>
		<br />
		<s:if test="behindTheWheelHours >= 6">
			<s:date format="MM/dd/yyyy" name="btwCompletionDate" id="btwCompletionDateFormat" />
			<s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.btwCompletionDate_datestamp" id="btwCompletionDate_datestampFormat"/>
			<s:if test="fileNumber !=null">
				<s:if test="btwCompletionDate != null">
					<logic:notPresent role="ADMIN">
						<div class="labelFormat">
							<s:label label="%{getText('student.behindWheelCompletionDateNoFormat')}" value="%{btwCompletionDateFormat}" />
							<span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionDate_userid"/> <s:property value="%{btwCompletionDate_datestampFormat}"/></span>
						</div>									
					</logic:notPresent>
					<logic:present role="ADMIN">
						<div class="greenTable">
							<s:form cssClass="noBorders" name="editForm" namespace="/commercial">
								<tr>
									<td>Behind the Wheel Completion Date (MM/DD/YYYY):</td>
									<td>
										<s:textfield theme="simple" value="%{btwCompletionDateFormat}" name="btwCompletionDate" id="btwCompletionDate" cssClass="datepicker" maxlength="10"/>
									</td>
								</tr>
								<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionDate_userid"/> <s:property value="%{btwCompletionDate_datestampFormat}"/></span></td>
								<s:submit type="button" value="Update Completion Date" action="insertBtwCompletion" />
								<s:hidden name="fileNumber" />
								<s:hidden name="classroomPk" />
								<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
								<s:hidden name="studentPk" />
							</s:form>
						</div>		
					</logic:present>
				</s:if>
				<s:else>
					<div class="greenTable"><s:form cssClass="noBorders" name="editForm" namespace="/commercial">
						<tr>
							<td>Behind the Wheel Completion Date (MM/DD/YYYY):</td>
							<td>
								<s:textfield theme="simple" value="%{btwCompletionDateFormat}" name="btwCompletionDate" id="btwCompletionDate" cssClass="datepicker" maxlength="10"/>
							</td>
						</tr>
						<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionDate_userid"/> <s:property value="%{btwCompletionDate_datestampFormat}"/></span></td>
						<s:submit type="button" value="%{getText('commercial.submit.completion')}" action="insertBtwCompletion" />
						<s:hidden name="fileNumber" />
						<s:hidden name="classroomPk" />
						<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
						<s:hidden name="studentPk" />
					</s:form></div>				
				</s:else>
			</s:if>
			<s:else>
				<div class="greenTable"><s:form cssClass="noBorders" name="editForm" namespace="/commercial">
					<tr>
						<td>Behind the Wheel Completion Date (MM/DD/YYYY):</td>
						<td>
							<s:textfield theme="simple" value="%{btwCompletionDateFormat}" name="btwCompletionDate" id="btwCompletionDate" cssClass="datepicker" maxlength="10"/>
						</td>
					</tr>
					<tr><td colspan="2" style="text-align:right"><span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionDate_userid"/> <s:property value="%{btwCompletionDate_datestampFormat}"/></span></td>
					<s:submit type="button" value="%{getText('commercial.submit.completion')}" action="insertBtwCompletion" />
					<s:hidden name="fileNumber" />
					<s:hidden name="classroomPk" />
					<s:hidden name="studentNumber" value="%{currentStudent.studentNumber}" />
					<s:hidden name="studentPk" />
				</s:form></div>
			</s:else>
		</s:if> 
		<s:form namespace="/commercial"
			onsubmit="return validateCommercialTimes('behindTheWheel')"
			action="singleInsertBtw">
			<s:token />
			<tr>
				<td colspan="2">
				<table class="noBorders">
					<tr>
						<th style="text-align: center;">Date</th>
						<th style="text-align: center;">Start Time</th>
						<th style="text-align: center;">End Time</th>
						<th style="text-align: center;">Instructor</th>
						<th style="text-align: center;">Vehicle</th>
						<th style="text-align: center;">Branch</th>
					</tr>
					<tr>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:textfield name="behindTheWheelDate" id="behindTheWheelDate" cssClass="datepicker" maxlength="10"/>
						</table>
						</td>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:textfield maxlength="5" size="5" id="behindTheWheelStartTime"
								name="behindTheWheelStartTime" />
						</table>
						</td>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:textfield maxlength="5" size="5" id="behindTheWheelEndTime"
								name="behindTheWheelEndTime" />
						</table>
						</td>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:select name="instructorFk" list="instructorList"
								listKey="personPk" listValue="fullName" />
						</table>
						</td>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:select name="vehicleFk" list="vehicleList" listKey="vehiclePk"
								listValue="vehiclePlate" />
						</table>
						</td>
						<td class="standardcell" style="text-align: center;">
						<table>
							<s:select name="branchFk" list="branchList" listKey="branchPk"
								listValue="address1" headerKey="" headerValue="Main Office" />
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<s:submit id="behindTheWheelSubmitButton"
				value="%{getText('submit')}" />
			<s:hidden name="classroomPk" />
			<s:hidden name="studentPk" />
			<s:hidden name="commercialTimes" value="true" />
			<%-- if left as a hidden field struts will not convert this to an array  --%>
			<table class="noBorders">
				<s:select multiple="true" id="studentArray" list="%{studentPk}"
					value="studentPk" name="studentArray" cssStyle="display:none;" />
			</table>
		</s:form> <br />
		<s:if test="behindTheWheelList.size > 0">
			<h3>Behind The Wheel List: <s:if test="behindTheWheelHours != 0">
				<s:if test="behindTheWheelHours >= 6">
					<span class="actionMessage">Total Behind The Wheel Hours <s:property
						value="%{behindTheWheelHours}" /> </span>
				</s:if>
				<s:else>
					<span class="actionError">Total Behind The Wheel Hours <s:property
						value="%{behindTheWheelHours}" /></span>
				</s:else>
			</s:if></h3>
			<br />
			<table>
				<tr>
					<th style="text-align: center;">Branch</th>
					<th style="text-align: center;">Start Time</th>
					<th style="text-align: center;">End Time</th>
					<th style="text-align: center;">Instructor</th>
					<th style="text-align: center;">Vehicle</th>
					<th style="text-align: center;">Audit</th>
					<th style="text-align: center;">Delete</th>
					<th style="text-align: center;">Edit</th>				
				</tr>
				<s:iterator value="behindTheWheelList">
					<tr>
						<td style="text-align: center;"><s:property
							value="branchName" /></td>
						<td style="text-align: center;"><s:date name="startTime"
							format="MM/dd/yyyy  hh:mm a" /></td>
						<td style="text-align: center;"><s:date name="endTime"
							format="MM/dd/yyyy  hh:mm a" /></td>
						<td style="text-align: center;"><s:property
							value="instructorFullName" /></td>
						<td style="text-align: center;"><s:property
							value="vehicleFullDesc" /></td>
						<td style="text-align: center;" class="studentAudit">
							<s:date format="MM/dd/yyyy" name="btw_datestamp" id="btw_datestampFormat"/>
							<s:if test="btw_userid != null">
								<s:property value="btw_userid" /> <s:property value="%{btw_datestampFormat}" />
							</s:if>
						</td>
						<td style="text-align: center;">
							<logic:present role="ADMIN">
								[<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="singleDeleteBTW-%{timePk}-%{studentPk}-%{classroomPk}" />">X</a>]
							</logic:present>
							<logic:notPresent role="ADMIN">
								<logic:present role="COMMERCIAL">
									NA
								</logic:present>
							</logic:notPresent>
						</td>
						<td style="text-align: center;">
							<logic:present role="ADMIN">
								<a href="<s:url action="singleViewUpdateBTW-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
							</logic:present>
							<logic:notPresent role="ADMIN">
								<logic:present role="COMMERCIAL">
									<s:if test="editable != 0">
										<a href="<s:url action="singleViewUpdateBTW-%{timePk}-%{studentPk}-%{classroomPk}" />">EDIT</a>
									</s:if>
									<s:else>
										NA
									</s:else>
								</logic:present>
							</logic:notPresent>
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if></div>
	</s:if>

</logic:notPresent>

<button id="show1" style="display: none"></button>

<!-- Remdy ticket 580768  -->
<div id="notesForm" style="display: block;"><br />
<h4><s:text name="Notes" /></h4>
<br />

<div class="greenTable" id="note"><s:form cssClass="noBorders"
	namespace="/commercial" action="updateStudentNote">
	<s:token />
	<s:textarea name="note" value="%{currentStudent.note}" id="noteId" cols="120" rows="20"  onkeydown="textCounter(this.id,'counterId',1000);" onkeyup="textCounter(this.id,'counterId',1000);" />
	<b><span id="counterId">1000</span></b> Remaining Characters
	<br /><br />
	<s:submit value="Submit" />
	<s:hidden name="classroomPk" />
	<s:hidden name="studentPk" />
</s:form></div>

</div>

<script type="text/javascript">
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
	$("#observationStartTime").mask("99:99");
	$("#observationEndTime").mask("99:99");
	$("#trainingStartTime").mask("99:99");
	$("#trainingEndTime").mask("99:99");
	$("#behindTheWheelStartTime").mask("99:99");
	$("#behindTheWheelEndTime").mask("99:99");
	
	showInitCount('noteId','counterId',1000);
</script>

</body>
</html>
