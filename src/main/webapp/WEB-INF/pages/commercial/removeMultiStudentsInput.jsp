<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title><s:text name="commercial.studentremove.header"/></title>
		
		<script type="text/javascript" src="/sdc/scripts/jquery-1.6.4.min.js"></script>
		
		<script type="text/javascript">
			function checkAll() {
				var total = 0;
				var field = document.getElementById("selectAll");
				if(field.checked == true) {
					var els=document.getElementsByTagName('input');
					for(var i=0; i< els.length; i++) {
						if(els[i].name && els[i].name.match('studentArray')) {
							els[i].checked = true;
							total++;
						}
					}
					$("#checkAllId").text(total + " of " + total + " were selected for this class.");
				} else {
					var els=document.getElementsByTagName('input');
					for(var i=0; i<els.length; i++) {
						if(els[i].name && els[i].name.match('studentArray')) {
							if (els[i].checked == true) els[i].checked = false;
							total++;
						}
					}
					$("#checkAllId").text("0 of " + total + " were selected for this class.");
				}
			}
			
			function checkEach() {
				var els=document.getElementsByTagName('input');
				var total = 0;
				var totalSelected = 0;
				for(var i=0; i<els.length; i++) {
					if(els[i].name && els[i].name.match('studentArray')) {
						total++;
						if (els[i].checked == true) {
							totalSelected++;
						}
					}
				}
				$("#checkAllId").text(totalSelected + " of " + total + " were selected for this class.");
			}
			
			function removeStudentsValidate() {				   	
				var count = 0;
				var els = document.getElementsByTagName('input');
				for(var i=0;i<els.length;i++) {
					if(els[i].name && els[i].name.match('studentArray')) {
						if (els[i].checked == true){
							count++;
						}
				   	}
				}   
				   
				if (count == 0) {
				  alert("At least One Student Should Be Selected");
				  return false;
				} else {
		    		if (confirm("Are you sure you want to delete these students?")) {
		    			return true;
		    		} else {
		    			return false;
		    		}
				}
			}
			
		</script>
    </head>
    <body>
        <br/>
        <h4>SDC Delete Students from <s:property value="currentClassroom.getSchoolName()" /> - <s:property value="currentClassroom.getAlias()" /></h4>
		<br/>        
        <s:actionerror/>
        <s:actionmessage/>
        <h2>
            <s:url id="url" namespace="/commercial" action="editClassroom-">
            <s:param name="classroomPk" value="classroomPk"/>
        </s:url>
        <s:a href="%{url}"><img src="/sdc/images/btn_classroom.gif" alt="Back To <s:text name="commercial.classroom"/>" /></s:a>
        </h2>
        <br/>
        <s:form id="removeStudentsForm" name="editForm" namespace="/commercial" action="removeMultiStudents" >
            <tr>
				<td>
            		<input type="checkbox" id="selectAll" onClick="checkAll()"/> Select/Unselect All
            	</td>
            	<td>
            		<span id="checkAllId" style="color:red">0 of <s:property value="studentList.size()"/> were selected for this class.</span>
            	</td>
            </tr>

            <s:checkboxlist id="trainingArray" label="%{getText('student.studentList')}" list="studentList" listKey="studentPk" listValue="studentFullName" name="studentArray" onclick="checkEach()" />
            <s:hidden name="classroomPk"/>
            <s:submit onclick="return removeStudentsValidate()" value="%{getText('submit')}"/>
            </s:form>
    </body>
</html>
