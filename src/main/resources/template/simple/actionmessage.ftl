<#if (actionMessages?exists && actionMessages?size > 0)>
	<ul class="messageList">
		<#list actionMessages as message>
			<li><span class="actionMessage">${message}</span></li>
		</#list>
	</ul>
</#if>
