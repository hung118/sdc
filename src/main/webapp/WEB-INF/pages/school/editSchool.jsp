<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="index.title"/></title>
		<!-- jquery - datepicker -->
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="/sdc/scripts/jquery.maskedinput-1.2.2.min.js"></script>

		<script src='/sdc/scripts/edit_school.js'></script>
        <script type="text/javascript">
function returnToList()
{
	document.editForm.action = "list.do";
	document.editForm.submit();
}

function editBranch(branch,school)
{
	document.editForm.action = "editBranch-"+branch+"-"+school+".do";
	document.editForm.submit();
}

function deleteBranch(branch,school)
{
	document.editForm.action = "deleteBranch-"+branch+"-"+school+".do";
	document.editForm.submit();
}
		</script>
    </head>
    <body>
		<div id="messageDIV">&nbsp;</div>
        <h4><s:text name="school.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        <s:form name="editForm" action="save">
<s:if test='%{editAction != null && editAction == "Y"}'>
	        <s:a href="javascript:returnToList();">
	        	<img src="/sdc/images/btn_schoollist.gif" alt="Back To School List" />
	        </s:a>
</s:if>
    	    <br/>
        	<br/>
        	<table>
        		<tr>
        			<td style="border-width:0px;">
			            <s:textfield label="%{getText('school.schoolName')}" id="schoolName" name="currentSchool.schoolName" required="true" size="45" maxLength="45"/>
			            <s:textfield label="%{getText('school.schoolNumber')}" id="schoolNumber" name="currentSchool.schoolNumber" maxlength="4" required="true"/>
            			<s:textfield label="%{getText('school.address1')}" id="address1" name="currentSchool.address1" required="true"/>
            			<s:textfield label="%{getText('school.address2')}" id="address2" name="currentSchool.address2"/>
            			<s:textfield label="%{getText('school.city')}" id="city" name="currentSchool.city" required="true"/>
            			<s:select label="%{getText('school.state')}" id="state" name="currentSchool.state" headerKey="-1" headerValue="Select State" list="stateMap" multiple="false" required="true"/>
            			<s:textfield label="%{getText('school.zip')}" id="zip" name="currentSchool.zip" required="true"/>
			            <tr>
            				<td>Phone Number<span class="required">*</span>:</td>
            				<td>
            					<input type="text" name="businessPhone1" id="businessPhone1" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhone2" id="businessPhone2" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhone3" id="businessPhone3" value="" size="4" maxlength="4">
		            			<s:hidden name="currentSchool.businessPhone" id="businessPhone" />
            				</td>
            			</tr>
			            <tr>
            				<td>Alt Phone Number:</td>
            				<td>
            					<input type="text" name="businessPhoneAlt1" id="businessPhoneAlt1" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhoneAlt2" id="businessPhoneAlt2" value="" size="3" maxlength="3"> -
            					<input type="text" name="businessPhoneAlt3" id="businessPhoneAlt3" value="" size="4" maxlength="4">
		            			<s:hidden name="currentSchool.businessPhone2" id="businessPhoneAlt" />
            				</td>
            			</tr>
            			<s:select label="%{getText('school.homeStudy')}" id="homeStudy" name="currentSchool.homeStudy" list="#{0:'Not Allowed', 1:'Allowed'}"/>
            			<s:select label="%{getText('school.schoolType')}" id="schoolType" name="currentSchool.schoolType" onchange="selectSchoolType();" list="#{1:'Commercial', 0:'High School'}"/>

	        			<s:date format="MM/dd/yyyy" name="currentSchool.expireDate" id="expireDateFormat"/>
	        			<s:textfield label="Expiration Date" value="%{expireDateFormat}" name="currentSchool.expireDate" id="expireDate" cssClass="datepicker" maxlength="10"/>
        			</td>
        		</tr>
<s:if test="%{currentSchool.schoolPk != null}">
				<tr>
					<td colspan="2">
						<div style="text-align:right"><input type="button" name="saveButton" value="Save" onclick="do_save();"/></div>
					</td>
				</tr>
</s:if>
<s:else>
				<tr>
					<td colspan="2">
						<div style="text-align:right"><input type="button" name="saveButton" value="Create" onclick="do_save();"/></div>
					</td>
				</tr>
</s:else>
			</table>
<s:if test="currentSchool.schoolType == 1">
			<table>
				<tr>
					<td colspan="2" style="border-width:0px;">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" style="border-width:0px;">
						<span style="font-weight:bold">School Branches:</span>
						<br/>
						<br/>
						<table class="sortable">
							<thead>
								<tr>
                                	<th>Address</th>
                                	<th>City</th>
                                	<th class="sorttable_nosort" style="text-align:center;">Delete Branch</th>
                                	<th class="sorttable_nosort" style="text-align:center;">Edit Branch</th>
                            	</tr>
							</thead>
							<tbody>
	<s:if test="branchList.size > 0">
		<s:iterator value="branchList">
								<tr>
									<td><s:property value="address1"/></td>
									<td><s:property value="city"/></td>
									<td align="center">
										<a onclick="return confirm('Are You Sure You Want to Delete This Branch?')" href="javascript:deleteBranch(<s:property value="branchPk"/>,<s:property value="schoolPk"/>);">DELETE</a>
									</td>    
									<td align="center">    
										<a href="javascript:editBranch(<s:property value="branchPk"/>,<s:property value="schoolPk"/>);">EDIT</a>
									</td>
								</tr>
		</s:iterator>
	</s:if>
	<s:else>
								<tr>
									<td colspan="4" align="center">Currently no branches to display</td>
								</tr>
	</s:else>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border-width:0px;">
						<p><a href="javascript:editBranch('',<s:property value="schoolPk"/>);">Create New Branch</a></p>
					</td>
				</tr>
</s:if>
				<s:hidden name="currentSchool.schoolPk" />
				<s:hidden name="currentSchool.updatedBy"  value="%{updatedEmail}"/>
				<s:hidden name="searchSchoolType" />
				<s:hidden name="searchSchoolName" />
				<s:hidden name="searchSchoolNumber" />
				<s:hidden name="searchCity" />
				<s:hidden name="activeFlag" />
				<s:hidden name="editAction"/>
			</table>
		</s:form>
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
			
			loadPhone('businessPhone,businessPhoneAlt');
		</script>
    </body>
</html>
