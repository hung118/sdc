function do_save()
{
	var sType = document.getElementById("schoolType").value;
	var eDate = document.getElementById("expireDate").value;
    var dateEmpty = true;
    var dateValid;
	var businessPhoneNotBlank = phoneNotEmpty("businessPhone");
	var businessPhoneAltNotBlank = phoneNotEmpty("businessPhoneAlt");
	var phoneIsValid;
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var msgBlock = "";
    var objName;
	var okToSubmit = true;

	// Make sure School Name is not empty
	if (document.getElementById("schoolName").value.length == 0) {
		alertString = alertString + "<BR>" + "School Name is blank.";
		if (okToSubmit) {
			objName = "schoolName";
			okToSubmit = false;
		}
	}
	// Make sure School Number is not empty
	if (document.getElementById("schoolNumber").value.length == 0) {
		alertString = alertString + "<BR>" + "School Number is blank.";
		if (okToSubmit) {
			objName = "schoolNumber";
			okToSubmit = false;
		}
	}
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
	
	// Check to see if we need to do date validation on expiration date
    if (eDate.length > 0) {
    	dateValid = isDate(eDate);
		if (!dateValid) {
			alertString = alertString + "<BR>" + "Expiration Date is invalid.";
			if (okToSubmit) {
				objName = "expireDate";
				okToSubmit = false;
			}
		}
    }else{
        // Expiration date is blank - Make sure this is not a Commercial School.
        if (document.getElementById("schoolType").value == "1") {
			alertString = alertString + "<BR>" + "Expiration Date must be supplied for a commercial school.";
			if (okToSubmit) {
				objName = "expireDate";
				okToSubmit = false;
			}
        }
    }
    
	if (okToSubmit) {
		updatePhone("businessPhone","businessPhoneAlt");
		document.getElementById("messageDIV").innerHTML = "";
		document.editForm.submit();
	}else{
		msgBlock = '<div class="errorText">';
		msgBlock = msgBlock + alertString;
		msgBlock = msgBlock + '</div>';
		document.getElementById("messageDIV").innerHTML = msgBlock;
		document.getElementById(objName).focus();
	}
	return okToSubmit;
}