<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="bond.index.title"/></title>
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
	var bondPhoneNotBlank = phoneNotEmpty("bondPhone");
	var phoneIsValid;
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var objName = "";
	var okToSubmit = true;

	//Do Phone Number validation
	if (bondPhoneNotBlank) {
		phoneIsValid = validatePhone("bondPhone");
		if (!phoneIsValid) {
			alertString = alertString + "<BR>" + "Phone Number is incomplete.";
			objName = "bondPhone1";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
		updateDateField('issueDate,expireDate');
		updatePhone('bondPhone');
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
        <h4><s:text name="bond.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="editForm" action="save" >
<s:if test='%{editAction != null && editAction == "Y"}'>
	        <s:a href="javascript:returnToList();">
	        	<img src="/sdc/images/btn_bondlist.gif" alt="Back To Bond List" />
	        </s:a>
</s:if>
    	    <br/>
        	<br/>
			<s:textfield label="%{getText('bond.company')}" name="bond.company" size="45" required="true"/>
			<s:textfield label="%{getText('bond.agent')}" name="bond.agent" size="45"/>
			<s:select label="%{getText('bond.highschools')}" name="bond.schoolFk" id="schoolFk" multiple="false" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" size="1"/>
			<s:date format="MM/dd/yyyy" name="bond.issueDate" id="issueDateFormat"/>
			<s:textfield label="%{getText('bond.issueDate')}" value="%{issueDateFormat}" name="bond.issueDate" id="issueDateFormat" cssClass="datepicker" maxlength="10"/>
			<s:date format="MM/dd/yyyy" name="bond.expireDate" id="expireDateFormat"/>
			<s:textfield label="%{getText('bond.expireDate')}" value="%{expireDateFormat}" name="bond.expireDate" id="expireDate" cssClass="datepicker" maxlength="10"/>
			
			
			<s:textfield label="%{getText('bond.amount')}" name="bond.amount" onkeypress="return (numberOnly(event));"/>
            <tr>
   				<td>Phone Number:</td>
       			<td>
       				<input type="text" name="bondPhone1" id="bondPhone1" value="" size="3" maxlength="3"> -
       				<input type="text" name="bondPhone2" id="bondPhone2" value="" size="3" maxlength="3"> -
       				<input type="text" name="bondPhone3" id="bondPhone3" value="" size="4" maxlength="4">
					<s:hidden name="bond.phone" id="bondPhone" />
            	</td>
            </tr>
			<s:textfield label="%{getText('bond.bondNumber')}" name="bond.bondNumber"/>
			<s:submit value="%{getText('bond.save')}"  onclick="return validate();" />
			<s:hidden name="bond.bondsPk" />
			<s:hidden name="searchSchoolFk"/>
			<s:hidden name="activeFlag"/>
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
			
			loadPhone('bondPhone');
		</script>
   </body>
</html>
