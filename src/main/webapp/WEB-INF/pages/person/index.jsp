<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<title><s:text name="person"/>- CRUD </title>
</head>
<body>
<h1> CRUD </h1>

<p>
    <ul>
        <li><s:url id="url" namespace="/person" action="list"/><s:a href="%{url}">Person List</s:a></li>
        <li><s:url id="url" namespace="/person" action="edit"/><s:a href="%{url}">Create/Edit Person</s:a></li>
    </ul>
</p>


</body>
</html>
