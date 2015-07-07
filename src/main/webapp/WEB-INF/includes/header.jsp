 
<#assign logic=JspTaglibs["http://struts.apache.org/tags-logic"] />

<div style="width: 94%;">
    <div class="outerHeader">
        <div class="innerHeader"></div>
    </div>
    
<div id="sdcMenu" class="yuimenubar">
    <div class="bd">
        <ul class="first-of-type">
            <li class="yuimenubaritem first-of-type"><@s.url id="url" namespace="/" action="Home" includeParams="none"/><@s.a href="%{url}">Home</@s.a></li>
            <@logic.present role="ADMIN,HIGHSCHOOL">
            <li class="yuimenubaritem">High School
                <div id="highschool" class="yuimenu">
                    <div class="bd">                    
                        <ul>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/" action="highSchoolFront" includeParams="none"/><@s.a href="%{url}">File Number Search</@s.a></li>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/student" action="dateOfBirthSearch" includeParams="none"/><@s.a href="%{url}">Name and Date of Birth Search</@s.a></li>
                            
                            <@s.if test="#session.authPerson.knowledgeTest == true">
                            	<li class="yuimenuitem"><@s.url id="url" namespace="/student" action="knowledgeTestGenerator" includeParams="none"/><@s.a href="%{url}">Knowledge Test Generator</@s.a></li>
							</@s.if>
                        </ul>                    
                    </div>
                </div>                                        
            </li>
            </@logic.present>
             <@logic.present role="ADMIN,COMMERCIAL">
            <li class="yuimenubaritem">Commercial
                <div id="commercial" class="yuimenu">
                    <div class="bd">                    
                        <ul>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/commercial" action="commercialFront" includeParams="none"/><@s.a href="%{url}">Student Records</@s.a></li>
                            <@logic.present role="ADMIN,COMMERCIAL">
                            <li class="yuimenuitem"><@s.url id="url" namespace="/commercial" action="inputFileNumber" includeParams="none"/><@s.a href="%{url}">File Number Search</@s.a></li>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/commercial" action="inputStudentNumber" includeParams="none"/><@s.a href="%{url}">Student Number Search</@s.a></li>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/commercial" action="inputDOB" includeParams="none"/><@s.a href="%{url}">Name and Date of Birth Search</@s.a></li>
                            </@logic.present>
                        </ul>                    
                    </div>
                </div>                                        
            </li>
            </@logic.present>
             <@logic.present role="ADMIN,THIRDPARTY">
            <li class="yuimenubaritem">Road Tester
                <div id="thirdparty" class="yuimenu">
                    <div class="bd">                    
                        <ul>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/thirdparty" action="thirdPartyFront" includeParams="none"/><@s.a href="%{url}">File Number Search</@s.a></li>
                            <li class="yuimenuitem"><@s.url id="url" namespace="/thirdparty" action="dateOfBirthSearch" includeParams="none"/><@s.a href="%{url}">Name and Date of Birth Search</@s.a></li>
                        </ul>                    
                    </div>
                </div>                                        
            </li>
            </@logic.present>
            
            <li class="yuimenubaritem">Reports
                <div id="reports" class="yuimenu">
                    <div class="bd">                    
                        <ul>
                            <@logic.present role="ADMIN,HIGHSCHOOL">
                            <li class="yuimenuitem"><@s.url id="url" namespace="/reports" action='completionDatesSearch' includeParams='none'/><@s.a href="%{url}">High School Test Report</@s.a></li>
                            </@logic.present>
                            <@logic.present role="ADMIN,THIRDPARTY">
                            <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?report=initRoadTester" includeParams="none"/><@s.a href="%{url}">Road Tester Statistics</@s.a></li>
                            </@logic.present>
                            <@logic.present role="ADMIN,COMMERCIAL,THIRDPARTY">
                            <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?report=initSchoolCompletion" includeParams="none"/><@s.a href="%{url}">School Completions</@s.a></li>
                            </@logic.present>
                            <li class="yuimenuitem"><a href="/sdc/PDFReport?report=schoolList" target="_blank">Public List of Schools</a></li>
                            <li class="yuimenuitem"><a href="/sdc/PDFReport?report=testerList" target="_blank">Public List of Testers</a></li>
                            <@logic.present role="ADMIN">
                                <li class="yuimenuitem"><a href="/sdc/PDFReport?report=instructorList" target="_blank">SDC User Report</a></li>
                                <li class="yuimenuitem"><a href="/sdc/PDFReport?report=bondList" target="_blank">List of Surety Bond Companies</a></li>
                                <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?report=initInstructorExpiration" includeParams="none" /><@s.a href="%{url}">Instructor License Expiration</@s.a></li>
                                <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?report=initSchoolExpiration" includeParams="none" /><@s.a href="%{url}">School Expirations</@s.a></li>
                                <li class="yuimenuitem">Overlapping Time Reports testing<div id="overlap" class="yuimenu">
	                                <div class="bd">
	                                    <ul class="first-of-type">
                                            <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=studentOver1" includeParams="none"/><@s.a href="%{url}">Students Multi Training</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=studentOver2" includeParams="none"/><@s.a href="%{url}">Students Multi Training By Type</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver1" includeParams="none"/><@s.a href="%{url}">Instructor Multi BTW</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver2" includeParams="none"/><@s.a href="%{url}">Instructor Multi Training By Type</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver3" includeParams="none"/><@s.a href="%{url}">Instructor Multi Classroom</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver4" includeParams="none"/><@s.a href="%{url}">Training and Testing Overlap</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver5" includeParams="none"/><@s.a href="%{url}">Training over 10 hours</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver6" includeParams="none"/><@s.a href="%{url}">Testing over 10 hours</@s.a></li>
                                            <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=instructorOver7" includeParams="none"/><@s.a href="%{url}">Training and Testing over 10 hours</@s.a></li>
                                            <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=vehicleOver1" includeParams="none"/><@s.a href="%{url}">Vehicle Training Overlap</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=vehicleOver2" includeParams="none"/><@s.a href="%{url}">Vehicle Testing Overlap</@s.a></li>
	                                        <li class="yuimenuitem"><@s.url id="url" value="/PDFReport?showPage&page=testerOver1" includeParams="none"/><@s.a href="%{url}">Road Tester Overlap</@s.a></li>
	                                    </ul>            
	                                </div>
	                            </div>                    
                            </li>
                            </@logic.present>
                        </ul>                    
                    </div>
                </div>                                        
            </li>
             
            <@logic.present role="ADMIN">
                <li class="yuimenubaritem">Administration
                    <div id="administration" class="yuimenu">
                        <div class="bd">                    
                            <ul>
                                 <li class="yuimenuitem">News<div id="news" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/news" action="edit-" includeParams="none"/><@s.a href="%{url}">Create News Item</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/news" action="search" includeParams="none"/><@s.a href="%{url}">Search News Items</@s.a></li>
                                            </ul>            
                                        </div>
                                    </div>                    
                                </li>
                                <li class="yuimenuitem">Person<div id="person" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/person" action="edit-" includeParams="none"/><@s.a href="%{url}">Create User</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/person" action="search" includeParams="none"/><@s.a href="%{url}">Search Users</@s.a></li>
                                            </ul>            
                                        </div>
                                    </div>                    
                                </li>
                                <li class="yuimenuitem">School<div id="school" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/school" action="edit-" includeParams="none"/><@s.a href="%{url}">Create School</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/school" action="search" includeParams="none"/><@s.a href="%{url}">Search Schools</@s.a></li>
                                            </ul>            
                                        </div>
                                    </div>                    
                                </li>
                               <li class="yuimenuitem">Vehicle<div id="vehicle" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/vehicle" action="edit-" includeParams="none"/><@s.a href="%{url}">Create New Vehicle</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/vehicle" action="search" includeParams="none"/><@s.a href="%{url}">Search Vehicles</@s.a></li>
                                            </ul>            
                                        </div>
                                    </div>                    
                                </li>
                               <li class="yuimenuitem">Surety Bond<div id="bond" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/bond" action="edit-" includeParams="none"/><@s.a href="%{url}">Create New Bond</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/bond" action="search" includeParams="none"/><@s.a href="%{url}">Search Bonds</@s.a></li>
                                            </ul>            
                                        </div>
                                    </div>                    
                                </li>
                                <li class="yuimenuitem">Group<div id="group" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/school" action="searchGroup" includeParams="none"/><@s.a href="%{url}">Search Commercial Groups</@s.a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
       							<li class="yuimenuitem">Audit Logs<div id="auditLogs" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/reports" action="searchAuditLogs" includeParams="none"/><@s.a href="%{url}">Search Audit Logs</@s.a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
       							<li class="yuimenuitem">Others<div id="others" class="yuimenu">
                                        <div class="bd">
                                            <ul class="first-of-type">
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/others" action="listCompletionSlip" includeParams="none"/><@s.a href="%{url}">Completion Slip Hours</@s.a></li>
                                                <li class="yuimenuitem"><@s.url id="url" namespace="/others" action="editCompletionSlip" includeParams="none"/><@s.a href="%{url}">Create Completion Slip</@s.a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                            </ul>                    
                        </div>
                    </div>                                        
                </li>
            </@logic.present>
            <@logic.notPresent role="ADMIN,THIRDPARTY,HIGHSCHOOL,COMMERCIAL">
                <li class="yuimenubaritem"><@s.url id="url" namespace="/" action="Login_input" includeParams="none"/><@s.a href="%{url}">Login</@s.a></li>
            </@logic.notPresent>
            <@logic.present role="ADMIN,THIRDPARTY,HIGHSCHOOL,COMMERCIAL">
                <li class="yuimenubaritem"><@s.url id="url" namespace="/" action="Logout" includeParams="none"/><@s.a href="%{url}">Logout</@s.a></li>
            </@logic.present>
        </ul>            
    </div>
</div>
</div>
