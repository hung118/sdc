<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="commercial.observation.header"/></title>
    </head>
    <body>
        <br/>
        <h4>SDC Commercial Observation for <s:label name="currentClassroom.getSchoolName()" /> - <s:label name="currentClassroom.getAlias()" /></h4>
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
            <s:form namespace="/commercial" action="addMultiObservationTime" tooltipDelay="500" tooltipIconPath="/images/information.png">
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
                <tr>
                    <td colspan="2">
                        <table>
			                <tr>
			                	<td>
			                		&nbsp;
			                	</td>
			                	<td>
			                		<span id="checkAllId" style="color:blue"><s:property value="resultMsg" /></span>
			                	</td>
			                </tr>
		                	<tr>
		                		<td>
		                			Students: <img alt="Choose Up To 3 Students" title="Choose Up To 3 Students" src="/sdc/images/information.png"> 
		                		</td>
		                		<td>
									<s:iterator value="studentInfoFromStudentArray">
		                				<s:property value="nameDob"/> <br />
		                			</s:iterator>
		                		</td>
		                	</tr>
		                	<tr><td colspan="2">
		                		<div style="text-align:right">
		                			<input type="button" onclick="window.print()" value="Print">
								</div>
		                	</td></tr>
                        </table>
                    </td>
                </tr>
            </s:form>
        </div>
        
        <br/>
    </body>
    
</html>