<html>
    <head>
        <title>Create Instructor Multi BTW PDF Report</title>
		<script src='/sdc/scripts/dwr_eng.js'></script>
		<script src='/sdc/scripts/road_tester.js'></script>
		<script src="/sdc/dwr/interface/RoadTesterService.js"></script>
		
		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>

    </head>
    
    <body>
        <!-- *** Begin of report form data *** -->
        
        <div class="docBody"><br/>        
            <h4>Create Instructor Multi BTW PDF Report</h4>
            <br/>
            
            <form name="searchForm" id="searchForm" action="/sdc/PDFReport?overlap&report=instructorOver1" method="post">
				<div id="messageDIV">&nbsp;</div>
                <table class="wwFormTable">
                   	<tr>
                       	<td class="tdLabel"><label for="schoolTypeSelectField" class="label"><span class="required">*</span>School Type:</label></td>
                        <td>
                            <select name="schoolType" id="schoolTypeSelectField">
                               	<option value="1">Commercial</option>
                            </select>
                        </td>			
					</tr>
                    <tr>
                        <td class="tdLabel"><label for="schoolSelectField" class="label">School(s):</label></td>
                        <td>
                        	<div id="schoolSelectDIV">&nbsp;</div>
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
                <input type="button" name="pdfButton" id="pdfButton" value="Create PDF" onclick="doGenerateReport();">
            </form>
            
        </div>
        
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
			selectSchoolType2();
		</script>
            
    </body>
    
</html>


