<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <title><s:text name="news.index.title"/></title>

        <script type="text/javascript">
function returnToList()
{
	document.editForm.action = "list.do";
	document.editForm.submit();
}
		</script>
    </head>
    <body>
        <br/>
        <h4><s:text name="news.edit.title"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <s:form name="editForm" action="save" >
<s:if test='%{editAction != null && editAction == "Y"}'>
	        <s:a href="javascript:returnToList();">
	        	<img src="/sdc/images/btn_newslist.gif" alt="Back To News Item List" />
	        </s:a>
</s:if>
    	    <br/>
        	<br/>
			<s:textfield label="%{getText('news.newsId')}" name="currentNews.newsPk" cssClass="readonly" readonly="true"/>
			<s:textfield label="%{getText('news.description')}" name="currentNews.description" size="45" maxLength="100"/>
			<s:textarea label="%{getText('news.news')}" name="currentNews.news" id="newsId" cols="80" rows="5"  onkeydown="textCounter(this.id,'counterId',4096);" onkeyup="textCounter(this.id,'counterId',4096);" />
			<tr>
				<td colspan="2" style="text-align:right;">
					<b><span id="counterId">4096</span></b> Remaining Characters
				</td>
			</tr>
			<s:select label="%{getText('news.role')}" headerKey="0" headerValue="ALL USERS" name="currentNews.roleTypesFk" multiple="false" list="allRolesList" listKey="roleTypesPk" listValue="description"/>
			<s:date id="date" format="MM/dd/yyyy"  name="currentNews.timestamp" />
			<s:textfield label="%{getText('news.timestamp')}"  value="%{date}" cssClass="readonly" readonly="true"/>
			<s:submit value="%{getText('news.save')}"  />
        	<s:hidden name="searchDescription"/>
        	<s:hidden name="searchRoleTypesFk"/>
        	<s:hidden name="activeFlag"/>
        	<s:hidden name="editAction"/>
        </s:form>
        
		<script type="text/javascript">
			showInitCount('newsId','counterId',4096);
		</script>
		    
    </body>
</html>
