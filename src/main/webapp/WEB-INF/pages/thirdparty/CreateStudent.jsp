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
                                                var site = '<s:url namespace="/thirdparty" action="Home"/>';
                                                window.location.href=site;
					};
					var handleNo = function() {
						this.hide();
                                                var d = document.forms[0];
                                                d.target = "";
                                                document.getElementById("createStudent").click();
					};

					// Instantiate the Dialog
					YAHOO.example.container.simpledialog1 = new YAHOO.widget.SimpleDialog("simpledialog1", 
					{ width: "300px",
					 fixedcenter: true,
					 visible: false,
                                         modal:true,
					 draggable: false,
					 close: true,
					 text: "Do you want to return home?",
					 icon: YAHOO.widget.SimpleDialog.ICON_HELP,
					 constraintoviewport: true,
					 buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
					  { text:"No",  handler:handleNo } ]
						 } );
					YAHOO.example.container.simpledialog1.setHeader("Return to Home?");
            
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
            
        </script> 
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="thirdparty.student.header"/></h1>
        <br/>
        <s:form name="editForm" namespace="student" action="create_input.do"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <s:textfield label="%{getText('student.firstName')}" name="firstName" required="true"/>
            <s:textfield label="%{getText('student.middleName')}" name="middleName" />
            <s:textfield label="%{getText('student.lastName')}" name="lastName" required="true"/> 

            <s:date format="MM/dd/yyyy" name="dob" id="dobFormat"/>
            <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="dob" id="dob" cssClass="datepicker" maxlength="10"/>
            
            <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="roadSearchSchoolList" listKey="schoolPk" listValue="schoolName"/>
                
            <s:date format="MM/dd/yyyy" name="roadTestCompletionDate" id="roadTestCompletionDateFormat"/>
            <s:textfield label="%{getText('student.roadTestCompletionDate')}" value="%{roadTestCompletionDateFormat}" name="roadTestCompletionDate" id="roadTestCompletionDate" cssClass="datepicker" maxlength="10"/>

            <s:textfield label="%{getText('thirdparty.roadTestStartTime')}" maxlength="5" size="5" id="roadTestStartTime" name="roadTestStartTime" required="true"/>
            <s:textfield label="%{getText('thirdparty.roadTestEndTime')}" maxlength="5" size="5" id="roadTestEndTime" name="roadTestEndTime" required="true"/>
            <s:textfield id="roadTestScore" label="%{getText('student.roadScore')}" name="roadTestScore" size="2" maxlength="3" tooltip="Road Score should be between 0 - 20.  This should not be a percentage, it is a points reduction score"  required="true"/>
            <s:textfield id="routeNumber" label="%{getText('thirdparty.routeNumber')}" name="routeNumber" size="2" maxlength="2" tooltip="Two digit route number"  required="true"/>
            <s:textfield id="routeArea" label="%{getText('thirdparty.routeArea')}" name="routeArea" maxlength="45" tooltip="City or area of road test" required="true"/>
            <s:select label="%{getText('student.roadTestInstructor')}" name="roadInstructorPk" list="roadInstructorList" listKey="personPk" listValue="fullName" required="true"/>
            <s:select label="%{getText('thirdparty.vehicle')}" name="vehicleFk" list="roadVehicleList" listKey="vehiclePk" listValue="vehiclePlate" required="true"/>    
            
            <s:submit value="%{getText('thirdparty.generateRoadTest')}" id="generateRoadTest" action="generateRoadTest" onclick="return validate();" />
            <s:hidden name="currentStudent.fileNumber"/>
        </s:form>
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
