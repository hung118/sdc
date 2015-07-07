<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-dojo-tags" prefix="dojo" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <dojo:head/>
        <title><s:text name="commercial.title"/></title>
        <script type='text/javascript' src='/sdc/dwr/interface/Classroom.js'></script>
        <script type='text/javascript' src='/sdc/dwr/engine.js'></script>
        <script type='text/javascript' src='/sdc/dwr/util.js'></script>
        <script type='text/javascript' src='/sdc/common/sorttable.js'></script>
        
<style type="text/css">
div img {vertical-align:top;}
div input {vertical-align:top;}
</style>

        <script type="text/javascript">
            YAHOO.namespace("example.container");
        	function init() {
		// Instantiate a Panel from markup
                	YAHOO.example.container.panel1 = new YAHOO.widget.Panel("panel1", { width:"300px",  
                                                         fixedcenter:false,  
                                                         modal:true, 
                                                         visible:false, 
                                                         constraintoviewport:true } );
                        YAHOO.example.container.panel1.render();
                        
                        YAHOO.example.container.panel2 = new YAHOO.widget.Panel("panel2", { width:"300px",  
                                                         fixedcenter:false,  
                                                         modal:true, 
                                                         visible:false, 
                                                         constraintoviewport:true } );
                        YAHOO.example.container.panel2.render();
                                                
                        YAHOO.example.container.panel5 = new YAHOO.widget.Panel("panel5", { width:"500px",  
                                                         fixedcenter:true,  
                                                         modal:true, 
                                                         visible:false, 
                                                         constraintoviewport:true } );
                        YAHOO.example.container.panel5.render();

			YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.panel1.show, YAHOO.example.container.panel1, true);
                        YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.panel2.show, YAHOO.example.container.panel2, true);   
                        YAHOO.util.Event.addListener("show5", "click", YAHOO.example.container.panel5.show, YAHOO.example.container.panel5, true);    
			}
            YAHOO.util.Event.addListener(window, "load", init);
            
            
            function addStudent() {
  var student = { id:viewed, name:null, address:null, salary:null };
  dwr.util.getValues(person);

  dwr.engine.beginBatch();
  People.setPerson(person);
  fillTable();
  dwr.engine.endBatch();
}

function validate() {
 var field = document.getElementById("fileNumber");
 var str;
  if (field != null) {
   s = trim(field.value);
   var numeric = isNumeric(s);
   if (numeric){
    field.value = s;
    return true;
   } else {    
    alert("File Number Can Only Contain Numbers");
    return false;
   }
  } else {
    return false;
  }
}

function studentNumberValidate() {
 var field = document.getElementById("studentNumber");
 var str;
  if (field != null) {
   s = trim(field.value);
   var numeric = isNumeric(s);
   if (numeric){
    field.value = s;
    return true;
   } else {    
    alert("Student Number Can Only Contain Numbers");
    return false;
   }
  } else {
    return false;
  }
}

function observationValidate() {
  var count = 0;
  var form = document.getElementById("inputObservationTime");
  var els=form.getElementsByTagName('input');
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
    } else if (count > 3) {
      alert("No More Than Three Students Should Be Selected");
      return false;
    } else {
        return true;
    }
 
}

function trainingValidate() {
   var count = 0;
   var form=document.getElementById('inputTrainingTime');
   var els=form.getElementsByTagName('input');
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
        if (count > 30) {
            alert("No More Than Thirty Students Should Be Selected");
            return false;
        }else{
            return true;
        }
    }
}

function removeStudentValidate() {
   var count = 0;
   var form=document.getElementById('removeStudentsForm');
   var els=form.getElementsByTagName('input');
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
      return true;
    }
}

function isNumeric(sText)
{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;
 
   for (var i = 0; i < sText.length && IsNumber == true; i++) { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) {
         IsNumber = false;
      }
   }
   return IsNumber;
}

function update(action)
{
	//var d1 = ;
	var d = document.editForm;
	if (action == "update") {
		<s:url action="update" id="submitAction"/>
		d.action = "<s:property value='%{submitAction}'/>";
	} else {
		<s:url action="createCommercialStudent" id="submitAction2"/>
		d.action = "<s:property value='%{submitAction2}'/>";
	}
	return true;
}
            
function checkAll()
{
	var field = document.getElementById("selectAll");
	if(field.checked == true) {
		var els=document.getElementsByTagName('input');
		for(var i=0;i<els.length;i++) {
			if(els[i].name && els[i].name.match('studentArray')) {
				els[i].checked = true;
			}
		}
	} else {
		var els=document.getElementsByTagName('input');
		for(var i=0;i<els.length;i++) {
			if(els[i].name && els[i].name.match('studentArray')) {
				if (els[i].checked == true) els[i].checked = false;
			}
		}
	}
}

function closeClicked(gname,eleid) {
	// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
	if (confirm("Are you sure you want to inactivate group: " + gname + "?")) {
		dwr.engine.beginBatch();
		Classroom.closeClassroom(eleid);
		dwr.engine.endBatch();
		location.href='<%= request.getContextPath() %>/commercial/commercialFront.do';
	}
}

function showHidden(box)
{
	var site = '<s:url namespace="/commercial" action="editClassroom-"><s:param name="classroomPk" value="classroomPk"/></s:url>';
	if (box.checked) {
		site += "&showHidden=1";
	}else{
		site += "&showHidden=0";
	}
    window.location.href=site;
}

function hideStudent(student,classroom)
{
	var site='<s:url namespace="/commercial" action="hideStudent"/>';
	site += '?studentFk='+student+'&classroomPk='+classroom;
	var box = document.getElementById("showHiddenBox");
	if (box.checked) {
		site += '&showHidden=1';
	}else{
		site += '&showHidden=0';
	}
    window.location.href=site;
}

function unhideStudent(student,classroom)
{
	var site='<s:url namespace="/commercial" action="unhideStudent"/>';
	site += '?studentFk='+student+'&classroomPk='+classroom;
	var box = document.getElementById("showHiddenBox");
	if (box.checked) {
		site += '&showHidden=1';
	}else{
		site += '&showHidden=0';
	}
    window.location.href=site;
}
</script>
    </head>
    <body>
        <%-- This is the URL of the struts action we will call in the next step --%>
        <s:url var="ajaxAddStudentNumber" namespace="/commercial" action="ajaxCallAddStudentNumber">
			<s:param name="classroomPk" value="classroomPk"/>
        </s:url>   
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <h4><s:text name="commercial.header"/></h4>
        <br/>
<logic:notPresent role="GUEST">
        <div class="greenTable">
            <table>
                <s:label cssClass="actionMessage" label="%{getText('commercial.classroomNumber')}" name="classroomNumber" />
             	<s:label cssClass="actionMessage" label="%{getText('commercial.schoolName')}" name="schoolName" />
	<s:if test="alias != null">
    	   		<s:label  cssClass="actionMessage" label="%{getText('commercial.alias')}" name="alias" />
	</s:if>
               	<tr>
	           		<td colspan="2">
               			<br/>
               			<input type="button" id="close" onclick="closeClicked('<s:property value="%{alias}" />',<s:property value="%{classroomNumber}" />)" value="Inactivate Group"/>
                	</td>
                </tr>
            </table>
        </div>
        <br/>
        <table>
			<tr>
				<td colspan="2" style="border-width:0px;">
					<div style="text-align:right;">
	<s:if test="showHidden == null || showHidden == '' || showHidden == 0">
	        			<input type="checkbox" id="showHiddenBox" onclick="showHidden(this);" />&nbsp;Display Hidden Students
	</s:if>
	<s:else>
    	    			<input type="checkbox" id="showHiddenBox" onclick="showHidden(this);" checked="true" />&nbsp;Display Hidden Students
	</s:else>
					</div>
				</td>
			</tr>
            <tr>
                <td>
          			<div><%--<button id="show1">Add Student By File Number</button> --%>
               			<a href="#"><img id="show1" src="/sdc/images/btn_filenumber.gif" alt="Add Student By File Number"/></a>
               			<br/>
               			<dojo:submit type="image" src="/sdc/images/btn_studentnumber.gif" id="show2" value="Add Student By Student Number" targets="searchStudentNumber" href="%{#ajaxAddStudentNumber}" />
               			<br/>
				        <s:url id="addMultiObservationTimeInput" namespace="/commercial" action="addMultiObservationTimeInput">
				            <s:param name="classroomPk" value="classroomPk"/>
				        </s:url> 
               			<s:a href="%{addMultiObservationTimeInput}"><img src="/sdc/images/btn_observation.gif" alt="Add Multi Observation Time"/></s:a>
               			<br/>
				        <s:url id="addMultiTrainingTimeInput" namespace="/commercial" action="addMultiTrainingTimeInput">
				            <s:param name="classroomPk" value="classroomPk"/>
				        </s:url> 
               			<s:a href="%{addMultiTrainingTimeInput}"><img src="/sdc/images/btn_training.gif" alt="Add Multi Training Time"/></s:a>
               			<br/>
               			
               			<logic:present role="ADMIN">
				        <s:url id="removeMultiStudentsInput" namespace="/commercial" action="removeMultiStudentsInput">
				            <s:param name="classroomPk" value="classroomPk"/>
				        </s:url> 
               			<s:a href="%{removeMultiStudentsInput}"><img src="/sdc/images/btn_delete_students.gif" alt="Delete Students"/></s:a>
               			<br/>
               			</logic:present>
               			
               			<s:url id="url" namespace="/commercial" action="inputCommercialStudent" includeParams="none">
                   			<s:param name="classroomPk" value="classroomPk"/>
               			</s:url>
               			<s:a href="%{url}"><img src="/sdc/images/btn_newstudent.gif" alt="Create New Student"/></s:a>
           			</div>

           			<div id="panel1">
		                <div class="hd">Add Student By File Number</div>
               			<div class="bd">
                   			<s:form onsubmit="return validate()" cssClass="noBorders" id="addStudent" action="webSearch">
                       			<s:textfield label="File Number" id="fileNumber"  name="fileNumber"/>
                       			<s:hidden name="classroomPk" />
                       			<s:submit onclick="return validate()" cssStyle="display:none"/>                    
               				</s:form>
           				</div>
						<div class="ft"></div>
					</div>
		        	<div id="panel2">
						<div class="hd">Add Student By Student Number</div>
						<div class="bd">
               				<div id='searchStudentNumber'><b>Loading...</b></div>
           				</div>
           				<div class="ft"></div>
        			</div>
	       			<div id="panel5">
           				<div class="hd">Delete Students</div>
           				<div class="bd">
               				<div id='removeStudents'><b>Loading...</b></div>
           				</div>
           				<div class="ft"></div>
        			</div>
        		</td>
        		<td style="vertical-align: top">
	<s:if test="commercialAjaxMessages != null">
       				<div id="ajaxMessages" class="errorMessage" style="text-align:left">
           				<s:iterator value="commercialAjaxMessages">
               				<s:property escape="false" />
               				<br/>
           				</s:iterator>
           				<s:set scope="session" id="commercialAjaxMessages" value="null"/>
       				</div>
	</s:if>
			        <br/>
       				<table>
       					<thead>
							<tr>
								<th>Student Name</th>
								<th>Student Number</th>
								<th>File Number</th>
								<th class="sorttable_nosort">Birth Date</th>
								<th class="sorttable_nosort" style="text-align:center;">Edit Student</th>
								<th class="sorttable_nosort" style="text-align:center;">Hide</th>
								<logic:present role="ADMIN">
									<th class="sorttable_nosort" style="text-align:center;">Delete Student</th>
								</logic:present>
							</tr>
           				</thead>
           				<tbody>
	<s:if test="studentList.size > 0">
		<s:iterator value="studentList">
							<tr>
								<td>
									<s:property value="firstName"/>
									<s:property value="middleName"/>
									<s:property value="lastName"/>
								</td>
								<td><s:property value="studentNumber"/></td>
								<td><s:property value="fileNumber"/></td>
								<td><s:property value="displayDob"/></td>
								   
								<td align="center">    
									<a href="<s:url action="editStudent-%{studentPk}-%{classroomPk}" />">EDIT</a>
								</td>
			<s:if test="hide == 0">
								<td align="center">
									<a href="javascript:hideStudent(<s:property value="studentPk"/>,<s:property value="classroomPk"/>);">HIDE</a>
								</td>
			</s:if>
			<s:else>
								<td align="center">
									<a href="javascript:unhideStudent(<s:property value="studentPk"/>,<s:property value="classroomPk"/>);">UNHIDE</a>
								</td>
			</s:else>
								<logic:present role="ADMIN">
									<td align="center">
										[<a onclick="return confirm('Are You Sure You Want to Delete This Student?')" href="<s:url action="removeStudent"><s:param name="studentFk" value="studentPk"/><s:param name="classroomPk" value="classroomPk"/></s:url>">X</a>]
									</td> 
								</logic:present>
							</tr>
		</s:iterator>
	</s:if>
	<s:else>
							<tr>
								<logic:present role="ADMIN">
									<td colspan="7" align="center">No Students In <s:text name="commercial.classroom"/></td>
								</logic:present>
								<logic:notPresent role="ADMIN">
									<td colspan="6" align="center">No Students In <s:text name="commercial.classroom"/></td>
								</logic:notPresent>
							</tr>
	</s:else>
						</tbody>
       				</table>
       			</td>
       		</tr>
		</table>
</logic:notPresent> 
    </body>
</html>
