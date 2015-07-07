<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
    <head>
        <title><s:text name="commercial.index.title"/></title>
        <script type='text/javascript' src='/sdc/dwr/interface/Classroom.js'></script>
        <script type='text/javascript' src='/sdc/dwr/engine.js'></script>
        
        <script type='text/javascript' src='/sdc/dwr/util.js'></script>
        <script type='text/javascript' src='/sdc/common/sorttable.js'></script>
        <script type="text/javascript">
            function init() {
                fillTable();
            }

var peopleCache = { };
var viewed = -1;

function fillTable(column) {
var school = dwr.util.getValue("schoolFk");

  Classroom.getAllClassroom(school,function(people) {
  // Delete all the rows except for the "pattern" row
    dwr.util.removeAllRows("peoplebody", { filter:function(tr) {
    return (tr.id != "pattern");
    }});
    
  
    // Create a new set cloned from the pattern row
    var id;
    people.sort();
    if (people.length > 0) {
    $("defaultClassroom").style.display = "none";
    $("hideable").style.display = "block";
    for (var i = 0; i < people.length; i++) {
     classroom = people[i];
      id = classroom.classroomPk;
      dwr.util.cloneNode("pattern", { idSuffix:id });
      dwr.util.setValue("tableAlias" + id, classroom.alias);
      dwr.util.setValue("tableSchool" + id, classroom.schoolName);
      
      $("pattern" + id).style.display = "";
      //$("pattern" + id).style.display = "table-row"; //doesnt work in ie
      peopleCache[id] = classroom;
     }
    } else {
    $("hideable").style.display = "none";
    $("defaultClassroom").style.display = "block";
    } 
  });
}


function editClicked(eleid) {
  // we were an id of the form "edit{id}", eg "edit42". We lookup the "42"
  var id = eleid.substring(4);
  var site = '<s:url namespace="/commercial" action="editClassroom"/>';
  site = site.slice(0,site.length - 3);
  site = site + '-' +id+ '.do';
  window.location.href=site;
}

function deleteClicked(eleid) {
  // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
  var person = peopleCache[eleid.substring(6)];
  if (confirm("Are you sure you want to delete group " + person.classroomNumber + "?")) {
    dwr.engine.beginBatch();
    Classroom.deleteClassroom(person.classroomPk);
    fillTable();
    dwr.engine.endBatch();
  }
}

function closeClicked(eleid) {
  // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
  var person = peopleCache[eleid.substring(5)];
  if (confirm("Are you sure you want to delete group: " + person.alias + "?")) {
    dwr.engine.beginBatch();
    Classroom.closeClassroom(person.classroomPk);
    fillTable();
    dwr.engine.endBatch();
  }
}
        </script> 
    </head>
    <body>
        <br/>
        <h4><s:text name="commercial.index.heading"/></h4>
        <br/>
        <s:actionerror/>
        <s:actionmessage/>
        
        <div id="commercialForm" style="display:block;">
            <s:form namespace="/commercial" action="createClassroom">
                <logic:present role="ADMIN">
                    <s:select emptyOption="true" onchange="fillTable()" label="%{getText('commercial.schoolName')}" id="schoolFk" name="schoolFk" list="commercialSchoolListAll" listKey="schoolPk" listValue="schoolName" required="true" />
                </logic:present>
                <logic:notPresent role="ADMIN">
                    <s:select onchange="fillTable()" label="%{getText('commercial.schoolName')}" id="schoolFk" name="schoolFk" list="commercialSchoolList" listKey="schoolPk" listValue="schoolName" required="true" />
                </logic:notPresent>
                <s:textfield label="%{getText('commercial.alias')}" name="alias" required="true"/>
                <%--
                <s:select label="%{getText('commercial.instructorFullName')}" name="instructorFk" list="roadTestInstructorList"  listKey="personPk" listValue="fullName"/>
                --%>
                <s:token />
                <s:submit value="%{getText('commerical.submit')}" />
            </s:form>
        </div>
        
        <br/>
        
        
        <h3>Available <s:text name="commercial.classrooms"/></h3><br/>
        <div id="defaultClassroom"><table><tr><td colspan=3>No Available <s:text name="commercial.classrooms"/> For This School</td></tr></table>
        </div>
        <div id="hideable">
        <table border="1" class="sortable">
            <thead>
                <tr>
                    <th id="col1" style="text-align:center"><s:text name="commercial.alias"/></th>
                    <th id="col2" style="text-align:center">School Name</th>
                    <th class="sorttable_nosort" style="text-align:center"><s:text name="commercial.classroom"/></th>
                </tr>
            </thead>
            <tbody id="peoplebody">
                <tr id="pattern" style="display:none;">
                    <td>
                        <span id="tableAlias"><s:text name="commercial.alias"/></span>
                    </td>
                    <td>
                        <span id="tableSchool">School Name</span>
                    </td>
                    <td align="center">
                        <div class="buttons">
                            <input type="button" id="edit" onclick="editClicked(this.id)" value="Edit"/>
                            <%-- <input type="button" id="close" onclick="closeClicked(this.id)" value="Delete"/>
                                 <input type="button" id="delete" onclick="deleteClicked(this.id)" value="Delete"/>
                            --%>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
        <script type="text/javascript">
           init();
        </script>
        
        <%-- end test --%>
        
    </body>
    
</html>