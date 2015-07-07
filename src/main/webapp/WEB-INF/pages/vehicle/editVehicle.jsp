<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="vehicle.index.title"/></title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>

		<script type="text/javascript">
function returnToList()
{
	document.editForm.action = "list.do";
	document.editForm.submit();
}

function validate() {
	// fields validation
	var insurancePhoneNotBlank = phoneNotEmpty("insurancePhone");
	var phoneIsValid;
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var objName = "";
	var okToSubmit = true;

	//Do Phone Number validation
	if (insurancePhoneNotBlank) {
		phoneIsValid = validatePhone("insurancePhone");
		if (!phoneIsValid) {
			alertString = alertString + "<BR>" + "Agency Phone Number is incomplete.";
			objName = "insurancePhone1";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
		updatePhone("insurancePhone");
		document.getElementById("messageDIV").innerHTML = "";
	}else{
		msgBlock = '<div class="errorText">';
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
        <h4><s:text name="vehicle.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="editForm" action="save" >
<s:if test='%{editAction != null && editAction == "Y"}'>
	        <s:a href="javascript:returnToList();">
	        	<img src="/sdc/images/btn_vehiclelist.gif" alt="Back To Vehicle List" />
	        </s:a>
</s:if>
    	    <br/>
        	<br/>
			<s:textfield label="%{getText('vehicle.vehiclePlate')}" name="currentVehicle.vehiclePlate" maxLength="10" required="true"/>
			<s:select label="%{getText('vehicle.vehicleState')}" name="currentVehicle.vehicleState" headerKey="" headerValue="Select State" list="stateMap" multiple="false" required="true"/>
			<s:select label="%{getText('vehicle.highschools')}" name="currentVehicle.schoolFk" id="schoolFk" multiple="false" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" size="1" required="true"/>
			<s:textfield label="%{getText('vehicle.vehicleMake')}" name="currentVehicle.vehicleMake"/>
			<s:textfield label="%{getText('vehicle.vehicleYear')}" name="currentVehicle.vehicleYear"/>
			<s:textfield label="%{getText('vehicle.vehicleVin')}" name="currentVehicle.vehicleVin"/>
			<s:textfield label="%{getText('vehicle.insurancePolicy')}" name="currentVehicle.insurancePolicy"/>
			<s:date format="MM/dd/yyyy" name="currentVehicle.insuranceExpire" id="insuranceExpireFormat"/>
        	<s:textfield label="%{getText('vehicle.insuranceExpire')}" value="%{insuranceExpireFormat}" name="currentVehicle.insuranceExpire" id="insuranceExpire" cssClass="datepicker" maxlength="10"/>
			
			<s:textfield label="%{getText('vehicle.insuranceCompany')}" name="currentVehicle.insuranceCompany"/>
			<s:textfield label="%{getText('vehicle.insuranceAgent')}" name="currentVehicle.insuranceAgent"/>
            <tr>
   				<td>Agent Phone Number:</td>
       			<td>
       				<input type="text" name="insurancePhone1" id="insurancePhone1" value="" size="3" maxlength="3"> -
       				<input type="text" name="insurancePhone2" id="insurancePhone2" value="" size="3" maxlength="3"> -
       				<input type="text" name="insurancePhone3" id="insurancePhone3" value="" size="4" maxlength="4">
					<s:hidden name="currentVehicle.insurancePhone" id="insurancePhone" />
            	</td>
            </tr>
			<s:submit value="%{getText('vehicle.save')}"  onclick="return validate()" />
			<s:hidden name="currentVehicle.vehiclePk" />
			<s:hidden name="searchVehiclePlate" />
			<s:hidden name="searchSchoolFk" />
			<s:hidden name="activeFlag" />
			<s:hidden name="editAction"/>
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
		
			loadPhone('insurancePhone');
		</script>
   </body>
</html>
