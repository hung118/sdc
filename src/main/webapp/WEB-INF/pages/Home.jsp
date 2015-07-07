<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<body>
<br/>
<h4><s:text name="index.heading"/></h4>

<s:actionerror/>
<s:actionmessage/>

<logic:present role="GUEST">
    <div style="width:93%;">
    <div class="actionMessage">
    <s:property value="%{#session.guestMessage}"/>
    </div>
    </div>
    <br/>
</logic:present>
<logic:notPresent role="ADMIN,THIRDPARTY,HIGHSCHOOL,COMMERCIAL">
    <h3>Student Driver Certificate</h3>
    <h2>This application allows you to search/edit student driver records.</h2>
    <br/>
    <h3>Reports </h3>
    <h2>Public PDF Reports </h2>
    <br/>
    <h3>Best Viewed </h3>
    <h2>Use Internet Explorer 7.0 or Mozilla Firefox 3.x </h2>
</logic:notPresent>

<logic:present role="ADMIN">
    <br/><br/>
    Welcome to the Admin Page
    <br/>
</logic:present>

</body>


</html>