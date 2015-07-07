<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<title><s:text name="news"/>- CRUD </title>
</head>
<body>
<h1> CRUD </h1>

<p>
    <ul>
        <li><s:url id="url" namespace="/news" action="list"/><s:a href="%{url}">News List</s:a></li>
        <li><s:url id="url" namespace="/news" action="edit"/><s:a href="%{url}">Create/Edit News</s:a></li>
    </ul>
</p>


</body>
</html>
