<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<head>
    <title><s:text name="report.completionTestsReport"/></title>

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
                                                var site = '<s:url namespace="/" action="Home"/>';
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
					 text: "Do you want to return home?",
					 icon: YAHOO.widget.SimpleDialog.ICON_HELP,
					 constraintoviewport: true,
					 buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
					  { text:"No",  handler:handleNo } ]
						 } );
					YAHOO.example.container.simpledialog1.setHeader("Return Home?");
            
                        // Render the Dialog
			YAHOO.example.container.simpledialog1.render(document.body);
                        
                       YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);
                       
                       }
            YAHOO.util.Event.addListener(window, "load", init);
        </script>
</head>
<body>
<br/>
<h4><s:text name="report.completionTestsReport"/></h4>
<br/>
<s:actionerror/>
<s:actionmessage/>

    
    <br/> <br/>
    <s:form action="generateCompletionDatesReport" onsubmit="redirectWindow(this)"> 
        <s:date format="MM/dd/yyyy" name="reportStartDate" id="reportStartDateFormat"/>
        <s:textfield label="%{getText('report.startDate')}" value="%{reportStartDateFormat}" name="reportStartDate" id="reportStartDate" cssClass="datepicker" maxlength="10"/>
        
        <s:date format="MM/dd/yyyy" name="reportEndDate" id="reportEndDateFormat"/>
        <s:textfield label="%{getText('report.endDate')}" value="%{reportEndDateFormat}" name="reportEndDate" id="reportEndDate" cssClass="datepicker" maxlength="10"/>

        <logic:present role="ADMIN">
        <s:select emptyOption="true" label="%{getText('student.schoolName')}" name="schoolFk" list="highSchoolListAll" listKey="schoolPk" listValue="schoolName"/>
        </logic:present>
        <logic:notPresent role="ADMIN">
        <s:select label="%{getText('student.schoolName')}" name="schoolFk" list="highSchoolList" listKey="schoolPk" listValue="schoolName"/>
        </logic:notPresent>
        <s:submit value="%{getText('student.submit')}"  onclick="return updateDateField('reportStartDate,reportEndDate');" />
    </s:form>

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
	</script>

</body>

</html>