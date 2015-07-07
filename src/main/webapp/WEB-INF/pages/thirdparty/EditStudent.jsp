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
            
            function validate() {
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

            function editClicked(index) {
				var site = '/sdc/thirdparty/updatechange.do';
				site = site.slice(0,site.length - 3);
				site = site + '_' + document.forms[0].studentPk.value + '_' + index + '.do';
				window.location.href=site;
            }

            function deleteClicked(completionRoadPk, studentFk) {
            	if (!confirm("Are you sure you want to delete this record?")) {
					return false;
            	}    
				var site = '/sdc/thirdparty/updatedelete.do';
				site = site.slice(0,site.length - 3);
				site = site + '_' + completionRoadPk + '_' + studentFk + '.do';
				window.location.href=site;
          	}
            
        </script>    
        
    </head>
    <body>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        
        <h1><s:text name="thirdparty.student.header"/></h1>
        <br/>
        <logic:notPresent role="GUEST">
        <s:form name="editForm" namespace="/thirdparty" action="update"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
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
        
        <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="roadSearchSchoolList" listKey="schoolPk" listValue="schoolName" />
        
        <s:if test="currentStudent.licenseType == 'LRN' || currentStudent.licenseType == 'TPT'">
        <s:if test="currentStudent.roadTestCompletionDate == null">
	        <s:hidden name="currentStudent.roadTestCompletionDate" />
	        <s:date format="MM/dd/yyyy" name="roadTestCompletionDate" id="roadTestCompletionDateFormat"/>
	        <s:textfield label="%{getText('student.roadTestCompletionDate')}" value="%{roadTestCompletionDateFormat}" name="roadTestCompletionDate" id="roadTestCompletionDate" cssClass="datepicker" maxlength="10"/>

	        
	        <s:textfield label="%{getText('thirdparty.roadTestStartTime')}" maxlength="5" size="5" id="roadTestStartTime" name="roadTestStartTime" required="true"/>
	        <s:textfield label="%{getText('thirdparty.roadTestEndTime')}" maxlength="5" size="5" id="roadTestEndTime" name="roadTestEndTime" required="true"/>
	        <s:textfield id="roadTestScore" label="%{getText('student.roadScore')}" name="roadTestScore" size="2" maxlength="3" tooltip="Road Score should be between 0 - 20.  This should not be a percentage, it is a points reduction score"  required="true"/>
	        <s:textfield id="routeNumber" label="%{getText('thirdparty.routeNumber')}" name="routeNumber" size="2" maxlength="2" tooltip="Two digit route number"  required="true"/>
	        <s:textfield id="routeArea" label="%{getText('thirdparty.routeArea')}" name="routeArea" maxlength="45" tooltip="City or area of road test" required="true"/>
	        <s:select label="%{getText('student.roadTestInstructor')}" name="roadInstructorPk" list="roadInstructorList" listKey="personPk" listValue="fullName" required="true"/>
	        <s:select label="%{getText('thirdparty.vehicle')}" name="vehicleFk" list="roadVehicleList" listKey="vehiclePk" listValue="vehiclePlate" required="true"/>    
	        <s:submit type="button" value="%{getText('student.update')}" onclick="return validate();" action="update" />
        </s:if>
        <s:else>
        	<s:submit type="button" value="%{getText('student.generateFullCompletion')}" id="generateRoadTestPDF" onclick="return dialog();" action="generateRoadTestPDF" />
        </s:else>
        <tr>
            <td colspan=2>
                <div id="roadTestList">
                    <s:if test="roadTestList.size > 0"> 
                    <h3>
                    Road Test List:</h3>
                    <br/>
                    <table>
                        <tr>
                            <th style="text-align:center;">Completion Date</th>
                            <th style="text-align:center;">Start Time</th>
                            <th style="text-align:center;">End Time</th>
                            <th style="text-align:center;">Road Score</th>
                            <th style="text-align:center;">Route Number</th>
                            <th style="text-align:center;">Route Area</th>
                            <th style="text-align:center;">Instructor</th>
                            <th style="text-align:center;">Vehicle</th>
                            <th style="text-align:center;">School</th>
                            <th style="text-align:center;">Audit</th>
                            <logic:present role="ADMIN">
                            <th style="text-align:center;">&nbsp;</th>
                            </logic:present>
                        </tr>
                        <s:iterator value="roadTestList" status="rowstatus">
                        <tr>
                            <td style="text-align:center;">
                                <s:date name="completionDate" format="MM/dd/yyyy" />
                            </td>
                            <td style="text-align:center;">
                                <s:date name="startTime" format="hh:mm a" />
                            </td>
                            <td style="text-align:center;">
                                <s:date name="endTime" format="hh:mm a" />
                            </td>
                            <td style="text-align:center;">
                                <s:if test="roadScore != 101">
                                -<s:property value="roadScore"/>
                                </s:if>
                                <s:else>
                                Pass
                                </s:else>
                            </td>
                            <td style="text-align:center;">
                                <s:if test="routeNumber != null">
                                <s:property value="routeNumber"/>
                                </s:if>
                                <s:else>
                                DL Route
                                </s:else>
                            </td>
                            <td style="text-align:center;">
                                <s:if test="routeArea != null">
                                <s:property value="routeArea"/>
                                </s:if>
                                <s:else>
                                DL Area
                                </s:else>
                            </td>
                            <td style="text-align:center;">
                                <s:property value="instructorFullName"/>
                            </td>
                            <td style="text-align:center;">
                                <s:property value="vehicleFullDesc"/>
                            </td>
                            <td style="text-align:center;">
                                <s:property value="schoolName"/>
                            </td>
                            <td class="studentAudit" style="text-align:center;">
                            	<s:date format="MM/dd/yyyy" name="road_datestamp" id="road_datestampFormat"/>
                                <s:property value="road_userid"/> <s:property value="%{road_datestampFormat}"/>
                            </td>
                            <td style="text-align:center;">
                                <input type="button" id="edit" onclick="editClicked(<s:property value="#rowstatus.index" />)" value="Edit"/>
                                <logic:present role="ADMIN">
                                	<input type="button" id="delete" onclick="return deleteClicked(<s:property value="timePk" />, <s:property value="studentFk" />)" value="Delete"/>
                                </logic:present>
                            </td>
                            
                        </tr>
                        </s:iterator>
                    </table>
                    </s:if>
                </div>
            </td>
        </tr>
        </s:if>
        <s:else>
        <tr>
            <td class="noBorders" colspan=2 style="text-align:center;">
                <table style="margin-left:auto;margin-right:auto;">
                    <tr>
                        <td style="text-align:center;">
                            <br/>
                            <span class="actionError">Learner Permit Not Present</span>
                            <br/><br/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        </s:else>
        <s:hidden name="studentPk" value="%{currentStudent.studentPk}"/>
        <s:hidden name="licenseType" value="%{currentStudent.licenseType}"/>
        <s:hidden name="currentStudent.licenseType" value="%{currentStudent.licenseType}"/>
        <s:hidden name="currentStudent.studentPk" value="%{currentStudent.studentPk}"/>
        </s:form>
        </logic:notPresent>
        <button id="show1" style="display:none"></button> 
        
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
			$("#roadTestStartTime").mask("99:99");
			$("#roadTestEndTime").mask("99:99");
		</script>

    </body>
    
    
</html>
