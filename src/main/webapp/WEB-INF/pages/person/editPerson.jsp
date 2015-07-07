<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="index.title"/></title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>

        <script type="text/javascript" src="<s:url value='/common/multiselect.js' includeParams='none'/>"></script> 
        <script type="text/javascript">
function returnToList()
{
	document.editForm.action = "list.do";
	document.editForm.submit();
}

function formatDate(field)
{
	alert("here");
	alert("field is "+field);
}
            
function checkSchools()
{
	var arr=[];
	var els=document.getElementsByTagName('input');
	for(var i=0;i<els.length;i++) {
		if(els[i].name && els[i].name.match('currentPerson.associatedSchools')) {
			if (els[i].checked) {
				arr[arr.length]=els[i].value;
			}    
		}
	}
	updateCurrentSchools(arr);
}

function updateCurrentSchools(id)
{
	var reportDiv = document.getElementById('currentAssociatedSchools');
	reportDiv.href = '/school/currentAssociatedSchools.do?schoolArr='+id;
	alert(reportDiv.href);
	reportDiv.refresh();
}

function checked(obj)
{
	var label = document.getElementById('label'+obj.value);
	if (obj.checked){
		label.className="checked";
	}else {
		label.className="unchecked";
	}    
}

function toggleInstructor(value)
{
	if (value.length > 0) {
		document.getElementById('instructorID').firstChild.nodeValue = 'Yes';
	} else {
		document.getElementById('instructorID').firstChild.nodeValue = 'No';
	}
}

function validate() {
	// fields validation
	var homePhoneNotBlank = phoneNotEmpty("homePhone");
	var businessPhoneNotBlank = phoneNotEmpty("businessPhone");
	var homePhoneIsValid, businessPhoneIsValid;
	var alertmsg = "The following field(s) need to be completed: \n";
	var okToSubmit = true;
	var objname = null;

	if (homePhoneNotBlank) {
		homePhoneIsValid = validatePhone("homePhone");
		if (!homePhoneIsValid) {
			alertmsg += "\n     Home Number is incomplete.\n";
			objname = "homePhone1";
			okToSubmit = false;
		}
	}
	if (businessPhoneNotBlank) {
		businessPhoneIsValid = validatePhone("businessPhone");
		if (!businessPhoneIsValid) {
			alertmsg += "\n     Work Number is incomplete.\n";
			if (okToSubmit) {
				objname = "businessPhone1";
				okToSubmit = false;
			}
		}
	}else{
		alertmsg += "\n     Work Number is required.\n";
		if (okToSubmit) {
			objname = "businessPhone1";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
        // update phone and date
    	updatePhone('homePhone,businessPhone'); 
	}else{
		alert(alertmsg);
		document.getElementById(objname).focus();
	}
                
   	return okToSubmit;
}

function toggleTestTypes() {
	
	var checkBox1 = document.getElementById("currentPerson.roles-1");
	var checkBox2 = document.getElementById("currentPerson.roles-2");

	if (checkBox1.checked == true || checkBox2.checked == true) {
		document.getElementById("testTypeId").style.display = "block";
		document.getElementById("currentPerson.testTypes-1").checked = true;
		document.getElementById("currentPerson.testTypes-2").checked = true;
	} else {
		document.getElementById("testTypeId").style.display = "none";
		document.getElementById("currentPerson.testTypes-1").checked = false;
		document.getElementById("currentPerson.testTypes-2").checked = false;
	}
}

</script>

    </head>
    <body>
        <br/>
        <h4><s:text name="person.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <s:form name="editForm" action="save" >
<s:if test='%{editAction != null && editAction == "Y"}'>
			<s:a href="javascript:returnToList();">
				<img src="/sdc/images/btn_userlist.gif" alt="Back To User List" />
			</s:a>
</s:if>
			<br/>
			<br/>
			<s:textfield label="%{getText('person.driversLicenseNumber')}" name="currentPerson.driversLicenseNumber"/>
            <s:select label="%{getText('person.driversLicenseState')}" name="currentPerson.driversLicenseState" headerKey="" headerValue="Select State" list="stateMap" multiple="false" required="true"/>
            <s:textfield label="%{getText('person.instructornum')}" name="currentPerson.instructorNum" onblur="toggleInstructor(this.value);"/>
            <s:textfield label="%{getText('person.firstName')}" name="currentPerson.firstName"/>
            <s:textfield label="%{getText('person.middleName')}" name="currentPerson.middleName"/>
            <s:textfield label="%{getText('person.lastName')}" name="currentPerson.lastName" required="true" />
            <s:textfield label="%{getText('person.suffix')}" name="currentPerson.suffix"/>
            <s:textfield key="currentPerson.dob" value="%{getText('format.date',{currentPerson.dob})}" required="true" />
            <s:textfield label="%{getText('person.address1')}" name="currentPerson.address1"/>
            <s:textfield label="%{getText('person.address2')}" name="currentPerson.address2"/>
            <s:textfield label="%{getText('person.city')}" name="currentPerson.city"/>
            <s:select label="%{getText('person.state')}" name="currentPerson.state" headerKey="" headerValue="Select State" list="stateMap" multiple="false" required="true"/>
            <s:textfield label="%{getText('person.zip')}" name="currentPerson.zip"/>
            <tr>
            	<td>Home Number:</td>
            	<td>
            		<input type="text" name="homePhone1" id="homePhone1" value="" size="3" maxlength="3"> -
            		<input type="text" name="homePhone2" id="homePhone2" value="" size="3" maxlength="3"> -
            		<input type="text" name="homePhone3" id="homePhone3" value="" size="4" maxlength="4">
		            <s:hidden name="currentPerson.homePhone" id="homePhone" />
            	</td>
            </tr>
            <tr>
            	<td>Work Number<span class="required">*</span>:</td>
            	<td>
            		<input type="text" name="businessPhone1" id="businessPhone1" value="" size="3" maxlength="3"> -
            		<input type="text" name="businessPhone2" id="businessPhone2" value="" size="3" maxlength="3"> -
            		<input type="text" name="businessPhone3" id="businessPhone3" value="" size="4" maxlength="4">
		            <s:hidden name="currentPerson.businessPhone" id="businessPhone" />
            	</td>
            </tr>
            <s:textfield label="%{getText('person.email')}" name="currentPerson.email" maxLength="45" size="45" required="true" />
            <s:textfield label="%{getText('person.languages')}" name="currentPerson.languages" maxLength="60" size="60" />
<s:if test="currentPerson.instructorNum != null && currentPerson.instructorNum != ''">
            <s:label label="Instructor" value="Yes" id="instructorID" />
</s:if>
<s:else>
	        <s:label label="Instructor" value="No" id="instructorID" />
</s:else>

            <s:checkboxlist label="%{getText('person.roles')}" list="allRolesList" listKey="roleTypesPk" listValue="description" name="currentPerson.roles" onclick="toggleTestTypes();" />

			<s:if test="currentPerson.roles[0] == 1 || currentPerson.roles[0] == 2">         
      		<tr id="testTypeId">
            	<td>High Schools Test Types:</td>
            	<td>
            		<s:checkboxlist theme="simple" list="#{1:'Knowledge Test', 2:'Skills Test'}" name="currentPerson.testTypes" />
				</td>
			</tr>
			</s:if>
            
            <s:select label="%{getText('person.highschools')}" name="currentPerson.associatedSchools" id="select_associatedSchools" multiple="true" list="highSchoolListAll" listKey="schoolPk" listValue="schoolName" size="8" />
            <s:select label="%{getText('person.commercialschools')}" name="currentPerson.associatedSchools" id="select_associatedSchools" multiple="true" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" size="8" />
            
            <s:date format="MM/dd/yyyy" name="currentPerson.backgroundCheckDate" id="backgroundCheckDateFormat"/>
            <s:textfield label="Background Check Date" value="%{backgroundCheckDateFormat}" name="currentPerson.backgroundCheckDate" id="backgroundCheckDate" cssClass="datepicker" maxlength="10"/>

            <s:date format="MM/dd/yyyy" name="currentPerson.pdpCheckDate" id="pdpCheckDateFormat"/>
            <s:textfield label="PDP Check Date" value="%{pdpCheckDateFormat}" name="currentPerson.pdpCheckDate" id="pdpCheckDate" cssClass="datepicker" maxlength="10"/>

            <s:date format="MM/dd/yyyy" name="currentPerson.instructorTrainingDate" id="instructorTrainingDateFormat"/>
            <s:textfield label="Instructor Training Date" value="%{instructorTrainingDateFormat}" name="currentPerson.instructorTrainingDate" id="instructorTrainingDate" cssClass="datepicker" maxlength="10"/>

            <s:date format="MM/dd/yyyy" name="currentPerson.licenseExpireDate" id="licenseExpireDateFormat"/>
            <s:textfield label="License Expiration Date" value="%{licenseExpireDateFormat}" name="currentPerson.licenseExpireDate" id="licenseExpireDate" cssClass="datepicker" maxlength="10"/>
            
            <s:date format="MM/dd/yyyy" name="currentPerson.dlHistoryDate" id="dlHistoryDateFormat"/>
            <s:textfield label="DL History Date" value="%{dlHistoryDateFormat}" name="currentPerson.dlHistoryDate" id="dlHistoryDate" cssClass="datepicker" maxlength="10"/>
            
            <s:submit value="%{getText('person.save')}" onclick="return validate()"/>
            <s:hidden name="currentPerson.personPk" />
            <s:hidden name="currentPerson.updatedBy"  value="%{updatedEmail}"/>
			<s:hidden name="searchInstructor" />
			<s:hidden name="searchFirstName" />
			<s:hidden name="searchLastName" />
			<s:hidden name="searchSchoolName" />
			<s:hidden name="searchInstructorNum" />
			<s:hidden name="activeFlag" />
			<s:hidden name="editAction" />
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
			
			loadPhone('homePhone,businessPhone');
		</script>
    </body>
</html>
