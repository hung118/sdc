<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="news.index.title"/></title>

        <script type="text/javascript">
			function returnToList() {
				document.editForm.action = "listCompletionSlip.do";
				document.editForm.submit();
			}			
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="others.completionSlip.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <s:form name="editForm" action="saveCompletionSlip" >
<s:if test='%{editAction != null && editAction == "Y"}'>
	        <s:a href="javascript:returnToList();">
	        	Back to List
	        </s:a>
</s:if>
    	    <br/>
        	<br/>
			<s:textfield label="ID" name="namevalue.namevaluePk" cssClass="readonly" readonly="true"/>
			
<s:if test='%{editAction != null && editAction == "Y"}'>
			<s:textfield label="Name" name="namevalue.name" size="45" maxLength="100" cssClass="readonly" readonly="true"/>
</s:if>
<s:else>
			<s:textfield label="Name" name="namevalue.name" size="45" maxLength="100"/>
</s:else>
			
			<s:textfield label="Value" name="namevalue.value" size="45" maxLength="100"/>
			<s:textfield label="Description" name="namevalue.description" size="45" maxLength="200"/>
<s:if test='%{editAction != null && editAction == "Y"}'>
			<s:date id="date" format="MM/dd/yyyy" name="namevalue.timestamp" />
			<s:textfield label="Date" value="%{date}" cssClass="readonly" readonly="true"/>
</s:if>
			<s:submit value="Save"  />
        	<s:hidden name="editAction"/>

        </s:form>
    </body>
</html>
