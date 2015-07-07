<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<head>
    <title><s:text name="index.title"/></title>

	<!-- jquery - datepicker -->
	<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
	<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
	<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
	<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
	
</head>
<body>
<br/>
<h4><s:text name="index.heading"/></h4>
<br/>
<s:actionerror/>
<s:actionmessage/>

<div id="advancedSearchForm" style="display:block;">
    <s:form namespace="/thirdparty" action="dbSearch">
        <s:textfield label="%{getText('student.firstName')}" name="currentStudent.firstName" required="true"/>
        <s:textfield label="%{getText('student.lastName')}" name="currentStudent.lastName" required="true"/>
        <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="dobFormat"/>
        <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="currentStudent.dob" id="dob" cssClass="datepicker" maxlength="10"/>
        
        <logic:notPresent role="ADMIN">
        <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="highSchoolList" listKey="schoolPk" listValue="schoolName"/>
        </logic:notPresent>
        <s:submit value="%{getText('student.submit')}" />
    </s:form>
</div>
<br/>
<div id="searchDecision">
    <span class="searchDecision">
    <s:url id="url" namespace="/thirdparty" action="studentNumberSearch" includeParams="none"/><s:a href="%{url}">Basic Search</s:a>
    </span>
    </div>
<br/>
<s:url id="url" namespace="/student" action="create" includeParams="none"/><s:a href="%{url}"><s:text name="student.createStudentRecord"/></s:a>

<br/><br/>

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