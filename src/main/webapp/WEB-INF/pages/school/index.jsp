<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<title><s:text name="school"/>- CRUD </title>
</head>
<body>
<h1> <s:text name="school"/> CRUD </h1>

<p>
    <ul>
        <li><s:url id="url" namespace="/school" action="list"/><s:a href="%{url}">School List</s:a></li>
        <li><s:url id="url" namespace="/school" action="edit"/><s:a href="%{url}">Create/Edit School</s:a></li>
    </ul>
</p>


</body>
</html>
