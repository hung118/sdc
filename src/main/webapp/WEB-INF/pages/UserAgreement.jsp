<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<body>
<br/>
<h4><s:text name="user.agreement.index.heading"/></h4>

<s:actionerror/>
<s:actionmessage/>

<s:form name="uaform" namespace="/" action="userAgreement">
<div style="width:900px; margin:0px; padding:0px;">
	<div class="noIndentRow">
		Access to this site is limited to those persons or entities who have been authorized by the
		Utah Driver License Division.
	</div>
	<div class="rowWrapper12">
		<div class="singleIndentLabel">1.</div>
		<div class="singleIndentText">
			The SDC system will be used pursuant to U.C.A. 53-3-501 - 510 and Utah Administrative
			Rules R708-2, R708-37 (Commercial Driving Schools) and R277-746 (High School Driver Education
			Instructors).
		</div>
	</div>
	<div class="rowWrapper12">
		<div class="singleIndentLabel">2.</div>
		<div class="singleIndentText">
			Notice is provided that both Utah and federal law authorize the imposition of administrative, criminal
			and civil penalties against any person or entity for the violation of law or rule.
		</div>
	</div>
	<div class="rowWrapper12">
		<div class="singleIndentLabel">3.</div>
		<div class="singleIndentText">
			Information stored in the SDC program is used strictly for the storage of driver education and testing
			records.  Any information received by the school from the SDC program is considered confidential information
			and may only be used for the completion of driver education and testing.
		</div>
	</div>
	<div class="rowWrapper40">
		<div class="singleIndentLabel">4.</div>
		<div class="singleIndentText">
			By using this site you are agreeing to the following terms:
		</div>
		<div class="doubleIndentLabel">a.</div>
		<div class="doubleIndentText">
			User will not knowingly falsify any records entered into the system.
		</div>
		<div class="doubleIndentLabel">b.</div>
		<div class="doubleIndentText">
			SDC Program shall only be used for the storage of driver education information.
		</div>
		<div class="doubleIndentLabel">c.</div>
		<div class="doubleIndentText">
			Inappropriate use could result in the revocation of your school or instructor license
			and/or educator license and employment.
		</div>
		<div class="doubleIndentLabel">d.</div>
		<div class="doubleIndentText">
			User is responsible for all activity that occurs on their user account.
		</div>
		<div class="doubleIndentLabel">e.</div>
		<div class="doubleIndentText">
			Information entered into the program is true and correct to the best of my knowledge.
		</div>
		<div class="doubleIndentLabel">f.</div>
		<div class="doubleIndentText">
			I will not disseminate or disclose any information to any person or for any other purpose,
			including advertising or solicitation purposes.
		</div>
	</div>
	<div style="width: 100%; padding-bottom:20px;">
		<center>
			<span class="searchDecision" style="margin-right:25px;">
	        	<a href="<%=request.getContextPath()%>/userAgreementAgree.do">I Agree</a>
	    	</span>
			<span class="searchDecision">
	        	<a href="<%=request.getContextPath()%>/userAgreementDisagree.do">I Do Not Agree</a>
	    	</span>
		</center>
	</div>
</div>
</s:form>
</body>
</html>
