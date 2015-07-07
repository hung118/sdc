<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/struts-dojo-tags" prefix="dojo" %>

<html>
    <head>
        <title><s:text name="commercial.observation.header"/></title>

		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>
       
    </head>
    <body>
        <br/>
        <h4><s:text name="commercial.observation.header"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editClassroom-">
            <s:param name="classroomPk" value="classroomPk"/>
        </s:url>
        <s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To <s:text name="commercial.classroom"/>" /></s:a>
        </h2>
        <br/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="multipleInsertObservation">
                <s:token />
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Date</th>
                                <th style="text-align:center;">Start Time</th>
                                <th style="text-align:center;">End Time</th>
                                <th style="text-align:center;">Instructor</th>
                                <th style="text-align:center;">Vehicle</th>
                                <th style="text-align:center;">Branch</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
	                                    <s:date format="MM/dd/yyyy" name="observationDate" id="observationDateFormat"/>
	                                    <s:textfield value="%{observationDateFormat}" name="observationDate" id="observationDate" cssClass="datepicker" maxlength="10"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="observationStartTime" name="observationStartTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;" >
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="observationEndTime" name="observationEndTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="instructorFk" list="instructorList" listKey="personPk" listValue="fullName" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="vehicleFk" list="vehicleList" listKey="vehiclePk" listValue="vehiclePlate" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                                <table>
                                                    <s:select name="branchFk" list="branchList" listKey="branchPk" listValue="address1" headerKey="" headerValue="Main Office" />
                                                </table>
                                        </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <s:submit onclick="return validateCommercialTimes('observation')" value="%{getText('submit')}" />
                 <s:hidden name="classroomPk"/>
                 <%-- if left as a hidden field struts will not convert this to an array  --%>
                <table class="noBorders">
                    <s:select multiple="true" id="studentArray" list="studentArray" name="studentArray" cssStyle="display:none;"/>
                </table>
                <s:hidden name="editStudentSubmit"/>
                <s:hidden name="studentPk"/>
            </s:form>
        </div>
        
        <br/>
        
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