<%

//LOCAL HOST -- Please change this with your own information
//Also please note that the login id and password are the same and need to be in all caps

session.setAttribute("sm_user","cn=000133533,ou=Emp,ou=Gov,o=UT");
session.setAttribute("authPersonEmail","CHWARDLE@UTAH.GOV");
%>
<html>
<head>
<title>Login Page</title>
<body bgcolor="white">
<form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
  <table border="0" cellspacing="5">
    <tr>
      <th align="right">Username:</th>
      <td align="left"><input type="text" name="j_username" value="HNGUYEN@UTAH.GOV" size="45"></td>
    </tr>
    <tr>
      <th align="right">Password:</th>
      <td align="left"><input type="password" name="j_password" value="HNGUYEN@UTAH.GOV" size="45"></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="Log In"></td>
      <td align="left"><input type="reset"></td>
    </tr>
  </table>
</form>
</body>
</html>
