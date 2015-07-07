<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="commercial.training.header"/></title>
   </head>
    <body>
        <br/>
        <h4>SDC Commercial Classroom Training for <s:label name="currentClassroom.getSchoolName()" /> - <s:label name="currentClassroom.getAlias()" /></h4>
        <br/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editClassroom-">
            	<s:param name="classroomPk" value="classroomPk"/>
        	</s:url>
        	<s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To <s:text name="commercial.classroom"/>" /></s:a>
        </h2>
        <br/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="addMultiTrainingTime" tooltipDelay="500" tooltipIconPath="/images/information.png">
                <s:token />
                <tr>
                    <td colspan="2">
                        <table class="noBorders">
                            <tr>
                                <th style="text-align:center;">Date</th>
                                <th style="text-align:center;">Start Time</th>
                                <th style="text-align:center;">End Time</th>
                                <th style="text-align:center;">Class</th>
                                <th style="text-align:center;">Instructor</th>
                                <th style="text-align:center;">Branch</th>
                            </tr>
                            <tr>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    	<s:date format="MM/dd/yyyy" name="trainingDate" id="trainingDateFormat"/>
                                    	<s:textfield value="%{trainingDateFormat}" name="trainingDate" id="trainingDate" maxlength="10"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="trainingStartTime" name="trainingStartTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;" >
                                    <table>
                                    <s:textfield maxlength="5" size="5" id="trainingEndTime" name="trainingEndTime"/>
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:textfield name="section" size="3" maxlength="3" />
                                    </table>
                                </td>
                                <td class="standardcell" style="text-align:center;">
                                    <table>
                                        <s:select name="instructorFk" list="instructorList" listKey="personPk" listValue="fullName" />
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
		                			Students: <img alt="Choose Up To 30 Students" title="Choose Up To 30 Students" src="/sdc/images/information.png"> 
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