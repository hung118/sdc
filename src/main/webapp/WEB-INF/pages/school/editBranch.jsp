<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="school.index.title"/></title>
        <script language="javascript" type="text/javascript">
function returnToSchool()
{
	document.editForm.action = 'edit-<s:property value="schoolPk"/>.do';
	document.editForm.submit();
}

function validate() {
	// fields validation
	var businessPhoneNotBlank = phoneNotEmpty("businessPhone");
	var businessPhoneAltNotBlank = phoneNotEmpty("businessPhoneAlt");
	var phoneIsValid
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var objName;
	var okToSubmit = true;

	// Make sure Address is not empty
	if (document.getElementById("address1").value.length == 0) {
		alertString = alertString + "<BR>" + "Address is blank.";
		if (okToSubmit) {
			objName = "address1";
			okToSubmit = false;
		}
	}
	// Make sure City is not empty
	if (document.getElementById("city").value.length == 0) {
		alertString = alertString + "<BR>" + "City is blank.";
		if (okToSubmit) {
			objName = "city";
			okToSubmit = false;
		}
	}
	// Make sure State is not empty
	if (document.getElementById("state").value == "-1") {
		alertString = alertString + "<BR>" + "A State must be selected.";
		if (okToSubmit) {
			objName = "state";
			okToSubmit = false;
		}
	}
	// Make sure Zipcode is not empty
	if (document.getElementById("zip").value.length < 5) {
		alertString = alertString + "<BR>" + "A valid zipcode must be provided.";
		if (okToSubmit) {
			objName = "zip";
			okToSubmit = false;
		}
	}

	//Do Phone Number validation
	if (businessPhoneNotBlank) {
		phoneIsValid = validatePhone("businessPhone");
		if (!phoneIsValid) {
			alertString = alertString + "<BR>" + "Phone Number is incomplete.";
			objName = "businessPhone1";
			okToSubmit = false;
		}
	}else{
		alertString = alertString + "<BR>" + "Phone Number is required.";
		if (okToSubmit) {
			objName = "businessPhone1";
			okToSubmit = false;
		}
	}

	if (businessPhoneAltNotBlank) {
		phoneIsValid = validatePhone("businessPhoneAlt");
		if (!phoneIsValid) {
			alertString = alertString + "<BR>" + "Alt Phone Number is incomplete.";
			if (okToSubmit) {
				objName = "businessPhoneAlt1";
				okToSubmit = false;
			}
		}
	}

	if (okToSubmit) {
		updatePhone("businessPhone","businessPhoneAlt");
		document.getElementById("messageDIV").innerHTML = "";
	}else{
		msgBlock = '<div align="center" class="errorText">';
		msgBlock = msgBlock + alertString;
		msgBlock = msgBlock + '</div>';
		document.getElementById("messageDIV").innerHTML = msgBlock;
		document.getElementById(objName).focus();
	}
	return okToSubmit;
}
</script>
    </head>
    <body>
		<div id="messageDIV">&nbsp;</div>
        <h4><s:text name="branch.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="editForm" action="saveBranch">
	        <s:a href="javascript:returnToSchool();">
	        	<img src="/sdc/images/btn_school.gif" alt="Back To School" />
	        </s:a>
    	    <br/>
        	<br/>
        	<table>
        		<tr>
        			<td style="border-width:0px;">
						<s:label label="%{getText('school.schoolName')}" name="currentSchool.schoolName"  />
						<s:label label="%{getText('school.schoolNumber')}" name="currentSchool.schoolNumber" />
						<s:textfield label="%{getText('school.address1')}" id="address1" name="currentBranch.address1" required="true"/>
						<s:textfield label="%{getText('school.address2')}" name="currentBranch.address2"/>
						<s:textfield label="%{getText('school.city')}" id="city" name="currentBranch.city" required="true"/>
						<s:select label="%{getText('school.state')}" id="state" name="currentBranch.state" headerKey="-1" headerValue="Select State" list="stateMap" multiple="false" required="true"/>
						<s:textfield label="%{getText('school.zip')}" id="zip" name="currentBranch.zip" required="true"/>
			            <tr>
            				<td>Phone Number<span class="required">*</span>:</td>
            				<td>
            					<input type="text" name="businessPhone1" id="businessPhone1" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhone2" id="businessPhone2" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhone3" id="businessPhone3" value="" size="4" maxlength="4">
		            			<s:hidden name="currentBranch.businessPhone" id="businessPhone" />
            				</td>
            			</tr>
			            <tr>
            				<td>Alt Phone Number:</td>
            				<td>
            					<input type="text" name="businessPhoneAlt1" id="businessPhoneAlt1" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhoneAlt2" id="businessPhoneAlt2" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhoneAlt3" id="businessPhoneAlt3" value="" size="4" maxlength="4">
		            			<s:hidden name="currentBranch.businessPhone2" id="businessPhoneAlt" />
            				</td>
            			</tr>
<s:if test="%{currentBranch.branchPk != null}">
						<s:submit value="%{getText('school.save')}" onclick="return validate()" />
</s:if>
<s:else>
						<s:submit value="%{getText('school.create')}" onclick="return validate()" />
</s:else>
					</td>
				</tr>
			</table>
            <s:hidden name="currentBranch.branchPk" />
            <s:hidden name="currentBranch.updatedBy" value="%{updatedEmail}" />
            <s:hidden name="schoolPk" />
			<s:hidden name="searchSchoolType" />
			<s:hidden name="searchSchoolName" />
			<s:hidden name="searchSchoolNumber" />
			<s:hidden name="searchCity" />
			<s:hidden name="activeFlag" />
			<s:hidden name="editAction" />
        </s:form>
		<script language="javascript" type="text/javascript">
			loadPhone('businessPhone,businessPhoneAlt');
		</script>
    </body>
</html>
