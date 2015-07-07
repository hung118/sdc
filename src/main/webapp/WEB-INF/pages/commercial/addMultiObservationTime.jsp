<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/struts-dojo-tags" prefix="dojo" %>

<html>
    <head>
        <title><s:text name="commercial.observation.header"/></title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
       
		<script type="text/javascript">
			function checkEach() {
				var els=document.getElementsByTagName('input');
				var total = 0;
				var totalSelected = 0;
				for(var i=0; i<els.length; i++) {
					if(els[i].name && els[i].name.match('studentArray')) {
						total++;
						if (els[i].checked == true) {
							totalSelected++;
						}
					}
				}
				if (totalSelected == 0) {
					$("#checkAllId").attr("style", "color:red");
					$("#checkAllId").text("Error: At least 1 student must be selected.");
					$("#submitButton").removeAttr("disabled");									
				} else if (totalSelected > 3) {
					$("#checkAllId").attr("style", "color:red");
					$("#checkAllId").text("Error: No more than 3 students are allowed for observation.");
					$("#submitButton").attr("disabled", "true");					
				} else {
					$("#checkAllId").attr("style", "color:blue");
					$("#checkAllId").text(totalSelected + " of " + total + " were selected for this observation.");
					$("#submitButton").removeAttr("disabled");
				}
			}
			
			function observationValidate() {
				if ($("#observationDate").val() == "") {
					$("#validationError").text("Observation Date is missing.");
					return false;
				} else if ($("#observationStartTime").val() == "") {
					$("#validationError").text("Start Time is missing.");
					return false;					
				} else if ($("#observationEndTime").val() == "") {
					$("#validationError").text("End Time is missing.");
					return false;					
				} else if (validateCommercialTimes('observation')) {
		    		if (confirm("Are you sure you want to save this class with these students?")) {
		    			return true;
		    		} else {
		    			return false;
		    		}
		    	} else {
		    		return false;
		    	}
			}
			
		</script>
    </head>
    <body>
        <br/>
        <h4>SDC Commercial Observation for <s:label name="currentClassroom.getSchoolName()" /> - <s:label name="currentClassroom.getAlias()" /></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editClassroom-">
            <s:param name="classroomPk" value="classroomPk"/>
        </s:url>
        <s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To <s:text name="commercial.classroom"/>" /></s:a>
        </h2>
        <br/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="addMultiObservationTime" tooltipDelay="500" tooltipIconPath="/images/information.png">
                <s:token />
                <tr><td colspan="2"><span id="validationError" style="color:red"></span></td></tr>
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Date</th>
                                <th style="text-align:center;">Start Time</th>
                                <th style="text-align:center;">End Time</th>
                                <th style="text-align:center;">Instructor</th>
                                <th style="text-align:center;">Vehicle</th>
                                <th style="text-align:center;">Branch</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
	                                    <s:date format="MM/dd/yyyy" name="observationDate" id="observationDateFormat"/>
	                                    <s:textfield value="%{observationDateFormat}" name="observationDate" id="observationDate" cssClass="datepicker" maxlength="10"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="observationStartTime" name="observationStartTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;" >
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="observationEndTime" name="observationEndTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="instructorFk" list="instructorList" listKey="personPk" listValue="fullName" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="vehicleFk" list="vehicleList" listKey="vehiclePk" listValue="vehiclePlate" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                                <table>
                                                    <s:select name="branchFk" list="branchList" listKey="branchPk" listValue="address1" headerKey="" headerValue="Main Office" />
                                                </table>
                                        </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                	<td>
                		&nbsp;
                	</td>
                	<td>
                		<span id="checkAllId" style="color:blue">0 of <s:property value="studentList.size()"/> were selected for this observation class.</span>
                	</td>
                </tr>
                <s:checkboxlist id="observationArray" tooltip="Choose Up To 3 Students" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="nameDob" name="studentArray" onclick="checkEach()" />
                
                <s:submit id="submitButton" disabled="true" onclick="return observationValidate()" value="%{getText('submit')}" />
                <s:hidden name="classroomPk"/>
            </s:form>
        </div>
        
        <br/>
        
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
		</script>
    </body>
    
</html>