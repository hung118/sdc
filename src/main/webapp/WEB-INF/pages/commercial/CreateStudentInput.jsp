<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="commercial.student.title"/></title>
        
		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
        
        <script type="text/javascript" src="<@s.url value='/common/sdc.js' includeParams='none'/>"></script>
<script type="text/javascript">
result = false;
YAHOO.namespace("example.container");

function init() {
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
		{ width: "380px",
		  fixedcenter: true,
		  visible: false,
		  modal:true,
		  draggable: false,
		  close: true,
		  text: "Student already exists in the group. Do you want to add?",
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
	// ajax call to check for existing student.
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}

	var phoneNotBlank = phoneNotEmpty("studentPhone");
	var phoneIsValid;
	var firstName = document.getElementById("firstName");
	var lastName = document.getElementById("lastName");
	var dob = document.getElementById("dob");
	var classroomPk = document.getElementById("classroomPk");
	var alertmsg = "The following field(s) need to be completed: \n";
	var okToSubmit = true;
	var objname = null;

	if (phoneNotBlank) {
		phoneIsValid = validatePhone("studentPhone");
		if (!phoneIsValid) {
			alertmsg += "\n     Phone Number is incomplete.\n";
			objname = "studentPhone1";
			okToSubmit = false;
		}
	} else {
		alertmsg += "\n     Phone Number is incomplete.\n";
		objname = "studentPhone1";
		okToSubmit = false;
	}

	if(dob.value == '') {
		alertmsg += "\n     Date of Birth is incomplete.\n";
		objname = "dob";
		okToSubmit = false;
	} else if (!isValidDateString(dob.value)) {
		alertmsg += "\n     Invalid Date of Birth.\n";
		objname = "dob";
		okToSubmit = false;		
	}

	if (okToSubmit) {
        // update phone and date
    	updatePhone('studentPhone');
	}else{
		alert(alertmsg);
		document.getElementById(objname).focus();
		return false;
	}

	<s:url action="ajaxCheckUser" id="ajaxCheckUserID" />
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
                        
function callback()
{
	if (req.readyState == 4) {
		var serverResponse = req.responseXML.getElementsByTagName("message");
		//alert("return length/value: " + serverResponse.length + " / " + serverResponse[0].childNodes[0].nodeValue);
		if (serverResponse[0].childNodes[0].nodeValue == "Yes") {
			document.getElementById("show1").click();
		} else {
			document.forms[0].submit();
		}
	}
}

function onClick_cityStateLookup()
{
	zipEmpty = true;
	checkedZip = false;
    parmZip = "";

	if (document.editForm.zip.value.length > 0){
		zipEmpty = false;
		checkedZip = isNumeric(document.editForm.zip.value);
	}
    if (!zipEmpty && checkedZip) {
		parmZip = document.editForm.zip.value;
        lookupWindow = window.open("<%= request.getContextPath() %>/addresslookup/cityStateLookup.do?zip="+parmZip+"&src=commercial", "lookupWindow", 'width=800,height=400,toolbar=no,status=no,menubar=no,scrollbars=yes,resizeable=no');
    }else{
        alert("Zip code accepts only integer values.");
    }
    return checkedZip;
}
        </script>
    </head>
    <body>
        <s:actionerror cssClass="actionError"/>
        <s:actionmessage/>
        <br/>
        
        <h4><s:text name="commercial.student.header"/></h4>
        <br/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editClassroom-">
            <s:param name="classroomPk" value="classroomPk"/>
        </s:url>
        <s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To Classroom"/></s:a>
        </h2>
        <br/>
        
        <s:form name="editForm" namespace="/commercial" action="createCommercialStudent"  tooltipDelay="500" tooltipIconPath="/images/information.png" >
            <s:textfield label="%{getText('student.firstName')}" name="firstName" id="firstName" required="true"/>
            <s:textfield label="%{getText('student.middleName')}" name="middleName" />
            <s:textfield label="%{getText('student.lastName')}" name="lastName" id="lastName" required="true"/> 
            <tr>
				<td>Phone Number<span style="color: red;">*</span>:</td>
       			<td>
       				<input type="text" name="studentPhone1" id="studentPhone1" value="" size="3" maxlength="3"> -
       				<input type="text" name="studentPhone2" id="studentPhone2" value="" size="3" maxlength="3"> -
       				<input type="text" name="studentPhone3" id="studentPhone3" value="" size="4" maxlength="4">
					<s:hidden name="phoneNumber" id="studentPhone" />
            	</td>
            </tr>

            <s:date format="MM/dd/yyyy" name="dob" id="dobFormat"/>
            <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="dob" id="dob" cssClass="datepicker" maxlength="10"/>
            
            <s:textfield label="%{getText('student.address1')}" name="address1"/>
            <s:textfield label="%{getText('student.address2')}" name="address2"/>

            <tr>
    			<td class="tdLabel"><label for="createCommercialStudent_zip" class="label">Zip:</label></td>
    			<td>
    				<input type="text" name="zip" value="" id="createCommercialStudent_zip"/> 
	            	<input type="button" id="addressLookup" onclick="onClick_cityStateLookup();" value="Find" />
    			</td>
			</tr>

            <s:textfield label="%{getText('student.city')}" name="city"/>
            <s:select label="%{getText('student.state')}" name="state" headerKey="" headerValue="Select State" list="stateMap" multiple="false"/>

			<tr>
				<td align="right" colspan="2">
    				<input type="button" value="Save" onclick="createCommercialStudentValidate();" />
    			</td>
			</tr>
           	<s:hidden name="classroomPk" id="classroomPk"/>
        </s:form>
		<button id="show1" style="display: none"></button>
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
		
			loadPhone('studentPhone');
		</script>
    </body>
</html>
