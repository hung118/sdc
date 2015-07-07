<html>
    <head>
        <title>Create Instructor License Expiration PDF Report</title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
		
    </head>
    
    <body>
        <div class="docBody"><br/>        
            <h4>Create Instructor License Expiration PDF Report</h4>
            
            <form name="searchForm" id="searchForm" action="/sdc/PDFReport?overlap&report=instructorExpiration" method="post">
				<div id="messageDIV">&nbsp;</div>
                <table class="wwFormTable">
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
                <input type="button" name="pdfButton" id="pdfButton" value="Create PDF" onclick="generateReport();">
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
		</script>
            
    </body>
</html>


