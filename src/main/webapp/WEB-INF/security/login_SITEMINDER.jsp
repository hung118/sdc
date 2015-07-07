<!-- SITEMINDER -->
<html>
<head>
<body bgcolor="white" onLoad="document.forms[0].submit()">
<form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
<input type="hidden" name="j_username" value="<%= request.getHeader("email").toUpperCase()%>">
<input type="hidden" name="j_password" value="<%= request.getHeader("email").toUpperCase()%>">
</form>
</body>
</html>
