<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<title><s:text name="logs.index.title" /></title>
<!-- jquery - datepicker -->
<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>

<script type="text/javascript">
	YAHOO.namespace("example.container");
	function init() {
		
		// Define various event handlers for Dialog
		var handleYes = function() {
			this.hide();
                                             var site = '<s:url namespace="/" action="Home"/>';
                                             window.location.href=site;
		};
		var handleNo = function() {
			this.hide();
		};

		// Instantiate the Dialog
		YAHOO.example.container.simpledialog1 = new YAHOO.widget.SimpleDialog("simpledialog1", 
		{ width: "300px",
		 fixedcenter: true,
		 visible: false,
                                      modal:true,
		 draggable: false,
		 close: true,
		 text: "Do you want to return home?",
		 icon: YAHOO.widget.SimpleDialog.ICON_HELP,
		 constraintoviewport: true,
		 buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
		  { text:"No",  handler:handleNo } ]
			 } );
		YAHOO.example.container.simpledialog1.setHeader("Return Home?");
         
                     // Render the Dialog
		YAHOO.example.container.simpledialog1.render(document.body);
                     
		YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.simpledialog1.show, YAHOO.example.container.simpledialog1, true);
                    
	}
	YAHOO.util.Event.addListener(window, "load", init);
	
	function onSubmit() {
		if (document.searchForm.fromDate.value == "" || document.searchForm.toDate.value == "") {
			alert("From Date and To Date are required!");
			return false;
		} else {
			return true;
		}
	}
</script>

</head>
<body>
<br />
<h4><s:text name="logs.search.title" /></h4>
<br />
<s:actionerror />
<s:actionmessage />

<div id="basicSearchForm" style="display: block;"><s:text
	name="logs.lookupBy" /> <br />
<br />
<s:form name="searchForm" namespace="/reports" action="listAuditLogs">
	<s:date format="MM/dd/yyyy" name="fromDate" id="fromDateFormat"/>
	<s:textfield label="%{getText('logs.fromDate')}" value="%{fromDateFormat}" name="fromDate" id="fromDate" cssClass="datepicker" maxlength="10"/>
	
	<s:date format="MM/dd/yyyy" name="toDate" id="toDateFormat"/>
	<s:textfield label="%{getText('logs.toDate')}" value="%{toDateFormat}" name="toDate" id="toDate" cssClass="datepicker" maxlength="10"/>
	
	<s:textfield id="userEmail" label="%{getText('logs.userEmail')}" name="userEmail" />

	<tr>
		<td style="border-right-width: 0px;" align="left">
			<input align="left" type="reset" id="btn_0" value="Clear" /></td>
		<td style="border-left-width: 0px;" align="right">
			<input align="right" type="submit" id="btn_1" value="Search" onclick="return onSubmit();"/></td>
	</tr>
</s:form>
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
