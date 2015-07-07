<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><s:text name="index.title"/></title>
    </head>
    <body>
        <br/>        
        <h1><s:text name="registration.merge.title"/></h1>
        <br/>
        
        <s:form name="editForm" action="Registration_save" >
            <s:label label="%{getText('registration.driversLicenseNumber')}" name="person.driversLicenseNumber" />
            <s:label label="%{getText('registration.driversLicenseState')}" 
                      name="person.driversLicenseState" />
            <s:label label="%{getText('registration.instructornum')}" name="person.instructorNum"/>
            <s:label label="%{getText('registration.firstName')}" name="person.firstName"/>
            <s:label label="%{getText('registration.middleName')}" name="person.middleName"/>
            <s:label label="%{getText('registration.lastName')}" name="person.lastName"/>
            <s:label label="%{getText('registration.suffix')}" name="person.suffix"/>
            
			<tr>
				<td>Date of Birth:</td>
				<td>
					<s:text name="format.date">
						<s:param value="person.dob"/>
					</s:text>
				</td>
			</tr>

            <s:label label="%{getText('registration.address1')}" name="person.address1" />
            <s:label label="%{getText('registration.address2')}" name="person.address2"/>
            <s:label label="%{getText('registration.city')}" name="person.city"/>
            <s:label label="%{getText('registration.state')}" 
                      name="person.state" />
            <s:label label="%{getText('registration.zip')}" name="person.zip"/>
            <s:label label="%{getText('registration.businessPhone')}" name="person.businessPhone"/>
            <s:label label="%{getText('registration.homePhone')}" name="person.homePhone"/>            
            <s:label label="%{getText('registration.email')}" name="person.email" />
            <s:submit value="%{getText('registration.merge')}" />
            <s:hidden name="person.personPk"/>
            <s:hidden name="person.updatedBy" value="%{updatedEmail}"/>
            <s:hidden name="person.dn"/>
        </s:form>
        
    </body>
    
    
</html>
