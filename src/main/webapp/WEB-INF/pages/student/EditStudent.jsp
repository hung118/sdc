<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="student.title"/></title>
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
                    var site = '<s:url namespace="/" action="highSchoolFront"/>';
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
                    text: "Do you want to start over?",
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
                
            function editRoadCheck(){
                var a=document.getElementById('roadCheck');
                var b=document.getElementById('editRoad');
                if (a.checked == true) {
                    if(b.style.display == "none") { 
                        showNews('editRoad');
                    }    
                } else {
                    if(b.style.display =="block") {
                        showNews('editRoad');
                    }
                }    
            }            

            function update(action) {
                var d = document.forms[0];
                if (validate()) {
                    if (action == "update") {
                        // redmine 9841
                        if ("<s:property value="currentStudent.fileNumber" />" == "") {
                            alert("This student has an empty file number.\nPlease contact DLD for assistance.");
                            return false;
                        }
				
            <s:url action="update" id="submitAction1"/>
                            d.action = "<s:property value='%{submitAction1}'/>";
                            d.target = "_self";
                        } else if (action == "generateFullCompletion") {
            <s:url action="generateFullCompletion" id="submitAction2"/>
                            d.action = "<s:property value='%{submitAction2}'/>";
                            d.target = "_blank";
                            document.getElementById("show1").click();
                        } else if (action == "generateOcsFullCompletion") {
            <s:url action="generateOcsFullCompletion" id="submitAction3"/>
                            d.action = "<s:property value='%{submitAction3}'/>";
                            d.target = "_blank";
                            if (validateWritten()) {
                                document.getElementById("show1").click();
                            } else {
                                return false;
                            }
                        } else {
            <s:url action="generateWrittenPDF" id="submitAction4"/>
                            d.action = "<s:property value='%{submitAction4}'/>";
                            d.target = "_blank";
                            document.getElementById("show1").click();
                        }                        
                        return noFutureDates();
                    } else {
                        return false;
                    }
            }        

            function validateWritten() {
                var retVal = true;
                var wdate = document.getElementById("writtenTestCompletionDate");
                var wscore = document.getElementById("writtenTestScore");

                if (wdate != null) {
                    if (wdate.value != "") {
                        if (wscore.value == "") {
                            alert("Written Score Cannot Be Blank!");
                            retVal = false;
                        }
                    }
                }
                return retVal;
            }
                
            function noFutureDates() {
                var now = new Date();
                var today   = (now.getMonth() + 1) + "/" + now.getDate() + "/" + now.getFullYear();
                
                if (document.getElementById("writtenTestCompletionDate") != null) {
                    var date = new Date (document.getElementById("writtenTestCompletionDate").value);
             
                    if (compareDates((date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear(), today) == 1) {
                        alert("The Written Test Completion Date cannot be greater than Today's Date of " + today);
                        return false;
                    }
                } 
                if (document.getElementById("classroomCompletionDate") != null) {
                    var date = new Date (document.getElementById("classroomCompletionDate").value);
             
                    if (compareDates((date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear(), today) == 1) {
                        alert("The Classroom Completion Date cannot be greater than Today's Date of " + today);
                        return false;
                    }
                }             
                if (document.getElementById("observationCompletionDate") != null) {
                    var date = new Date (document.getElementById("observationCompletionDate").value);
                
                    if (compareDates((date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear(), today) == 1) {
                        alert("The Observation Completion Date cannot be greater than Today's Date of " + today);
                        return false;
                    }
                } 
                if (document.getElementById("behindWheelCompletionDate") != null) {
                    var date = new Date (document.getElementById("behindWheelCompletionDate").value);
                
                    if (compareDates((date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear(), today) == 1) {
                        alert("The Behind The Wheel Completion Date cannot be greater than Today's Date of " + today);
                        return false;
                    }
                } 
                if (document.getElementById("roadTestCompletionDate") != null) {
                    var date = new Date (document.getElementById("roadTestCompletionDate").value);

                    if (compareDates((date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear(), today) == 1) {
                        alert("The Road Test Completion Date cannot be greater than Today's Date of " + today);
                        return false;
                    }
                }             
            }
            
            function validate() {
                var retVal = true;
                var rdate = document.getElementById("roadTestCompletionDate");
                var rscore = document.getElementById("roadTestScore");
                if (rdate != null){
                    if (rdate.value != ""){
                        if (rscore.value == ""){
                            alert("Road Score Cannot Be Blank!");
                            retVal = false;
                        }
                    }
                }    
                return retVal;
            }

            function disableFields() {
                retVal = false;
                var d = document.forms[0];
                if (d.roadCheck != null) {
                    if (d.roadCheck.checked == false) {
                        d.elements['currentStudent.roadTestCompletionDate'].disabled = true;
                        d.elements['currentStudent.roadTestScore'].disabled = true;
                        d.elements['currentStudent.roadTestInstructorFk'].disabled = true;
                    } else {
                        d.elements['currentStudent.roadTestScore'].disabled = false;
                    }
                }
                retVal = true;
                return retVal;
            }
            function format(dt) {
                if (YAHOO.lang.isString(dt)) {
                    var parts = dt.split("/");
                    var month = parts[0];
                    var day = parts[1];
                    var year = parts[2];
                    return month + "/" + day + "/" + year;
                } else {
                    var month = dt.getMonth();
                    var day = dt.getDate();
                    var year = dt.getFullYear();
                    return month + "/" + day + "/" + year;
                }
            }
            
            function setAuditFields(field) {
            	$("#" + field + "_userid").val("${authPerson.email}");
            	$("#" + field + "_datestamp").val($.datepicker.formatDate('mm/dd/yy', new Date()));
            }
            
        </script>    

    </head>
    <body>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>

        <h1><s:text name="student.header"/></h1>
        <br/>
        <logic:notPresent role="GUEST">
            <div class="wideTable">
                <s:form name="editForm" namespace="student" action="create_input.do"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
                    <s:label cssClass="actionError" label="%{getText('student.fileNumber')}" name="currentStudent.fileNumber" />
                    <s:hidden name="currentStudent.fileNumber"/>
                    <s:if test="currentStudent.studentNumber != null">
                        <s:label label="%{getText('student.studentNumber')}" name="currentStudent.studentNumber" />
                    </s:if>
                    <s:if test="currentStudent.lastName == null">
                        <s:textfield label="%{getText('student.firstName')}" name="currentStudent.firstName" required="true"/>
                        <s:textfield label="%{getText('student.lastName')}" name="currentStudent.lastName" required="true"/>
                    </s:if>
                    <s:else>
                        <s:label label="%{getText('student.firstName')}" name="currentStudent.firstName"/>
                        <s:label label="%{getText('student.lastName')}" name="currentStudent.lastName" />
                        <s:hidden name="currentStudent.firstName"/>
                        <s:hidden name="currentStudent.lastName"/>
                    </s:else>
                    
                    <s:if test="currentStudent.dob == null">
                        <s:textfield id="dob" label="%{getText('student.dob')}" name="currentStudent.dob" cssClass="datepicker" maxlength="10"/>
                    </s:if>
                    <s:else>
                        <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="currentStudentDobFormat"/>
                        <s:label label="%{getText('student.dob')}" value="%{currentStudentDobFormat}"/>
                        <s:hidden name="currentStudent.dob"/>
                    </s:else>
                    
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.writtenTestScore_datestamp" id="writtenTestScore_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.writtenTestScore_userid" id="writtenTestScore_userid"/> <s:hidden name="currentStudent.studentAudit.writtenTestScore_datestamp" id="writtenTestScore_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.writtenCompletionDate_datestamp" id="writtenCompletionDate_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.writtenCompletionDate_userid" id="writtenCompletionDate_userid"/> <s:hidden name="currentStudent.studentAudit.writtenCompletionDate_datestamp" id="writtenCompletionDate_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.writtenCompletionSchool_datestamp" id="writtenCompletionSchool_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.writtenCompletionSchool_userid" id="writtenCompletionSchool_userid"/> <s:hidden name="currentStudent.studentAudit.writtenCompletionSchool_datestamp" id="writtenCompletionSchool_datestamp"/>
                    <s:if test="currentStudent.writtenTestCompletionDate == null">
                        <s:textfield label="%{getText('student.writtenTestScore')}" size="3" maxlength="3" id="writtenTestScore" name="currentStudent.writtenTestScore" tooltip="Written Test Score should be between 80 - 100.  This is a percentage" required="true" onchange="setAuditFields(this.id)" />
						<s:textfield label="%{getText('student.writtenTestCompletionDate')}" name="currentStudent.writtenTestCompletionDate" id="writtenCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)" />
                        <logic:notPresent role="ADMIN">
                            <s:select label="Written Test Completion School" name="currentStudent.writtenCompletionSchoolNumber" id="writtenCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)" />
                        </logic:notPresent>                        
                        <logic:present role="ADMIN">
                            <s:select label="Written Test Completion School" name="currentStudent.writtenCompletionSchoolNumber" id="writtenCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)" />
                        </logic:present>
                    </s:if>
                    <s:else>
                    	<s:date format="MM/dd/yyyy" name="currentStudent.writtenTestCompletionDate" id="writtenTestCompletionDateFormat"/>
                        <s:if test="currentStudent.writtenTestScore < 80">
                            <s:textfield label="%{getText('student.writtenTestScore')}" size="3" maxlength="3" id="writtenTestScore" name="currentStudent.writtenTestScore" tooltip="Written Test Score should be between 80 - 100.  This is a percentage" required="true" onchange="setAuditFields(this.id)" />
							<s:textfield label="%{getText('student.writtenTestCompletionDate')}" name="currentStudent.writtenTestCompletionDate" id="writtenCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)" />
                            <logic:notPresent role="ADMIN">
                                <s:select label="Written Test Completion School" name="currentStudent.writtenCompletionSchoolNumber" id="writtenCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)" />
                            </logic:notPresent>                            
                            <logic:present role="ADMIN">
                                <s:select label="Written Test Completion School" name="currentStudent.writtenCompletionSchoolNumber" id="writtenCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)" />
                            </logic:present>
                        </s:if>
                        <s:else>
                            <s:if test="currentStudent.writtenTestScore == 101">
                            	<tr>
                            		<td>Written Test Score: </td>
                            		<td>PASS
                            			<span class="studentAudit"><s:property value="currentStudent.studentAudit.writtenTestScore_userid"/> <s:property value="%{writtenTestScore_datestampFormat}"/></span>
                            		</td>
                            	</tr>
                            </s:if>
                            <s:else>
                            	<tr>
                            		<td>Written Test Score: </td>
                            		<td><s:property value="currentStudent.writtenTestScore"/> &nbsp;
                            			<span class="studentAudit"><s:property value="currentStudent.studentAudit.writtenTestScore_userid"/> <s:property value="%{writtenTestScore_datestampFormat}"/></span>
                            		</td>
                            	</tr>
                            </s:else>
                           	<tr>
                           		<td>Written Test Completion Date (MM/DD/YYYY): </td>
                           		<td><s:property value="%{writtenTestCompletionDateFormat}"/> &nbsp;
                           			<span class="studentAudit"><s:property value="currentStudent.studentAudit.writtenCompletionDate_userid"/> <s:property value="%{writtenCompletionDate_datestampFormat}"/></span>
                           		</td>
                           	</tr>
                            <s:if test="currentStudent.writtenTestScore == 101">
	                           	<tr>
	                           		<td>Written Test Completion School: </td>
	                           		<td>DLD &nbsp;
	                           			<span class="studentAudit"><s:property value="currentStudent.studentAudit.writtenCompletionSchool_userid"/> <s:property value="%{writtenCompletionSchool_datestampFormat}"/></span>
	                           		</td>
	                           	</tr>
                            </s:if>
                            <s:else>
	                           	<tr>
	                           		<td>Written Test Completion School: </td>
	                           		<td><s:property value="currentStudent.writtenCompletionSchoolName"/> &nbsp;
	                           			<span class="studentAudit"><s:property value="currentStudent.studentAudit.writtenCompletionSchool_userid"/> <s:property value="%{writtenCompletionSchool_datestampFormat}"/></span>
	                           		</td>
	                           	</tr>
                            </s:else>
                            <logic:notPresent role="ADMIN">
                                <s:hidden name="currentStudent.writtenTestScore"/>
                                <s:hidden name="currentStudent.writtenTestCompletionDate"/>
                                <s:hidden name="currentStudent.writtenCompletionSchoolNumber"/>
                            </logic:notPresent>
                            <logic:present role="ADMIN">
                                <tr>
                                    <td colspan="2">
                                        <table class="noBorders">
                                            <tr>
                                                <td colspan="2">
                                                    Change Written Test Information?
                                                    &nbsp;
                                                    <input type="checkbox" value="true" name="writtenCheck" id="writtenCheck" onclick="javascript:showNews('editWritten');" />
                                                </td>
                                            </tr>
                                        </table>

                                        <div id="editWritten" style="display:none;">
                                            <table class="noBorders" style="background-color:#E5E5E5;width:100%;">
                                                <s:textfield id="writtenTestScore" label="%{getText('student.writtenTestScore')}" name="currentStudent.writtenTestScore" size="2" maxlength="2" tooltip="Written Test Score should be between 80 - 100.  This is a percentage"  required="true" onchange="setAuditFields(this.id)"/>
                                                <s:textfield label="%{getText('student.writtenTestCompletionDate')}" value="%{writtenTestCompletionDateFormat}" name="currentStudent.writtenTestCompletionDate" id="writtenCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                                <s:select label="Written Test Completion School" name="currentStudent.writtenCompletionSchoolNumber" id="writtenCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </logic:present>                            
                        </s:else>
                    </s:else>

                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.classroomCompletionDate_datestamp" id="classroomCompletionDate_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.classroomCompletionDate_userid" id="classroomCompletionDate_userid"/> <s:hidden name="currentStudent.studentAudit.classroomCompletionDate_datestamp" id="classroomCompletionDate_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.classroomCompletionSchool_datestamp" id="classroomCompletionSchool_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.classroomCompletionSchool_userid" id="classroomCompletionSchool_userid"/> <s:hidden name="currentStudent.studentAudit.classroomCompletionSchool_datestamp" id="classroomCompletionSchool_datestamp"/>
                    <s:if test="currentStudent.classroomCompletionDate == null">
                        <s:textfield label="%{getText('student.classroomCompletionDate')}" name="currentStudent.classroomCompletionDate"  id="classroomCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                        <logic:notPresent role="ADMIN">
                            <s:select label="Classroom Completion School" name="currentStudent.classroomCompletionSchoolNumber" id="classroomCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                        </logic:notPresent>                        
                        <logic:present role="ADMIN">
                            <s:select label="Classroom Completion School" name="currentStudent.classroomCompletionSchoolNumber" id="classroomCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                        </logic:present>
                    </s:if>
                    <s:else>
                        <s:date format="MM/dd/yyyy" name="currentStudent.classroomCompletionDate" id="classroomCompletionDateFormat"/>
                       	<tr>
                       		<td>Classroom Completion Date (MM/DD/YYYY): </td>
                       		<td><s:property value="%{classroomCompletionDateFormat}"/> &nbsp;
                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionDate_userid"/> <s:property value="%{classroomCompletionDate_datestampFormat}"/></span>
                       		</td>
                       	</tr>
                       	<tr>
                       		<td>Classroom Completion School: </td>
                       		<td><s:property value="currentStudent.classroomCompletionSchoolName"/> &nbsp;
                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.classroomCompletionSchool_userid"/> <s:property value="%{classroomCompletionSchool_datestampFormat}"/></span>
                       		</td>
                       	</tr>
                        <logic:notPresent role="ADMIN">
                            <s:hidden name="currentStudent.classroomCompletionDate"/>
                            <s:hidden name="currentStudent.classroomCompletionCheck"/>
                            <s:hidden name="currentStudent.classroomCompletionSchoolNumber"/>
                        </logic:notPresent>
                        <logic:present role="ADMIN">
                            <tr>
                                <td colspan="2">
                                    <table class="noBorders">
                                        <tr>
                                            <td colspan="2">
                                                Change Classroom Information?
                                                &nbsp;
                                                <input type="checkbox" value="true" name="classroomCheck" id="classroomCheck" onclick="javascript:showNews('editClassroom');" />
                                            </td>
                                        </tr>
                                    </table>

                                    <div id="editClassroom" style="display:none;">
                                        <table class="noBorders" style="background-color:#E5E5E5;">
                                            <s:textfield label="%{getText('student.classroomCompletionDate')}" value="%{classroomCompletionDateFormat}" name="currentStudent.classroomCompletionDate"  id="classroomCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                            <s:select label="Classroom Completion School" name="currentStudent.classroomCompletionSchoolNumber" id="classroomCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </logic:present>     
                    </s:else>

                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.observationCompletionDate_datestamp" id="observationCompletionDate_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.observationCompletionDate_userid" id="observationCompletionDate_userid"/> <s:hidden name="currentStudent.studentAudit.observationCompletionDate_datestamp" id="observationCompletionDate_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.observationCompletionSchool_datestamp" id="observationCompletionSchool_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.observationCompletionSchool_userid" id="observationCompletionSchool_userid"/> <s:hidden name="currentStudent.studentAudit.observationCompletionSchool_datestamp" id="observationCompletionSchool_datestamp"/>
                    <s:if test="currentStudent.licenseType == 'LRN' || currentStudent.licenseType == 'OCS'">
                        <s:if test="currentStudent.writtenTestCompletionDate != null">
                            <s:if test="currentStudent.observationCompletionDate == null">
                                <s:textfield label="%{getText('student.observationCompletionDate')}" name="currentStudent.observationCompletionDate"  id="observationCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>       
                                <logic:notPresent role="ADMIN">
                                    <s:select label="Observation Completion School" name="currentStudent.observationCompletionSchoolNumber" id="observationCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                </logic:notPresent>                        
                                <logic:present role="ADMIN">
                                    <s:select label="Observation Completion School" name="currentStudent.observationCompletionSchoolNumber" id="observationCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                </logic:present>							
                            </s:if>
                            <s:else>
                                <s:date format="MM/dd/yyyy" name="currentStudent.observationCompletionDate" id="observationCompletionDateFormat"/>                                
		                       	<tr>
		                       		<td>Observation Completion Date (MM/DD/YYYY): </td>
		                       		<td><s:property value="%{observationCompletionDateFormat}"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionDate_userid"/> <s:property value="%{observationCompletionDate_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
		                       	<tr>
		                       		<td>Observation Completion School: </td>
		                       		<td><s:property value="currentStudent.observationCompletionSchoolName"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.observationCompletionSchool_userid"/> <s:property value="%{observationCompletionSchool_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
                                <logic:notPresent role="ADMIN">
                                    <s:hidden name="currentStudent.observationCompletionDate"/>
                                    <s:hidden name="currentStudent.observationCompletionCheck"/>
                                    <s:hidden name="currentStudent.observationCompletionSchoolNumber"/>
                                </logic:notPresent>
                                <logic:present role="ADMIN">
                                    <tr>
                                        <td colspan="2">
                                            <table class="noBorders">
                                                <tr>
                                                    <td colspan="2">
                                                        Change Observation Information?
                                                        &nbsp;
                                                        <input type="checkbox" value="true" name="observationCheck" id="observationCheck" onclick="javascript:showNews('editObservation');" />
                                                    </td>
                                                </tr>
                                            </table>

                                            <div id="editObservation" style="display:none;">
                                                <table class="noBorders" style="background-color:#E5E5E5;">
                                                    <s:textfield label="%{getText('student.observationCompletionDate')}" value="%{observationCompletionDateFormat}" name="currentStudent.observationCompletionDate"  id="observationCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                                    <s:select label="Observation Completion School" name="currentStudent.observationCompletionSchoolNumber" id="observationCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </logic:present>     
                            </s:else>
                        </s:if>    
                    </s:if>      

                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.btwCompletionDate_datestamp" id="btwCompletionDate_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.btwCompletionDate_userid" id="btwCompletionDate_userid"/> <s:hidden name="currentStudent.studentAudit.btwCompletionDate_datestamp" id="btwCompletionDate_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.btwCompletionSchool_datestamp" id="btwCompletionSchool_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.btwCompletionSchool_userid" id="btwCompletionSchool_userid"/> <s:hidden name="currentStudent.studentAudit.btwCompletionSchool_datestamp" id="btwCompletionSchool_datestamp"/>
                    <s:if test="currentStudent.licenseType == 'LRN' || currentStudent.licenseType == 'OCS'">
                        <s:if test="currentStudent.writtenTestCompletionDate != null">
                            <s:if test="currentStudent.behindWheelCompletionDate == null">    
                                <s:textfield label="%{getText('student.behindWheelCompletionDate')}" name="currentStudent.behindWheelCompletionDate" id="btwCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>   
                                <logic:notPresent role="ADMIN">
                                    <s:select label="Behind The Wheel Completion School" name="currentStudent.btwCompletionSchoolNumber" id="btwCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                </logic:notPresent>                        
                                <logic:present role="ADMIN">
                                    <s:select label="Behind The Wheel Completion School" name="currentStudent.btwCompletionSchoolNumber" id="btwCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName"/>
                                </logic:present>	
                            </s:if>
                            <s:else>
                                <s:date format="MM/dd/yyyy" name="currentStudent.behindWheelCompletionDate" id="btwCompletionDateFormat"/>
		                       	<tr>
		                       		<td>Behind The Wheel Completion Date (MM/DD/YYYY): </td>
		                       		<td><s:property value="%{btwCompletionDateFormat}"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionDate_userid"/> <s:property value="%{btwCompletionDate_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
		                       	<tr>
		                       		<td>Behind The Wheel Completion School: </td>
		                       		<td><s:property value="currentStudent.btwCompletionSchoolName"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.btwCompletionSchool_userid"/> <s:property value="%{btwCompletionSchool_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
                                <logic:notPresent role="ADMIN">
                                    <s:hidden name="currentStudent.behindWheelCompletionDate"/>
                                    <s:hidden name="currentStudent.behindWheelCompletionCheck"/>
                                    <s:hidden name="currentStudent.btwCompletionSchoolNumber"/>
                                </logic:notPresent>
                                <logic:present role="ADMIN">
                                    <tr>
                                        <td colspan="2">
                                            <table class="noBorders">
                                                <tr>
                                                    <td colspan="2">
                                                        Change Behind The Wheel Information?
                                                        &nbsp;
                                                        <input type="checkbox" value="true" name="behindWheelCheck" id="behindWheelCheck" onclick="javascript:showNews('editBehindWheel');" />
                                                    </td>
                                                </tr>
                                            </table>

                                            <div id="editBehindWheel" style="display:none;">
                                                <table class="noBorders" style="background-color:#E5E5E5;">
                                                    <s:textfield label="%{getText('student.behindWheelCompletionDate')}" value="%{btwCompletionDateFormat}" name="currentStudent.behindWheelCompletionDate" id="btwCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                                    <s:select label="Behind The Wheel Completion School" name="currentStudent.btwCompletionSchoolNumber" id="btwCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </logic:present>     
                            </s:else>
                        </s:if>
                    </s:if>

                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.roadInstructor_datestamp" id="roadInstructor_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.roadInstructor_userid" id="roadInstructor_userid"/> <s:hidden name="currentStudent.studentAudit.roadInstructor_datestamp" id="roadInstructor_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.roadCompletionDate_datestamp" id="roadCompletionDate_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.roadCompletionDate_userid" id="roadCompletionDate_userid"/> <s:hidden name="currentStudent.studentAudit.roadCompletionDate_datestamp" id="roadCompletionDate_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.roadScore_datestamp" id="roadScore_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.roadScore_userid" id="roadScore_userid"/> <s:hidden name="currentStudent.studentAudit.roadScore_datestamp" id="roadScore_datestamp"/>
                    <s:date format="MM/dd/yyyy" name="currentStudent.studentAudit.roadCompletionSchool_datestamp" id="roadCompletionSchool_datestampFormat"/>
                    <s:hidden name="currentStudent.studentAudit.roadCompletionSchool_userid" id="roadCompletionSchool_userid"/> <s:hidden name="currentStudent.studentAudit.roadCompletionSchool_datestamp" id="roadCompletionSchool_datestamp"/>
                    <s:if test="currentStudent.licenseType == 'LRN' || currentStudent.licenseType == 'OCS'">
                        <s:if test="currentStudent.writtenTestCompletionDate != null">
                            <s:if test="currentStudent.roadTestCompletionDate == null">
                                <s:textfield label="%{getText('student.roadTestCompletionDate')}" name="currentStudent.roadTestCompletionDate" id="roadCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                <s:textfield id="roadScore" label="%{getText('student.roadScore')}" name="currentStudent.roadTestScore" size="2" maxlength="2" tooltip="Road Score should be between 0 - 20.  This should not be a percentage, it is a points reduction score" onchange="setAuditFields(this.id)"/>
                                <s:select label="%{getText('student.roadTestInstructor')}" name="currentStudent.roadTestInstructorFk" id="roadInstructor" list="highSchoolRoadTestInstructorList" listKey="personPk" listValue="fullName" onchange="setAuditFields(this.id)"/>
                                <logic:notPresent role="ADMIN">
                                    <s:select label="Road Test Completion School" name="currentStudent.roadCompletionSchoolNumber" id="roadCompletionSchool" list="highSchoolList2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                </logic:notPresent>                        
                                <logic:present role="ADMIN">
                                    <s:select label="Road Test Completion School" name="currentStudent.roadCompletionSchoolNumber" id="roadCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                </logic:present>
                                <input type="hidden" value="true" name="roadCheck" id="roadCheck"/>
                            </s:if>
                            <s:else>
		                       	<tr>
		                       		<td>Road Test Instructor: </td>
		                       		<td><s:property value="currentRoadTestInstructor"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.roadInstructor_userid"/> <s:property value="%{roadInstructor_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
                                <s:date format="MM/dd/yyyy" name="currentStudent.roadTestCompletionDate" id="roadTestCompletionDateFormat"/>
		                       	<tr>
		                       		<td>Road Test Completion Date (MM/DD/YYYY): </td>
		                       		<td><s:property value="%{roadTestCompletionDateFormat}"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.roadCompletionDate_userid"/> <s:property value="%{roadCompletionDate_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
                                <s:if test="currentStudent.roadTestScore == null">
			                       	<tr>
			                       		<td>Road Score (points missed): </td>
			                       		<td>PASS &nbsp;
			                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.roadScore_userid"/> <s:property value="%{roadScore_datestampFormat}"/></span>
			                       		</td>
			                       	</tr>
                                </s:if>
                                <s:else>
			                       	<tr>
			                       		<td>Road Score (points missed): </td>
			                       		<td><s:property value="currentStudent.roadTestScore"/> &nbsp;
			                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.roadScore_userid"/> <s:property value="%{roadScore_datestampFormat}"/></span>
			                       		</td>
			                       	</tr>
                                </s:else>
		                       	<tr>
		                       		<td>Road Test Completion School: </td>
		                       		<td><s:property value="currentStudent.roadCompletionSchoolName"/> &nbsp;
		                       			<span class="studentAudit"><s:property value="currentStudent.studentAudit.roadCompletionSchool_userid"/> <s:property value="%{roadCompletionSchool_datestampFormat}"/></span>
		                       		</td>
		                       	</tr>
                                <logic:notPresent role="ADMIN">
                                    <s:hidden name="currentRoadTestInstructor"/>
                                    <s:hidden name="currentStudent.roadTestCompletionDate"/>
                                    <s:hidden name="currentStudent.roadTestScore"/>
                                    <s:hidden name="currentStudent.roadCompletionSchoolNumber"/>
                                </logic:notPresent>
                                <logic:present role="ADMIN">
                                    <tr>
                                        <td colspan="2">
                                            <table class="noBorders">
                                                <tr>
                                                    <td colspan="2">
                                                        Change Road Test Information?
                                                        &nbsp;
                                                        <input type="checkbox" value="true" name="roadCheck" id="roadCheck" onclick="javascript:showNews('editRoad');"/>
                                                    </td>
                                                </tr>
                                            </table>

                                            <div id="editRoad" style="display:none;">
                                                <table class="noBorders" style="background-color:#E5E5E5;">
                                                    <s:textfield label="%{getText('student.roadTestCompletionDate')}" value="%{roadTestCompletionDateFormat}" name="currentStudent.roadTestCompletionDate" id="roadCompletionDate" cssClass="datepicker" maxlength="10" onchange="setAuditFields(this.id)"/>
                                                    <s:textfield id="roadScore" label="%{getText('student.roadScore')}" name="currentStudent.roadTestScore" size="2" maxlength="2" tooltip="Road Score should be between 0 - 20.  This should not be a percentage, it is a points reduction score"  required="true" onchange="setAuditFields(this.id)"/>
                                                    <s:select label="%{getText('student.roadTestInstructor')}" name="currentStudent.roadTestInstructorFk" id="roadInstructor" list="highSchoolRoadTestInstructorList" listKey="personPk" listValue="fullName" onchange="setAuditFields(this.id)"/>
                                                    <s:select label="Road Test Completion School" name="currentStudent.roadCompletionSchoolNumber" id="roadCompletionSchool" list="highSchoolListAll2" listKey="schoolNumber" listValue="schoolName" onchange="setAuditFields(this.id)"/>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </logic:present>
                            </s:else>
                        </s:if>
                    </s:if>

                    <logic:present role="ADMIN">
                        <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="highSchoolListAll" listKey="schoolPk" listValue="schoolName"/>
                    </logic:present>
                    <logic:notPresent role="ADMIN">
                        <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="highSchoolList" listKey="schoolPk" listValue="schoolName"/>
                    </logic:notPresent>
                    <logic:present role="ADMIN">
						<s:textarea label="Notes" name="currentStudent.note" id="noteId" cols="45" rows="5"  onkeydown="textCounter(this.id,'counterId',1000);" onkeyup="textCounter(this.id,'counterId',1000);" />
						<tr>
							<td colspan="2" style="text-align:right;">
								<b><span id="counterId">1000</span></b> Remaining Characters
							</td>
						</tr>
                    </logic:present>

                    <s:if test="currentStudent.licenseType == 'OCS'">
                        <s:submit type="button" cssClass="ieSubmit" value="%{getText('student.generateFullCompletion')}" id="generateOcsFullCompletion" onclick="return update('generateOcsFullCompletion');" />
                    </s:if>
                    <s:else>
                        <s:submit type="button" cssClass="ieSubmit" value="%{getText('student.update')}" onclick="return update('update');" />
                        <s:if test="currentStudent.writtenTestScore != 101">
                            <s:submit cssClass="ieSubmit" value="%{getText('student.generateWrittenTest')}" id="generateWrittenPDF" onclick="return update('generateWrittenPDF');" />
                        </s:if>
                        <s:submit type="button" cssClass="ieSubmit" value="%{getText('student.generateFullCompletion')}" id="generateFullCompletion" onclick="return update('generateFullCompletion');" />
                    </s:else>

                    <s:hidden name="currentStudent.studentPk"/>
                    <s:hidden name="currentStudent.licenseType"/>
                </s:form>
            </div>
        </logic:notPresent>
        <s:if test="currentStudent.licenseType == 'LRN' || currentStudent.licenseType == 'OCS'">
            <s:if test="currentStudent.roadTestCompletionDate != null">
                <script type="text/javascript">
                        editRoadCheck();
                </script>
            </s:if>
        </s:if>
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
		</script>
        
        <button id="show1" style="display:none"></button> 
    </body>


</html>
