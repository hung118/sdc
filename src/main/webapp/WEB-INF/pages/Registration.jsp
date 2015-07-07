<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><s:text name="index.title"/></title>
        
        <script>
function validate() {
	var dob1 = document.getElementById("dob1");
	var dob2 = document.getElementById("dob2");
	var dob3 = document.getElementById("dob3");
	var notesField = document.getElementById("notes");
	var businessPhoneNotBlank = phoneNotEmpty("businessPhone");
	var homePhoneNotBlank = phoneNotEmpty("homePhone");
	var phoneIsValid
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var objName;
	var okToSubmit = true;

	if (dob1.value.length < 1 || dob2.value.length < 1 || dob3.value.length < 1) {
		alertString += "<BR>" + "Birth Date is rquired.";
		objName = "dob1";
		okToSubmit = false;
	}
	if (businessPhoneNotBlank) {
		phoneIsValid = validatePhone("businessPhone");
		if (!phoneIsValid) {
			alertString += "<BR>" + "Main Contact Number is incomplete.";
			if (okToSubmit) {
				objName = "businessPhone1";
				okToSubmit = false;
			}
		}
	}else{
		alertString += "<BR>" + "Main Contact Number is required.";
		if (okToSubmit) {
			objName = "businessPhone1";
			okToSubmit = false;
		}
	}
	if (homePhoneNotBlank) {
		phoneIsValid = validatePhone("homePhone");
		if (!phoneIsValid) {
			alertString += "<BR>" + "Alternate Number is incomplete.";
			if (okToSubmit) {
				objName = "homePhone1";
				okToSubmit = false;
			}
		}
	}
	
	if (trim(notesField.value) == "" || notesField.value == "Please name the school(s) that you are registering for and the languages that you speak."){
		alertString += "<BR>" + "Name the school(s) that you are registering for in the Notes field.";
		if (okToSubmit) {
			objName = "notes";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
		updatePhone('homePhone,businessPhone'); 
		updateDate('dob');
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
        <h1><s:text name="registration.title"/></h1>
        <br/>
        <h2><s:text name="registration.description"/></h2>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="editForm" action="Registration_save" >
            <s:textfield label="%{getText('registration.driversLicenseNumber')}" name="person.driversLicenseNumber" required="true" />
            <s:select label="%{getText('registration.driversLicenseState')}" 
                      name="person.driversLicenseState" 
                      headerKey="-1" 
                      headerValue="Select State"
                      list="stateMap"
                      multiple="false"
                      required="true"
                      />
            <s:textfield label="%{getText('registration.instructornum')}" name="person.instructorNum" readonly="true" cssClass="readonly"/>
            <s:textfield label="%{getText('registration.firstName')}" name="person.firstName" required="true"/>
            <s:textfield label="%{getText('registration.middleName')}" name="person.middleName"/>
            <s:textfield label="%{getText('registration.lastName')}" name="person.lastName" required="true"/>
            <s:textfield label="%{getText('registration.suffix')}" name="person.suffix"/>
            
            <tr>
            	<td>Birth Date<span class="required">*</span>:<br>(MM/DD/YYYY)</td>
            	<td>
            		<input type="text" name="dob1" id="dob1" value="" size="2" maxlength="2"> /
            		<input type="text" name="dob2" id="dob2" value="" size="2" maxlength="2"> /
            		<input type="text" name="dob3" id="dob3" value="" size="4" maxlength="4">
            	</td>
            </tr>
            <s:hidden name="person.dob" id="dob" />
            
            <s:textfield label="%{getText('registration.address1')}" name="person.address1" required="true"/>
            <s:textfield label="%{getText('registration.address2')}" name="person.address2"/>
            <s:textfield label="%{getText('registration.city')}" name="person.city" required="true"/>
            <s:select label="%{getText('registration.state')}" 
                      name="person.state" 
                      headerKey="-1" 
                      headerValue="Select State"
                      list="stateMap"
                      multiple="false"
                      required="true"
                      />
            <s:textfield label="%{getText('registration.zip')}" name="person.zip" required="true"/>
            
            <tr>
            	<td>Main Contact Number<span class="required">*</span>:</td>
            	<td>
            		<input type="text" name="businessPhone1" id="businessPhone1" value="" size="3" maxlength="3"> -
            		<input type="text" name="businessPhone2" id="businessPhone2" value="" size="3" maxlength="3"> -
            		<input type="text" name="businessPhone3" id="businessPhone3" value="" size="4" maxlength="4">
            	</td>
            </tr>
            <s:hidden name="person.businessPhone" id="businessPhone" />

            <tr>
            	<td>Alternate Number:</td>
            	<td>
            		<input type="text" name="homePhone1" id="homePhone1" value="" size="3" maxlength="3"> -
            		<input type="text" name="homePhone2" id="homePhone2" value="" size="3" maxlength="3"> -
            		<input type="text" name="homePhone3" id="homePhone3" value="" size="4" maxlength="4">
            	</td>
            </tr>
            <s:hidden name="person.homePhone" id="homePhone" />
     
            <s:textfield label="%{getText('registration.email')}" name="person.email" readonly="true" cssClass="readonly" size="45" maxLength="45"/>
            <s:textarea required="true" id="notes" label="Notes" name="notes" cols="45" onfocus="if(this.value=='Please name the school(s) that you are registering for and the languages that you speak.')this.value='';"  rows="3" value="Please name the school(s) that you are registering for and the languages that you speak." />
            <s:submit value="%{getText('registration.register')}" onclick="return validate();" />
            <s:hidden name="person.updatedBy" value="%{updatedEmail}"/>
            <s:hidden name="person.dn"/>
            <s:hidden name="person.personPk" />
        </s:form>
        
		<script language="javascript" type="text/javascript">
			loadPhone('homePhone,businessPhone');
			loadDate('dob');
		</script>
        
    </body>
    
    
</html>
