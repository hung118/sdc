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
			var site = '<s:url namespace="/" action="Home"/>';
			window.location.href=site;
		};
		var handleNo = function() {
			this.hide();
			var d = document.forms[0];
			d.target = "";
			document.getElementById("createStudent2").click();
		};
	
		// Instantiate the Dialog
		YAHOO.example.container.simpledialog1 = new YAHOO.widget.SimpleDialog("simpledialog1", 
			{	width: "300px",
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
	
	var actionStr = "";
	var req;
	function update(action){
		if (action == "generateWrittenTest") {
			// check required field dob and writtenTestCompletionDate, StudentCreateAction-generateWrittenTest-validation.xml doesn't work
			if (document.getElementById("dob").value == '' || document.getElementById("writtenTestCompletionDate").value == '') {
				alert("Date of Birth and Written Test Completion Date are required.");
				return false;
			}
		} else {	// createSpecialStudent
			if (document.getElementById("dob").value == '') {
				alert("Date of Birth is required.");
				return false;
			}
		}
		
		// ajax call to check for existing student.
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} else {
			document.write("XMLHttpRequest not supported"); 
		}
		
		var firstName = document.getElementById("firstName");
		var lastName = document.getElementById("lastName");
		var dob = document.getElementById("dob");
		var schoolFk = document.getElementById("schoolFk");
		actionStr = action;
		
		<s:url action="ajaxCheckUser" id="ajaxCheckUserID"/>
		var url = "<s:property value='%{ajaxCheckUserID}'/>" + "?firstName=" + firstName.value +
			"&lastName=" + lastName.value + "&dob=" + dob.value + "&schoolFk=" + schoolFk.options[schoolFk.selectedIndex].value;
		//alert("url: " + url);
		req.open("GET", url, false);
		
		if (navigator.appName == "Microsoft Internet Explorer") {
			req.onreadystatechange = callback;
			req.send(null);
		} else {		// FireFox
			req.send(null);
			req.onreadystatechange = callback();		
		}
	}
	
	function callback() {
		if (req.readyState == 4) {
			var serverResponse = req.responseXML.getElementsByTagName("message");
			// alert("return length/value: " + serverResponse.length + " / " + serverResponse[0].childNodes[0].nodeValue);
			
			if (serverResponse[0].childNodes[0].nodeValue == "dob_error") {
				alert("Invalid Date of Birth.");
				return false;
			}
			
			if (serverResponse[0].childNodes[0].nodeValue == "Yes") {
				if (confirm("Student already exists in the school. Do you want to add?")) {
					return doUpdate();
				} else {
					return false;
				}
			} else {
				return doUpdate();
			}	
		}	
	}
	
	function doUpdate() {
		var d = document.forms[0];
		if (actionStr == "generateWrittenTest") {			
			<s:url action="generateWrittenTest" id="submitAction1"/>
			d.action = "<s:property value='%{submitAction1}'/>";
			d.target = "_blank";
			document.getElementById("show1").click();
			return true;
		} else {
			document.getElementById("writtenTestScore").value = -1;	// dummy val to pass validation
			<s:url action="createSpecialStudent" id="submitAction2"/>
			d.action = "<s:property value='%{submitAction2}'/>";
			return true;
		}
	}
	
	function update2() {
		// this is called when actionStr = generateWrittenTest in doUpdate by click No on "Return to Home" popup.
		var d = document.forms[0];
		<s:url action="createSpecialStudent" id="submitAction3"/>
		d.action = "<s:property value='%{submitAction3}'/>";
		return true;
	}
	
	function checkVal(checkId){
		var checkbox = document.getElementById(checkId);
		var writtenScoreChild = document.getElementById("writtenTestScore");
		var writtenScoreParent = writtenScoreChild.parentNode.parentNode;
	
		var writtenDateChild = document.getElementById("writtenTestCompletionDate");
		var writtenDateParent = writtenDateChild.parentNode.parentNode;
	
		var observationDateChild = document.getElementById("observationCompletionDate");
		var observationDateParent = observationDateChild.parentNode.parentNode;
	
		var classroomDateChild = document.getElementById("classroomCompletionDate");
		var classroomDateParent = classroomDateChild.parentNode.parentNode;
	
		var generateButton = document.getElementById("generateWrittenTest").parentNode.parentNode;
		var createButton = document.getElementById("createStudent").parentNode.parentNode;
	
		var createButton2 = document.getElementById("createStudent2").parentNode.parentNode;
		createButton2.style.display = "none";
		
		if (checkbox.checked) {
			// remove common add extra
			writtenScoreParent.style.display = "none";
			writtenDateParent.style.display = "none";
			generateButton.style.display = "none";
			observationDateParent.style.display = "";
			classroomDateParent.style.display = "";
			createButton.style.display = "";
		} else {
			// add common remove extra
			writtenScoreParent.style.display = "";
			writtenDateParent.style.display = "";
			generateButton.style.display = "";
			createButton.style.display = "none";
			observationDateParent.style.display = "none";
			classroomDateParent.style.display = "none";
		}
	}
	
	</script> 
        
</head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="student.header"/></h1>
        <br/>
        <s:form name="editForm" namespace="/student" action="create_input.do"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <tr>
                <td style="text-align:right">
                 <input type="checkbox" name="ocsCheck" id="ocsCheck" onclick="checkVal(this.id)" />
             </td>
             <td>
                 <label for="ocsCheck">If No Written Test Check Here.</label>
             </td>
         </tr> 
            <s:textfield label="%{getText('student.firstName')}" name="currentStudent.firstName" id="firstName" required="true"/>
            <s:textfield label="%{getText('student.middleName')}" name="currentStudent.middleName" />
            <s:textfield label="%{getText('student.lastName')}" name="currentStudent.lastName" id="lastName" required="true"/> 
            <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="dobFormat"/>
            <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="currentStudent.dob" id="dob" cssClass="datepicker" maxlength="10"/>
            
            <s:textfield id="writtenTestScore" label="%{getText('student.writtenTestScore')}" size="3" maxlength="3" name="currentStudent.writtenTestScore" tooltip="Written Test Score should be between 80 - 100.  This is a percentage" required="true" />
            <s:date format="MM/dd/yyyy" name="currentStudent.writtenTestCompletionDate" id="writtenTestCompletionDateFormat"/>
            <s:textfield label="%{getText('student.writtenTestCompletionDate')}" value="%{writtenTestCompletionDateFormat}" name="currentStudent.writtenTestCompletionDate" id="writtenTestCompletionDate" cssClass="datepicker" maxlength="10"/>            
            
            <s:date format="MM/dd/yyyy" name="currentStudent.classroomCompletionDate" id="classroomCompletionDateFormat"/>
            <s:textfield label="%{getText('student.classroomCompletionDate')}" value="%{classroomCompletionDateFormat}" name="currentStudent.classroomCompletionDate" id="classroomCompletionDate" cssClass="datepicker" maxlength="10"/>            
    
            <s:date format="MM/dd/yyyy" name="currentStudent.observationCompletionDate" id="observationCompletionDateFormat"/>
            <s:textfield label="%{getText('student.observationCompletionDate')}" value="%{observationCompletionDateFormat}" name="currentStudent.observationCompletionDate" id="observationCompletionDate" cssClass="datepicker" maxlength="10"/>            

            <logic:present role="ADMIN">
                <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="highSchoolListAll" listKey="schoolPk" listValue="schoolName" id="schoolFk"/>
            </logic:present>
            <logic:notPresent role="ADMIN">
                <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="highSchoolList" listKey="schoolPk" listValue="schoolName" id="schoolFk"/>
            </logic:notPresent>
            
            <s:submit value="%{getText('student.insert')}" id="createStudent" onclick="return update('createSpecialStudent');" />
            <s:submit value="%{getText('student.insert')}" id="createStudent2" onclick="return update2();" />
            <s:submit value="%{getText('student.generateWrittenTest')}" id="generateWrittenTest" onclick="return update('generateWrittenTest');" />

            <s:hidden name="currentStudent.fileNumber"/>
        </s:form>
        
        <button id="show1" style="display:none"/> 
        
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
    	
            checkVal('ocsCheck')
        </script> 
    </body>
    
    
</html>
