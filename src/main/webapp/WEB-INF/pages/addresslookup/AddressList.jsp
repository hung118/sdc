<%@ taglib uri="/struts-tags" prefix="s" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><s:text name="addresslookup.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript">
function onclickHREF(zip, zip4, state, county, city, sourceForm)
{
	//alert("zip = "+zip+", state = "+state+", county = "+county+", city = "+city+", src = "+sourceForm);
    if (sourceForm == "commercial")
    {
        window.opener.document.editForm.zip.value = zip;
        window.opener.document.editForm.state.value = state;
        window.opener.document.editForm.city.value = city;
        window.opener.document.editForm.city.focus();
    }

    window.close();
    return(false);
}

function closeIfSingleMatch()
{
    if (document.decisionForm.recordCount.value == 1)
    {
        onclickHREF(document.decisionForm.zip.value, document.decisionForm.state.value, document.decisionForm.county.value, document.decisionForm.city.value, '<c:out value="${src}"/>');
        window.close();
    }
}
</script>
</head>

<body onload='closeIfSingleMatch();'>
	<br/>
	<s:actionerror/>
	<s:actionmessage/>
    <h4>Zip Code <s:property value="zip"/> Lookup Results</h4>
    <br/>
	<table width="600">
		<tr>
    		<td valign="top">
				<form name='decisionForm'>
					<input type='hidden' name='recordCount' id='recordCount' value='<s:property value="addressListSize"/>'/>
<s:if test="addressList.size == 1">
	<s:iterator value="addressList">
					<input type='hidden' name='zip' id='zip' value='<s:property value="zipCode"/>'/>
					<input type='hidden' name='zip4' id='zip4' value='<s:property value="zip4AddOnHigh"/>'/>
					<input type='hidden' name='state' id='state' value='<s:property value="zipCode"/>'/>
					<input type='hidden' name='county' id='county' value='<s:property value="zipCode"/>'/>
					<input type='hidden' name='city' id='city' value='<s:property value="zipCode"/>'/>
	</s:iterator>
</s:if>
				</form>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
    				<tr>
        				<th width="12%">Zip Code</th>
        				<th width="46%">City</th>
        				<th width="34%">County</th>
        				<th width="8%">State</th>
    				</tr>
<s:iterator value="addressList">
    				<c:url var ="url" value="<s:property value='updateKeyNumber'/>">
    				</c:url>
    				<tr>
        				<td><a href="" onclick="onclickHREF('<s:property value="zipCode"/>', '<s:property value="zip4AddOnHigh"/>', '<s:property value="stateCode"/>', '<s:property value="countyName"/>', '<s:property value="cityName"/>', '<s:property value="src"/>');"><s:property value="zipCode"/>&nbsp;</a></td>
        				<td><a href="" onclick="onclickHREF('<s:property value="zipCode"/>', '<s:property value="zip4AddOnHigh"/>', '<s:property value="stateCode"/>', '<s:property value="countyName"/>', '<s:property value="cityName"/>', '<s:property value="src"/>');"><s:property value="cityName"/>&nbsp;</a></td>
        				<td><a href="" onclick="onclickHREF('<s:property value="zipCode"/>', '<s:property value="zip4AddOnHigh"/>', '<s:property value="stateCode"/>', '<s:property value="countyName"/>', '<s:property value="cityName"/>', '<s:property value="src"/>');"><s:property value="countyName"/>&nbsp;</a></td>
        				<td><a href="" onclick="onclickHREF('<s:property value="zipCode"/>', '<s:property value="zip4AddOnHigh"/>', '<s:property value="stateCode"/>', '<s:property value="countyName"/>', '<s:property value="cityName"/>', '<s:property value="src"/>');"><s:property value="stateCode"/>&nbsp;</a></td>
    				</tr>
</s:iterator>
				</table>
    		</td>
		</tr>
		<tr><td valign="bottom">&nbsp;</td></tr>
	</table>
</body>
</html>