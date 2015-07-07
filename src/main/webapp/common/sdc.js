function isNumeric(aString)
{
    var ValidChars = "0123456789";
    var IsNumber = true;
    var Char;

    if (aString == null || aString == '' || aString.length == 0) {
        IsNumber = false;
    }else{
        for (i = 0; i < aString.length && IsNumber == true; i++)
        {
            Char = aString.charAt(i);
            if (ValidChars.indexOf(Char) == -1) {
                IsNumber = false;
            }
        }
    }
    return IsNumber;
}

function isValidDate(m,d,y)
{
    returnValue = true;
    mNumeric = isNumeric(m);
    dNumeric = isNumeric(d);
    yNumeric = isNumeric(y);

    if (!mNumeric || !dNumeric || !yNumeric) returnValue = false;
    else if (m < 1 || m > 12) returnValue = false;
    else if (d < 1 || d > 31) returnValue = false;
    else if ((m == 2 || m == 4 || m == 6 || m == 9 || m == 11) && d > 30) returnValue = false;
    else if (m == 2 && ((y % 4 == 0 && d > 29) || (y % 4 != 0 && d > 28))) returnValue = false;
    else if (y < 1900) returnValue = false;

    return returnValue;
}

function isValidDateString(dateString) {
	var ret = new Date(dateString);
	if (ret.toString() == "Invalid Date") {
		return false;
	} else {
		return true;
	}
}

function isDate(d)
{
    //alert("Starting isDate("+d+")");
    returnValue = true;

    if (d != null && d != '' && d.length > 0) {
        // Get number of occurrences of '/' in d
        var temp = new Array();
        temp = d.split('/');
        if (temp.length < 3) {
        	returnValue = false;
        }else{
        	mNumeric = isNumeric(temp[0]);
            dNumeric = isNumeric(temp[1]);
            yNumeric = isNumeric(temp[2]);
            
            if (!mNumeric || !dNumeric || !yNumeric) {
            	returnValue = false;
            }else{
            	var m = parseInt(temp[0], 10);
            	var d = parseInt(temp[1], 10);
            	var y = parseInt(temp[2], 10);

            	if (m < 1 || m > 12) returnValue = false;
            	else if (d < 1 || d > 31) returnValue = false;
            	else if ((m == 2 || m == 4 || m == 6 || m == 9 || m == 11) && d > 30) returnValue = false;
            	else if (m == 2 && ((y % 4 == 0 && d > 29) || (m % 4 != 0 && d > 28))) returnValue = false;
            	else if (y < 1900) returnValue = false;
            }
        }
    }else returnValue = false;

    return returnValue;
}

function packDate(m,d,y)
{
    return new String(m + "/" + d + "/" + y);
}

function splitDate(inDate)
{
    return inDate.split('/');
}

function compareDates(value1, value2)
{
    // Return values:
    //   1 if value1 > value2
    //  -1 if value1 < value2
    //   0 if value1 = value2

    returnValue = 0;
    var date1, date2 = new Array();
    date1 = splitDate(value1);
    date2 = splitDate(value2);
    
    var m1 = parseInt(date1[0], 10);
    var m2 = parseInt(date2[0], 10);
    var d1 = parseInt(date1[1], 10);
    var d2 = parseInt(date2[1], 10);
    var y1 = parseInt(date1[2], 10);
    var y2 = parseInt(date2[2], 10);
    
    
    if (y1 > y2) returnValue = 1;
    else if (y1 < y2) returnValue = -1;
    else if (m1 > m2) returnValue = 1;
    else if (m1 < m2) returnValue = -1;
    else if (d1 > d2) returnValue = 1;
    else if (d1 < d2) returnValue = -1;

    return returnValue;
}

function showNews(whichLayer) {
    var elem, vis;
    if( document.getElementById ) // this is the way the standards work
        elem = document.getElementById( whichLayer );
    else if( document.all ) // this is the way old msie versions work
        elem = document.all[whichLayer];
    else if( document.layers ) // this is the way nn4 works
        elem = document.layers[whichLayer];
    vis = elem.style;
    // if the style.display value is blank we try to figure it out here
    if(vis.display==''&&elem.offsetWidth!=undefined&&elem.offsetHeight!=undefined)
        vis.display = (elem.offsetWidth!=0&&elem.offsetHeight!=0)?'block':'none';
    vis.display = (vis.display==''||vis.display=='block')?'none':'block';
}

function showSearch(whichLayer) {
    showNews(whichLayer);
    if(whichLayer == "advancedSearchForm"){
        showNews("basicSearchForm");
        document.getElementById("searchDecision").innerHTML = "<span class=searchDecision><a href=javascript:showSearch('basicSearchForm');>Basic Search</a></span>";
    }else {
        showNews("advancedSearchForm");
        document.getElementById("searchDecision").innerHTML = "<span class=searchDecision><a href=javascript:showSearch('advancedSearchForm');>Advanced Search</a></span>";
    }
    }
    
 function sdc_confirm() {
    if (confirm("Are you sure?")) {
       return true;
    } else {
       alert("cancel");
       return false;
    }

 }
 
 function updateDateField(commaList){
	 
	 return true; 	// jquery doesn't need it. Redmine 31283
	 
     /*var array = commaList.split(",");
     for (var x = 0; x < array.length; x++) {
        var d = document.getElementById(array[x]+"Input");
        if (!d == null) {
            var dvalue = d.value;
            if (dvalue.search(/:/) < 0) {
              dvalue = dvalue.replace(/-/g,'/');
              d.value = dvalue;
            }
        }
     }
     return true;*/
}   
    
function trim(str){
    var trimmed = str.replace(/^\s+|\s+$/g, '') ;
    return trimmed;
}

function verifyDates(day,month,year,time,admin) {
    var now = new Date();
    var today = new Date(now.getFullYear(),now.getMonth(),now.getDate());
    var future = new Date(year,(month -1),day);
    
    if (future.getTime() < today.getTime() ) {
        alert("The Date can not be earlier than todays Date of " 
        + (today.getMonth() + 1) + "/" + today.getDate()
        + "/" + today.getFullYear() );
        return false;
    } else if (future.getTime() == today.getTime() ) {   
        //only need to verify six hours if the same date
        //and not an admin
        if(admin != true) {
            return verifyTime(time.value,time);
        } else {
            return verifyTimeFields(time,time);
        }    
    } else {
        return verifyTimeFields(time,time);
    }
}

function verifyTimeFields(time,field) {
    var wm = " Time must be in military format i.e. HH:MM";
    //var time = time.value;
    time = time.replace(":","");
    if(time.length < 3 || time.length > 4) {
        return wm;
    } else if (time.length == 3) {
        time = "0" + time;
    }
    var timeArr = new Array()
    timeArr[0] = (time.substring(0,2));
    timeArr[1] = (time.substring(2));
    var fut_hour = timeArr[0];
    var fut_mins = timeArr[1];
        
    if(fut_hour != "" && fut_mins != "" ) { 
        if (fut_hour < 24 && fut_mins < 60) {
            field.value = fut_hour +":"+ fut_mins;
        } else {
            return wm;
        }
    }else {
        return wm;
    }
}

function showMessages(messages)
{
    var messageHTML="";
    if(messages.length > 0)
    {
        messageHTML = "The following errors must be fixed prior to submission:\n\n";
        for(var i=0;i < messages.length;i++)
        {
            messageHTML += messages[i]+"\n";
        }
        messages.length = 0;
	messageHTML+="\n";
    }
    alert(messageHTML);
}

function showHTMLMessages()
{
    var messageHTML="";
    if(messages.length > 0)
    {
        messageHTML = "The following errors must be fixed prior to submission:<br/><br/>";
        for(var i=0;i < messages.length;i++)
        {
            messageHTML += messages[i]+"<br/>";
        }
        messages.length = 0;
	messageHTML+="<br/>";
    }
    document.getElementById("messages").innerHTML = messageHTML;
    var loc = window.location+"";
    var end = loc.indexOf(/\#/);
    currentLoc = loc.substring(0,end);
    window.location = currentLoc+"#topOfPage";
}

function verifyTime(time,field) {
    //3,600,000 milliseconds in an hour
    //60,000 milliseconds in a minute
    
    var d = new Date();
    //Get 6 hours in milliseconds
    var sixHours = 3600000 * 6;
    //Get Current Day and Time plus 6 hours for warning messages
    var nd = new Date(d.getTime() + sixHours);
    var tempMin = ((nd.getMinutes() < 10) ? "0" : "") + nd.getMinutes();
    var timewm = "Time must be in military format i.e. HH:MM";
    var wm = "You must schedule a test after " 
            + nd.getHours() 
            + ":" + tempMin
            + " on " + (nd.getMonth() + 1) 
            +"/" + nd.getDate() +"/"
            + nd.getFullYear();
    
    //Get future Hour and Minutes in milliseconds
    time = time.replace(":","");
    if(time.length < 3 || time.length > 4) {
        alert(timewm);
        return false;
    } else if (time.length == 3) {
        time = "0" + time;
    }
    var timeArr = new Array()
    timeArr[0] = (time.substring(0,2));
    timeArr[1] = (time.substring(2));
    var fut_hour = timeArr[0];
    var fut_mins = timeArr[1];
        
    if(fut_hour != "" && fut_mins != "" ) { 
        if (fut_hour < 24 && fut_mins < 60) {
            field.value = fut_hour +":"+ fut_mins;
        }else {
            alert(timewm);
            return false;
        }
    }else {
        alert(timewm);
        return false;
    }
    var fut_time;
    fut_time = fut_hour * 60; 
    fut_time += parseInt(fut_mins, 10);
    fut_time = parseInt(fut_time) * 60000;
    
            //Get current Hour and Minutes in milliseconds
            var cur_hour = d.getHours();
            var cur_mins = d.getMinutes();
            var cur_time = cur_hour * 60;
            cur_time = parseInt(cur_time) + parseInt(cur_mins,10); 
            cur_time = parseInt(cur_time) * 60000;
           //Get 24 hours in milliseconds;
            var fullDay = 3600000 * 24;
           //Get current milliseconds plus 6 hour milliseconds
            var cur_time_plus6 = cur_time + sixHours;
           //Add 24 hours if needed
            if (cur_time_plus6 >= fullDay) {
                fut_time = fut_time + fullDay;
            }
            if (fut_time >= cur_time_plus6) {
                return true;
            }else {
                alert(wm);
                return false;
            }
  }

function validateCommercialTimes(pre) {
    var button = document.getElementById(pre+"SubmitButton");
    if(button != null) {
        button.disabled = true;
    }    
    var startField = document.getElementById(pre+"StartTime");
    var endField = document.getElementById(pre+"EndTime");
    var wm = " Time Can Not Be Left Blank";
    var messages = new Array();
    var x = messages.length;
    
    if(startField.value != "") {
        x = messages.length;
        var temp = verifyTimeFields(startField.value,startField);
        if (temp != null){
            messages[x] = "Start" + temp;
        }    
    } else {
        x = messages.length;
        messages[x] = "Start" + wm;
    }
    
    if(endField.value != ""){
        x = messages.length;
        var temp = verifyTimeFields(endField.value,endField);
        if (temp != null){
            messages[x] = "End" + temp;
        }  
    } else {
        x = messages.length;
        messages[x] = "End" + wm;
    }
    if(messages.length == 0) {
        return true;
    } else {
        showMessages(messages);
        if(button != null) {
            button.disabled = false;
        }
        return false;
    }   
}

/**
 * Usage:  onkeypress="return(numberOnly(event));"
 * @param e
 * @return
 */
function numberOnly(e) {
	var key = '';
	var strCheck = '0123456789';
	
	var whichCode = (window.Event) ? e.which : e.keyCode;
	if (whichCode == 13 || whichCode == 8 || whichCode == 9) return true;  // Enter, Backspace, tab (not work in Firefox)
	key = String.fromCharCode(whichCode);  // Get key value from key code
	if (strCheck.indexOf(key) == -1) return false;  // Not a valid key
	
	return true;
}

function loadPhone(commaList) {
    var array = commaList.split(",");
    for (var i = 0; i < array.length; i++) {
    	var obj = document.getElementById(array[i]);
    	var phone1 = document.getElementById(array[i]+"1");
    	var phone2 = document.getElementById(array[i]+"2");
    	var phone3 = document.getElementById(array[i]+"3");
    	// Remove any dashes that may have been stored in number.
    	var phone = obj.value.replace(/-/g,"");

    	// This series of ifs will load the different phone pieces even if partials
    	if (phone.length > 0) {
    		if (phone.length == 10) {
    			phone1.value = phone.substring(0, 3);
				phone2.value = phone.substring(3, 6);
				phone3.value = phone.substring(6, 10);
    		}else
    		if (phone.length == 7) {
    			phone1.value = "";
				phone2.value = phone.substring(0, 3);
				phone3.value = phone.substring(3, 7);
    		}else
    		if (phone.length > 7) {
    			phone1.value = phone.substring(0, 3);
				phone2.value = phone.substring(3, 6);
				phone3.value = phone.substring(6, phone.length);
    		}else
    		if (phone.length > 3){
    			phone1.value = phone.substring(0, 3);
				phone2.value = phone.substring(3, phone.length);
				phone3.value = "";
    		}else{
    			phone1.value = phone.substring(0, phone.length);
				phone2.value = "";
				phone3.value = "";
    		}
    	}
    }
}

function updatePhone(commaList) {
    var array = commaList.split(",");
    for (var i = 0; i < array.length; i++) {
    	var phone = document.getElementById(array[i]);
    	var phone1 = document.getElementById(array[i]+"1");
    	var phone2 = document.getElementById(array[i]+"2");
    	var phone3 = document.getElementById(array[i]+"3");
    	
    	if (phone1.value.length == 3) {
    		phone.value = phone1.value + "-" + phone2.value + "-" + phone3.value;
    	}
    }
}

function phoneNotEmpty(obj) {
	var phone1 = document.getElementById(obj+"1").value;
	var phone2 = document.getElementById(obj+"2").value;
	var phone3 = document.getElementById(obj+"3").value;
	if (phone1.length > 0 || phone2.length > 0 || phone3.length > 0) {
		return true;
	}else{
		return false;
	}
}

function validatePhone(obj) {
	var phone1 = document.getElementById(obj+"1").value;
	var phone2 = document.getElementById(obj+"2").value;
	var phone3 = document.getElementById(obj+"3").value;
	var isPhone1Numeric = isNumeric(phone1);
	var isPhone2Numeric = isNumeric(phone2);
	var isPhone3Numeric = isNumeric(phone3);
	var rval = true;
	if (isPhone1Numeric && isPhone2Numeric && isPhone3Numeric) {
		if (phone1.length == 3 && phone2.length == 3 && phone3.length == 4) {
		}else{
			rval = false;
		}
	}else{
		rval = false;
	}
	return rval;
}

function loadDate(commaList) {
    var array = commaList.split(",");

    for (var i = 0; i < array.length; i++) {
    	var dateField = document.getElementById(array[i]);
    	var dateField1 = document.getElementById(array[i]+"1");
    	var dateField2 = document.getElementById(array[i]+"2");
    	var dateField3 = document.getElementById(array[i]+"3");

    	if (dateField.value.length == 10) {
	    	dateField1.value = dateField.value.substring(0, 2);
	    	dateField2.value = dateField.value.substring(3, 5);
	    	dateField3.value = dateField.value.substring(6, 10);
    	} else {
    		var slash = dateField.value.indexOf("/");
    		var lastSlash = dateField.value.lastIndexOf("/");
    		dateField1.value = dateField.value.substring(0, slash);
    		if (dateField1.value.length == 1) dateField1.value = "0" + dateField1.value;
    		dateField2.value = dateField.value.substring(slash + 1, lastSlash);
    		if (dateField2.value.length == 1) dateField2.value = "0" + dateField2.value;
    		dateField3.value = dateField.value.substring(lastSlash + 1);
    		if (dateField3.value.length == 2) dateField3.value = "19" + dateField3.value;	// works only 1999 or less
    	}
    }
}

function updateDate(commaList) {
    var array = commaList.split(",");

    for (var i = 0; i < array.length; i++) {
    	var dateField = document.getElementById(array[i]);
    	var dateField1 = document.getElementById(array[i]+"1");
    	var dateField2 = document.getElementById(array[i]+"2");
    	var dateField3 = document.getElementById(array[i]+"3");
    	    	
    	dateField.value = dateField1.value + "/" + dateField2.value + "/"  + dateField3.value;  
    }
}

/**
 * Formats date field to format mm/dd/yyyy. Ex: 1/1/80 becomes 01/01/1980
 * @param fieldId
 * @return
 */
function formatDate(fieldId) {
	
	var dateField = document.getElementById(fieldId);

	if (dateField.value.length == 10) {	
		// do nothing
	} else {		// convert to format mm/dd/yyyy
		slash = dateField.value.indexOf("/");
		lastSlash = dateField.value.lastIndexOf("/");
		
		dateField1 = dateField.value.substring(0, slash);
		if (dateField1.length == 1) {
			dateField1 = "0" + dateField1 + "/";
		} else {
			dateField1 = dateField1 + "/";
		}
		
		dateField2 = dateField.value.substring(slash + 1, lastSlash);
		if (dateField2.length == 1) {
			dateField2 = "0" + dateField2 + "/";
		} else {
			dateField2 = dateField2 + "/";
		}

		dateField3 = dateField.value.substring(lastSlash + 1);
		if (dateField3.length == 2) {
			dateField3 = "19" + dateField3;
		}
		
		dateField.value = dateField1 + dateField2 + dateField3;
	}
}

function doGenerateReport() {
    okToSubmit = true;
    
    if ($("#schoolTypeSelectField").val() == '') {
    	alert("Please select School Type.");
    	return false;
    }
    
	var schools = document.getElementById("schoolSelectField");
	var schoolSelected = false;
	if (schools.options.length > 0) {
	    for (var i = 0; i < schools.options.length; ++i){
	        if (schools.options[i].selected) {
	            schoolSelected = true;
	            break;
	        }
	    }
	    if (!schoolSelected) {
	        // If the user didn't select a school than we include all schools.
	        for (var i = 0; i < schools.options.length; ++i){
	            schools.options[i].selected = true;
	        }
	    }
	} else {
		alert("School must be selected!");	// can't be
		if (okToSubmit) {
			objName = "schoolSelectField";
			okToSubmit = false;
		}
	}

	if (okToSubmit) {
		return generateReport();
	} else {
		return false;	
	}
}

function generateReport() {
    okToSubmit = true;
    var bDate = document.getElementById("reportStartDate").value;
    var eDate = document.getElementById("reportEndDate").value;
    var bDateEmpty = true;
    var eDateEmpty = true;
    var bDateValid, eDateValid;
    var alertString = "The following errors must be fixed prior to submission:<BR>";
    var msgBlock = "";
    var objName = "";

    if (bDate.length > 0) {
        bDateEmpty = false;
    }
    if (eDate.length > 0) {
        eDateEmpty = false;
    }
	bDateValid = isDate(bDate);
	eDateValid = isDate(eDate);

	if (bDateEmpty || !bDateValid || eDateEmpty || !eDateValid) {
		if (bDateEmpty) {
			alertString = alertString + "<BR>" + "Start Date is required.";
			if (okToSubmit) {
				objName = "reportStartDate";
				okToSubmit = false;
			}
		} else
		if (!bDateValid) {
			alertString = alertString + "<BR>" + "Start Date is invalid.";
			if (okToSubmit) {
				objName = "reportStartDate";
				okToSubmit = false;
			}
		}
		if (eDateEmpty) {
			alertString = alertString + "<BR>" + "End Date is required.";
			if (okToSubmit) {
				objName = "reportEndDate";
				okToSubmit = false;
			}
		} else
		if (!eDateValid) {
			alertString = alertString + "<BR>" + "End Date is invalid.";
			if (okToSubmit) {
				objName = "reportEndDate";
				okToSubmit = false;
			}
		}
    } else {
		// Is End Date before begin date?
		if (compareDates(eDate, bDate) < 0) {
			alertString = alertString + "<BR>" + "End Date is before Start Date.";
			if(okToSubmit) {
				objName = "reportEndDate";
				okToSubmit = false;
			}
		}
    }

	if (okToSubmit) {
		document.getElementById("messageDIV").innerHTML = "&nbsp;";
	    redirectOutput(document.searchForm);
	} else {
		msgBlock = '<div class="errorText">';
		msgBlock = msgBlock + alertString;
		msgBlock = msgBlock + '</div>';
		document.getElementById("messageDIV").innerHTML = msgBlock;
		document.getElementById(objName).focus();
	}
	return(okToSubmit);
}

/**
 * Redirects form submit to a new window.
 * @param myForm
 * @return
 */
function redirectOutput(myForm) {
    var now = new Date();
    var timeNow = now.getTime();
    var name = "report_"+timeNow;
	window.open("about:blank", name, "toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,status=yes,location=yes");
	myForm.target = name;
	myForm.submit();
	return true;
}

/**
 * Redirects form to a new window.
 * @param myForm
 * @return
 */
function redirectWindow(myForm) {
    var now = new Date();
    var timeNow = now.getTime();
    var name = "report_"+timeNow;
	window.open("about:blank", name, "toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,status=yes,location=yes");
	myForm.target = name;
	return true;
}

/** Limits max length of characters in text area box. Called from onkeyup and onkeydown events */
function textCounter(fieldId, counterId, maxlimit) {
	var field = document.getElementById(fieldId);
	var fieldval = field.value;
	var lfcount = getLFCount(fieldval);
	
	if (navigator.appName != "Microsoft Internet Explorer") {	// Firefox returns asc(10) char for a return key, whereas IE returns asc(13) and asc(10).
		maxlimit = maxlimit - lfcount;
	}
	
	var fieldLen = fieldval.length;
	
	if (fieldLen > maxlimit) { // if too long...trim it!
		field.value = fieldval.substring(0, maxlimit);
	} else {	// otherwise, update 'characters left' counter
		var countDown = maxlimit - fieldLen;
		document.getElementById(counterId).firstChild.nodeValue = countDown;
	}
}

/** Displays initial count down number when loading page */
function showInitCount(fieldId, counterId, maxlimit) {
	
	var field = document.getElementById(fieldId);
	var fieldLen = 0;
	if (navigator.appName == "Microsoft Internet Explorer") {	// IE
		fieldLen = field.value.length;	
	} else {	// firefox
		fieldLen = field.value.length + getLFCount(field.value);
	}
	
	var countDown = maxlimit - fieldLen;
	document.getElementById(counterId).firstChild.nodeValue = countDown.toString();
}

/** Gets number of line feed asc(10) characters. */
function getLFCount(textval) {
	
	var lfCount = 0;
	for (var i = 1; i < textval.length; i++) {
		var a = textval.charCodeAt(i);
		if (a == 10) lfCount++;
	}
	
	return lfCount;
}


