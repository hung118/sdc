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

<%-- set session param to null --%>
<s:if test="commercialAjaxMessages != null">
    <s:set scope="session" id="commercialAjaxMessages" value="null"/>
</s:if>

<div id="advancedSearchForm" style="display:block;">
    <s:form namespace="/commercial" action="dobSearch">
        <s:textfield label="%{getText('student.firstName')}" name="currentStudent.firstName" required="true"/>
        <s:textfield label="%{getText('student.lastName')}" name="currentStudent.lastName" required="true"/>
        <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="dobFormat"/>
        <s:textfield label="%{getText('student.dob')}" value="%{dobFormat}" name="currentStudent.dob" id="dob" cssClass="datepicker" maxlength="10"/>
        <logic:notPresent role="ADMIN">
        <s:select label="%{getText('student.schoolName')}" name="currentStudent.schoolFk" list="commercialSchoolList" listKey="schoolPk" listValue="schoolName"/>
        </logic:notPresent>
        <s:submit value="%{getText('student.submit')}" onclick="return updateDateField('dob');" />
        <%-- Need this because of baseStudentAction validation --%>
        <s:hidden name="classroomPk" value="0"/>
    </s:form>
</div>

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