<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="commercial.studentUpdate.title"/></title>
        
		 <!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
        
        <script type="text/javascript">
result = false;
YAHOO.namespace("example.container");
function init()
{
	// Define various event handlers for Dialog
	var handleYes = function() {
		this.hide();
		document.forms[0].submit();
	};

	var handleNo = function() {
		this.hide();
		result = false;
	};

	// Instantiate the Dialog
	YAHOO.example.container.simpledialog1 = new YAHOO.widget.SimpleDialog(
		"simpledialog1", 
		{
			width: "380px",
			fixedcenter: true,
			visible: false,
			modal:true,
			draggable: false,
			close: true,
			text: "Student already exists in the group. Do you want to update ?",
			icon: YAHOO.widget.SimpleDialog.ICON_HELP,
			constraintoviewport: true,
			buttons: [ 
				{ text:"Yes", handler:handleYes},
				{ text:"No",  handler:handleNo, isDefault:true } 
			]
		} 
	);

	YAHOO.example.container.simpledialog1.setHeader("Dialog");
 	            
	// Render the Dialog
	YAHOO.example.container.simpledialog1.render(document.body);

	YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);                 
}

YAHOO.util.Event.addListener(window, "load", init);
        
function createCommercialStudentValidate()
{
	var firstName = document.getElementById("firstName");
	var lastName = document.getElementById("lastName");
	var dob = document.getElementById("dob");
	var classroomPk = document.getElementById("classroomPk");
	var phoneNotBlank = phoneNotEmpty("phoneNumber");
	var phoneIsValid;
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var msgBlock = "";
    var objName;
	var okToSubmit = true;

	// if firstName, lastName, and dob are not changed, then don't check for existing student
	var firstNameTmp = document.getElementById("firstNameTmpId");
	var lastNameTmp = document.getElementById("lastNameTmpId");
	formatDate("dobTmpId"); 	// to mm/dd/yyyy format

	//Do Phone Number validation
	if (phoneNotBlank) {
		phoneIsValid = validatePhone("phoneNumber");
		if (!phoneIsValid) {
			alertString = alertString + "<BR>" + "Phone Number is incomplete.";
			objName = "phoneNumber1";
			okToSubmit = false;
		}
	}else{
		alertString = alertString + "<BR>" + "Phone Number is required.";
		if (okToSubmit) {
			objName = "phoneNumber1";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
		if (firstName.value == firstNameTmp.value && lastName.value == lastNameTmp.value && dob.value) { // don't check for existing user
			updatePhone('phoneNumber');
		} else {		// check for existing user
			// ajax call to check for existing student.
			if (window.XMLHttpRequest) {
				req = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}

			<s:url action="ajaxCheckUser" id="ajaxCheckUserID"/>
			var url = "<s:property value='%{ajaxCheckUserID}'/>" + "?firstName=" + firstName.value + "&lastName=" + lastName.value + "&dob=" + dob.value + "&classroomPk=" + classroomPk.value;
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
	}else{
		msgBlock = '<div align="center" class="errorText">';
		msgBlock = msgBlock + alertString;
		msgBlock = msgBlock + '</div>';
		document.getElementById("messageDIV").innerHTML = msgBlock;
		document.getElementById(objName).focus();
	}

	if(okToSubmit){
		document.forms[0].submit();
	}
}

function callback()
{
	if (req.readyState == 4) {
		var serverResponse = req.responseXML.getElementsByTagName("message");
		//alert("return length/value: " + serverResponse.length + " / " + serverResponse[0].childNodes[0].nodeValue);
		if (serverResponse[0].childNodes[0].nodeValue == "Yes") {
			document.getElementById("show1").click();
		} else {
			updatePhone('phoneNumber');
		}
	}	
}
        </script>
    </head>
    <body>
		<div id="messageDIV">&nbsp;</div>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h1><s:text name="commercial.studentUpdate.header"/></h1>
        <br/>
        <h2>
        <s:url id="url" namespace="/commercial" action="editClassroom-">
        	<s:param name="classroomPk" value="classroomPk"/>
        </s:url>
        <s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To Classroom"/></s:a>
        </h2>
        <br/>
        
        <s:form name="editForm" namespace="/commercial" action="updateStudentInformation"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <s:textfield label="%{getText('student.firstName')}" name="firstName" id="firstName" required="true"/>
            <s:set name="firstNameTmpVal" value="firstName" />
            <input type='hidden' name='firstNameTmp' id="firstNameTmpId" value='<s:property value="#firstNameTmpVal" />' />

            <s:textfield id="middleName" label="%{getText('student.middleName')}" name="middleName" />
            <s:textfield id="lastName" label="%{getText('student.lastName')}" name="lastName" required="true"/>
            <s:set name="lastNameTmpVal" value="lastName" />
            <input type='hidden' name='lastNameTmp' id="lastNameTmpId" value='<s:property value="#lastNameTmpVal" />' />

            <tr>
   				<td>Phone Number<span class="required">*</span>:</td>
   				<td>
   					<input type="text" name="phoneNumber1" id="phoneNumber1" value="" size="3" maxlength="3"> -
  					<input type="text" name="phoneNumber2" id="phoneNumber2" value="" size="3" maxlength="3"> -
   					<input type="text" name="phoneNumber3" id="phoneNumber3" value="" size="4" maxlength="4">
           			<s:hidden name="phoneNumber" id="phoneNumber" />
   				</td>
   			</tr>
            
            <s:date format="MM/dd/yyyy" name="dob" id="dobFormat"/>
            <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="dob" id="dob" cssClass="datepicker" maxlength="10"/>
            <s:set name="dobTmpVal" value="dob" />
            <input type='hidden' name='dobTmp' id="dobTmpId" value='<s:property value="#dobTmpVal" />' />
            
            <s:textfield label="%{getText('student.address1')}" name="address1"/>
            <s:textfield label="%{getText('student.address2')}" name="address2"/>
            <s:textfield label="%{getText('student.city')}" name="city"/>
            <s:select label="%{getText('student.state')}" 
                      name="state" 
                      headerKey="" 
                      headerValue="Select State"
                      list="stateMap"
                      multiple="false"/>
            <s:textfield label="%{getText('student.zip')}" name="zip"/>

			<tr>
				<td align="right" colspan="2">
    				<input type="button" value="Update" onclick="createCommercialStudentValidate();" />
    			</td>
			</tr>
			<s:hidden id="classroomPk" name="classroomPk"/>
			<s:hidden id="studentPk" name="studentPk"/>
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
		
			loadPhone('phoneNumber');
		</script>
        
        <button id="show1" style="display: none"></button>
    </body>
</html>
