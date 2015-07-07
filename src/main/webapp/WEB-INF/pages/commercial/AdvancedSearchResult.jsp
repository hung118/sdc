<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
<head>
<title><s:text name="index.title" /></title>

</head>
<body>
<br />
<h4>Student Search Result</h4>
<br />

<table>
	<thead>
		<tr>
			<th>Student Name</th>
			<th>Student Number</th>
			<th>File Number</th>
			<th>Birth Date</th>
			<th>School Name</th>
			<th>Group #</th>
			<th class="sorttable_nosort" style="text-align: center;">Edit
			Student</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="studentListSearchResult">
			<tr>
				<td><s:property value="firstName" /> <s:property
					value="middleName" /> <s:property value="lastName" /></td>
				<td><s:property value="studentNumber" /></td>
				<td><s:property value="fileNumber" /></td>
				<td><s:property value="displayDob" /></td>
				<td><s:property value="schoolName" /></td>
				<td><s:property value="classroomFk" /></td>
				<td align="center"><a
					href="<s:url action="editStudent-%{studentPk}-%{classroomFk}" />">EDIT</a>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>

<br />
<br />


</body>

</html>