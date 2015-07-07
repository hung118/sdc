<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>



        <title><s:text name="student.title"/></title>
        <script type="text/javascript">
        
 function validate(){
    var retVal = true;
    return retVal;
 }
            
        </script>    
        
    </head>
    <body>
        
        <s:actionerror/>
        <s:actionmessage/>
        
        <h4><s:text name="commercial.editstudent.header"/></h4>
        <br/>
        <logic:notPresent role="GUEST">
            <div class="labelFormat">
                <s:if test="currentStudent.fileNumber != null">
                    <s:label cssClass="actionMessage" label="%{getText('student.fileNumber')}" name="currentStudent.fileNumber" />
                    <br/>
                </s:if>
                <s:if test="currentStudent.eligibilityDate != null">
                    <s:date format="MM/dd/yyyy" name="currentStudent.eligibilityDate" id="label_currentStudent.eligibilityDate"/>
                    <s:label cssClass="actionMessage" label="%{getText('student.eligibilityDate')}" value="%{label_currentStudent.eligibilityDate}"/>
                </s:if>
                <s:label label="%{getText('student.studentNumber')}" name="currentStudent.studentNumber" />
                <br/>
                <s:label label="%{getText('student.schoolName')}"  name="commercial.noClassroom" />
                <br/>
                <s:if test="currentStudent.eligibilityDate != null">
                    <s:date format="MM/dd/yyyy" name="currentStudent.eligibilityDate" id="label_currentStudent.eligibilityDate"/>
                    <s:label label="%{getText('student.eligibilityDate')}" value="%{label_currentStudent.eligibilityDate}"/>
                    <br/>
                </s:if>
                <s:label cssClass="boldText" label="%{getText('student.lastName')}" name="currentStudent.lastName" /><s:label label="%{getText('student.firstName')}" name="currentStudent.firstName" />
                <br/>
                <s:date format="MM/dd/yyyy" name="currentStudent.dob" id="label_currentStudent.dob"/>
                <s:label label="%{getText('student.dobNoFormat')}" value="%{label_currentStudent.dob}"/>
            </div>
            
            <s:if test="commercialAjaxMessages != null"> 
                <div id="ajaxMessages" class="errorMessage" style="text-align:left">
                    <s:iterator value="commercialAjaxMessages">
                        <s:property escape="false" />
                        <br/>
                    </s:iterator>
                    <s:set scope="session" id="commercialAjaxMessages" value="null"/>
                </div>
            </s:if>
            
            <div id="observationForm" style="display:block;">
                <br/>
                <h4>Observation</h4><br/>
                <s:if test="observationCompletionDate != null">
                    <div class="labelFormat">
                        <s:date format="MM/dd/yyyy" name="observationCompletionDate" id="label_observationCompletionDate"/>
                        <s:label label="%{getText('student.observationCompletionDateNoFormat')}" value="%{label_observationCompletionDate}"/> 
                    </div>
                    <br/>
                </s:if>
                <s:if test="observationList.size > 0"> 
                    <h3>
                        Observation List: 
                        <s:if test="observationHours != 0">
                            <s:if test="observationHours >= 6">
                                <span class="actionMessage">Total Observation Hours <s:property value="%{observationHours}"/> </span>
                            </s:if>
                            <s:else>
                                <span class="actionError">Total Observation Hours <s:property value="%{observationHours}"/></span>
                            </s:else>
                        </s:if>
                    </h3>
                    <br/>
                    <table>
                        <tr>
                            <th style="text-align:center;">Start Time</th>
                            <th style="text-align:center;">End Time</th>
                            <th style="text-align:center;">Instructor</th>
                            <th style="text-align:center;">Vehicle</th>
                            <%--
                <th style="text-align:center;">Remove</th>
                --%>
                        </tr>
                        <s:iterator value="observationList">
                            <tr>
                                <td style="text-align:center;">
                                    <s:date name="startTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;">
                                    <s:date name="endTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;"><s:property value="instructorFullName"/></td>
                                <td style="text-align:center;"><s:property value="vehicleFullDesc"/></td>
                                <%--
                    <td align="center">
                        [<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="removeStudent"><s:param name="studentFk" value="studentPk"/>
                                                         <s:param name="classroomPk" value="classroomPk"/>
                        </s:url>">X</a>]
                    </td> 
                    --%>
                            </tr>
                        </s:iterator>
                    </table>
                </s:if>
                <s:else>
                    <span class="actionError">No Records Available</span>
                </s:else>
            </div>
            
            
            
            <div id="trainingForm" style="display:block;">
                <br/>
                <h4><s:text name="commercial.training" /></h4><br/>
                <s:if test="trainingCompletionDate != null">
                    <div class="labelFormat">
                        <s:date format="MM/dd/yyyy" name="trainingCompletionDateCompletionDate" id="label_trainingCompletionDate"/>
                        <s:label label="%{getText('student.trainingCompletionDateNoFormat')}" value="%{label_trainingCompletionDate}"/> 
                    </div>
                    <br/>
                </s:if>
                <s:if test="trainingList.size > 0">
                    <h3><s:text name="commercial.training.list" />: 
                        <s:if test="trainingHours != 0">
                            <s:if test="trainingHours >= 18">
                                <span class="actionMessage">Total Training Hours <s:property value="%{trainingHours}"/> </span>
                            </s:if>
                            <s:else>
                                <span class="actionError">Total Training Hours <s:property value="%{trainingHours}"/></span>
                            </s:else>
                        </s:if>
                    </h3>
                    <br/>
                    <table>
                        <tr>
                            <th style="text-align:center;">Class</th>
                            <th style="text-align:center;">Start Time</th>
                            <th style="text-align:center;">End Time</th>
                            <th style="text-align:center;">Instructor</th>
                            <%--
                <th style="text-align:center;">Remove</th>
                --%>
                        </tr>
                        <s:iterator value="trainingList">
                            <tr>
                                <td style="text-align:center;"><s:property value="section"/></td>
                                <td style="text-align:center;">
                                    <s:date name="startTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;">
                                    <s:date name="endTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;"><s:property value="instructorFullName"/></td>
                                <%--
                    <td align="center">
                        [<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="removeStudent"><s:param name="studentFk" value="studentPk"/>
                                                         <s:param name="classroomPk" value="classroomPk"/>
                        </s:url>">X</a>]
                    </td> 
                    --%>
                            </tr>
                        </s:iterator>
                    </table>
                </s:if>
                <s:else>
                    <span class="actionError">No Records Available</span>
                </s:else>
            </div>
            
            
            <div id="behindTheWheelForm" style="display:block;">
                <br/>
                <h4>Behind The Wheel</h4><br/>
                <s:if test="btwCompletionDate != null">
                    <div class="labelFormat">
                        <s:date format="MM/dd/yyyy" name="btwCompletionDateCompletionDateCompletionDate" id="label_btwCompletionDateCompletionDate"/>
                        <s:label label="%{getText('student.behindWheelCompletionDateNoFormat')}" value="%{label_btwCompletionDateCompletionDate}"/> 
                    </div>
                    <br/>
                </s:if>
                <s:if test="behindTheWheelList.size > 0"> 
                    <h3>
                        Behind The Wheel List: 
                        <s:if test="behindTheWheelHours != 0">
                            <s:if test="behindTheWheelHours >= 6">
                                <span class="actionMessage">Total Behind The Wheel Hours <s:property value="%{behindTheWheelHours}"/> </span>
                            </s:if>
                            <s:else>
                                <span class="actionError">Total Behind The Wheel Hours <s:property value="%{behindTheWheelHours}"/></span>
                            </s:else>
                        </s:if>
                    </h3>
                    <br/>
                    <table>
                        <tr>
                            <th style="text-align:center;">Start Time</th>
                            <th style="text-align:center;">End Time</th>
                            <th style="text-align:center;">Instructor</th>
                            <th style="text-align:center;">Vehicle</th>
                            <%--
                <th style="text-align:center;">Remove</th>
                --%>
                        </tr>
                        <s:iterator value="behindTheWheelList">
                            <tr>
                                <td style="text-align:center;">
                                    <s:date name="startTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;">
                                    <s:date name="endTime" format="MM/dd/yyyy  hh:mm a" />
                                </td>
                                <td style="text-align:center;"><s:property value="instructorFullName"/></td>
                                <td style="text-align:center;"><s:property value="vehicleFullDesc"/></td>
                                <%--
                    <td align="center">
                        [<a onclick="return confirm('Are You Sure You Want to Remove This Time')" href="<s:url action="removeStudent"><s:param name="studentFk" value="studentPk"/>
                                                         <s:param name="classroomPk" value="classroomPk"/>
                        </s:url>">X</a>]
                    </td> 
                    --%>
                            </tr>
                        </s:iterator>
                    </table>
                </s:if>
                <s:else>
                    <span class="actionError">No Records Available</span>
                </s:else>
            </div>
            
        </logic:notPresent>
        
        <button id="show1" style="display:none"/> 
    </body>
    
    
</html>
