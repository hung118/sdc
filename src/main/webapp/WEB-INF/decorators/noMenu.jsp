<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>${title}</title>
        <#include "/WEB-INF/includes/style.jsp"> 
        ${head}

<style type="text/css">
div {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 13px;
	font-weight: normal;
	color: #000000;
}
.noIndentRow{
    width: 100%;
    float: left;
    padding: 15px 0px 20px 8px;
}
.rowWrapper{
	width: 100%;
	vertical-align: top;
	float: left;
	padding: 0px;
}
.rowWrapper12{
	width: 100%;
	vertical-align: top;
	float: left;
	padding: 0px 0px 12px 0px;
}
.rowWrapper40{
	width: 100%;
	vertical-align: top;
	float: left;
	padding: 0px 0px 40px 0px;
}
.singleIndentLabel{
    width: 4%;
    float: left;
    text-align: right;
    padding: 0px 0px 0px 0px;
}
.singleIndentText{
    width: 95%;
    float: left;
    line-height: 1.37em;
    padding: 0px 0px 0px 4px;
}
.doubleIndentLabel{
    width: 6%;
	float: left;
    text-align: right;
    padding: 0px 0px 0px 0px;
}
.doubleIndentText{
    width: 93%;
	float: left;
    line-height: 1.37em;
    padding: 0px 0px 0px 4px;
}
</style>

     </head>
    
    <body>
        <#include "/WEB-INF/includes/logoHeader.jsp">  
        <br/>
        <div class="docBody">${body}</div>
        <br/>
        <#include "/WEB-INF/includes/footer.jsp">  
		<p style="text-align:center; width:94%;">
			<span style="text-align:right; color:#CCCCCC; width:94%;"><#include "/version.jsp"></span>
		</p>
    </body>
    
</html>
