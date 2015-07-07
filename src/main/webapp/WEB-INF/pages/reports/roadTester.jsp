<html>
    <head>
        <title>Create Road Tester Statistics PDF Report</title>
		<script src='/sdc/scripts/dwr_eng.js'></script>
		<script src='/sdc/scripts/road_tester.js'></script>
		<script src="/sdc/dwr/interface/RoadTesterService.js"></script>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
		
<script type="text/javascript">
function generateReport2() {
    okToSubmit = true;
    
    if ($("#schoolTypeSelectField").val() == '') {
    	alert("Please select School Type.");
    	return false;
    }
    
	var schools = document.getElementById("schoolSelectField");
	var testers = document.getElementById("testerSelectField");
	var schoolSelected = false;
	var testerSelected = false;
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
		alertString = alertString + "<BR>" + "A School must be selected.";
		if (okToSubmit) {
			objName = "schoolSelectField";
			okToSubmit = false;
		}
	}
	if (testers.options.length > 0) {
	    for (var i = 0; i < testers.options.length; ++i){
	        if (testers.options[i].selected) {
	            testerSelected = true;
	            break;
	        }
	    }
	    if (!testerSelected) {
	        // If the user didn't select a tester than we include all testers.
	        for (var i = 0; i < testers.options.length; ++i){
	            testers.options[i].selected = true;
	        }
	    }
	} else {
		alertString = alertString + "<BR>" + "A Tester Name must be selected.";
		if (okToSubmit) {
			objName = "testerSelectField";
			okToSubmit = false;
		}
	}
    
    if (okToSubmit) {
    	return generateReport();
    } else {
    	return false;
    }
}

YAHOO.util.Event.addListener(window, "load", function() {selectSchoolType();});
</script>
    </head>
    
    <body>
        <!-- *** Begin of report form data *** -->
        
        <div class="docBody"><br/>        
            <h4>Create Road Tester Statistics PDF Report</h4>
            <br/>
            
            <form name="searchForm" id="searchForm" action="/sdc/PDFReport?report=roadTester" method="post"> 
				<div id="messageDIV">&nbsp;</div>
                <table class="wwFormTable">
                    <tr>
                        <td class="tdLabel" valign="top"><label for="schoolTypeSelectField" class="label"><span class="required">*</span>School Type:</label></td>
                        <td>
                            <select name="schoolType" id="schoolTypeSelectField" onChange="selectSchoolType();">
                               	<option value=""> </option>
                               	<option value="0">High School</option>
                               	<option value="1">Commercial</option>
                            </select>
                        </td>			
                    </tr>
                    <tr>
                        <td class="tdLabel" valign="top"><label for="schoolSelectField" class="label">School(s):</label></td>
                        <td>
                        	<div id="schoolSelectDIV">&nbsp;</div>
                        </td>			
                    </tr>
                    <tr>
                        <td class="tdLabel" valign="top"><label for="testerSelectField" class="label">Tester Name:</label></td>
                        <td>
                        	<div id="testerSelectDIV">&nbsp;</div>
                        </td>			
                    </tr>
                    <tr>
                        <td>Start Date (MM/DD/YYYY):</td>
                        <td>
                        	<input type="text" name="reportStartDate" id="reportStartDate" class="datepicker" maxlength="10">
                        </td>
                    </tr>
                    
                    <tr>
                        <td>End Date (MM/DD/YYYY):</td>
                        <td>
                        	<input type="text" name="reportEndDate" id="reportEndDate" class="datepicker" maxlength="10"/>
                        </td>
                    </tr>
                </table>
                <br/>
               	<input type="button" name="pdfButton" id="pdfButton" value="Create PDF" onclick="generateReport2();">
            </form>
        </div>
        <!-- *** End of report form data *** -->     
        
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
		</script>
    </body>
</html>


