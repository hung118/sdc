<#assign logic=JspTaglibs["http://struts.apache.org/tags-logic"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>${title}</title>
        <#include "/WEB-INF/includes/style.jsp"> 
        ${head}
     </head>
    
    <body>
        <#include "/WEB-INF/includes/header.jsp">  
        <br/>
        <div class="docBody">${body}
            <@logic.present role="ADMIN,THIRDPARTY,HIGHSCHOOL,COMMERCIAL">
                <#include "/WEB-INF/includes/news.jsp"> 
            </@logic.present>
        </div>
        <#include "/WEB-INF/includes/footer.jsp">  
		<p style="text-align:center; width:94%;">
			<span style="text-align:right; color:#CCCCCC; width:94%;"><#include "/version.jsp"></span>
		</p>
    </body>
    
</html>
