<tr>
    <td colspan="2"><div <#rt/>
<#if parameters.align?exists>
    style="text-align:${parameters.align?html}"<#t/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
><#t/>
<#include "/${parameters.templateDir}/simple/submit.ftl" />
</div><#t/>
<#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" />
