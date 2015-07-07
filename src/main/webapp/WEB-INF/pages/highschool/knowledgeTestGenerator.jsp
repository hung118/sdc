<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="person.testGenerator"/></title>

    </head>
    <body>
        <br/>
        <h4><s:text name="person.testGenerator"/></h4>
        <s:actionerror/>
        <s:actionmessage/>
            <br/>
            <s:form name="testGeneratorForm" namespace="/student" action="generateKnowledgeTest.do">
            	<table border="1" cellpadding="5" cellspacing="5">
	        		<tr>
	        			<td>
	        				<br />
	        				<input type="radio" name="knowledgeTest" value="1" checked="checked"> 50 Questions Knowledge Test<br />
	        				<!--  input type="radio" name="knowledgeTest" value="2"> Teacher's Answer Sheet<br / -->
	        				<input type="radio" name="knowledgeTest" value="3"> Instructions for Exam
	        				<br /><br />
	        			</td>
	        		</tr>
                	<tr>
                		<td style="border-left-width: 0px;" align="right">
                			<input align="right" type="submit" id="btn_1" value="Generate"/>
                		</td>
                	</tr>
	        	</table>
            </s:form>
    </body>
    
</html>
