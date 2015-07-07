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
            <s:url id="url" namespace="/commercial" action="editStudent-">
                <s:param name="classroomPk" value="classroomPk"/>
                <s:param name="studentPk" value="studentPk"/>
            </s:url>
            <s:a href="%{url}">Back To Student</s:a>
        </h2>
        <br/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="insertObservationCompletion">
                <s:token />
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Date</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:date format="MM/dd/yyyy" name="observationCompletionDate" id="observationCompletionDateFormat"/>
                                        <s:textfield value="%{observationCompletionDateFormat}" name="observationCompletionDate" id="observationCompletionDate" cssClass="datepicker" maxlength="10"/>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <s:submit value="%{getText('submit')}" />
                <s:hidden name="classroomPk"/>
                <s:hidden name="studentPk"/>
                <s:hidden name="fileNumber" />
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