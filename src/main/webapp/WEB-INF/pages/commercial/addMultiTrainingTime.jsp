<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="commercial.training.header"/></title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
		
		<script type="text/javascript">
			function checkAll() {
				var total = 0;
				var field = document.getElementById("selectAll");
				if(field.checked == true) {
					var els=document.getElementsByTagName('input');
					for(var i=0; i< els.length; i++) {
						if(els[i].name && els[i].name.match('studentArray')) {
							els[i].checked = true;
							total++;
						}
					}
				} else {
					var els=document.getElementsByTagName('input');
					for(var i=0; i<els.length; i++) {
						if(els[i].name && els[i].name.match('studentArray')) {
							if (els[i].checked == true) els[i].checked = false;
							total++;
						}
					}
				}
				if (total == 0) {
					$("#checkAllId").attr("style", "color:red");
					$("#checkAllId").text("Error: At least 1 student must be selected.");
					$("#submitButton").removeAttr("disabled");									
				} else if (total > 30) {
					$("#checkAllId").attr("style", "color:red");
					$("#checkAllId").text("Error: No more than 30 students are allowed for training class.");
					$("#submitButton").attr("disabled", "true");					
				} else {
					$("#checkAllId").attr("style", "color:blue");
					$("#checkAllId").text(total + " of " + total + " were selected for this training class.");
					$("#submitButton").removeAttr("disabled");
				}
			}
			
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
				} else if (totalSelected > 30) {
					$("#checkAllId").attr("style", "color:red");
					$("#checkAllId").text("Error: No more than 30 students are allowed for training class.");
					$("#submitButton").attr("disabled", "true");					
				} else {
					$("#checkAllId").attr("style", "color:blue");
					$("#checkAllId").text(totalSelected + " of " + total + " were selected for this training class.");
					$("#submitButton").removeAttr("disabled");
				}
			}
			
			function trainingValidate() {
				if ($("#trainingDate").val() == "") {
					$("#validationError").text("Training Date is missing.");
					return false;
				} else if ($("#trainingStartTime").val() == "") {
					$("#validationError").text("Start Time is missing.");
					return false;					
				} else if ($("#trainingEndTime").val() == "") {
					$("#validationError").text("End Time is missing.");
					return false;					
				} else if ($("#sectionId").val() == "") {
					$("#validationError").text("Class is missing.");
					return false;					
				} else if (validateCommercialTimes('training')) {
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
        <h4>SDC Commercial Classroom Training for <s:property value="currentClassroom.getSchoolName()" /> - <s:property value="currentClassroom.getAlias()" /></h4>
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
            <s:form namespace="/commercial" action="addMultiTrainingTime" tooltipDelay="500" tooltipIconPath="/images/information.png">
                <s:token />
                <tr><td colspan="2"><span id="validationError" style="color:red"></span></td></tr>
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Date</th>
                                <th style="text-align:center;">Start Time</th>
                                <th style="text-align:center;">End Time</th>
                                <th style="text-align:center;">Class</th>
                                <th style="text-align:center;">Instructor</th>
                                <th style="text-align:center;">Branch</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    	<s:date format="MM/dd/yyyy" name="trainingDate" id="trainingDateFormat"/>
                                    	<s:textfield value="%{trainingDateFormat}" name="trainingDate" id="trainingDate" cssClass="datepicker" maxlength="10"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="trainingStartTime" name="trainingStartTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;" >
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="trainingEndTime" name="trainingEndTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:textfield name="section" id="sectionId" size="3" maxlength="3" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="instructorFk" list="instructorList" listKey="personPk" listValue="fullName" />
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
                		<input type="checkbox" id="selectAll" onClick="checkAll()"/> Select/Unselect All
                	</td>
                	<td>
                		<span id="checkAllId" style="color:blue">0 of <s:property value="studentList.size()"/> were selected for this training class.</span>
                	</td>
                </tr>
                <s:checkboxlist id="trainingArray" tooltip="Choose Up To 30 Students" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="nameDob" name="studentArray" onclick="checkEach()" />
                
                <s:submit id="submitButton" disabled="true" onclick="return trainingValidate()" value="%{getText('submit')}" />
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
			$("#trainingStartTime").mask("99:99");
			$("#trainingEndTime").mask("99:99");
		</script>

    </body>
    
</html>