package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;
import gov.utah.dts.util.SendMail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class ThirdPartyStudentSearchAction extends BaseThirdPartyStudentAction implements Preparable {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(ThirdPartyStudentSearchAction.class);
    private List mergeWebStudent;
    private List mergeDbStudent;
    private List multipleClassroomList;
    private Student student;
    private List studentList;
    private Student currentStudent;
    private List studentListSearchResult;

    public String editStudent() throws Exception {
        log.debug("...ThirdPartyStudentSearchAction - editStudent");
        //if (getCurrentStudent().getFileNumber() != null) {
       // if (student.getStudentNumber() != null) {
       //    Student student = new Student();
       //    if (student.getStudentNumber() != null)
       //     log.info("FILE NUMBER PRESENT, UPDATING STUDENT RECORD WITH WEBSERVICE");
       //     webserviceUpdate();
       // }
        //set ClassroomInfo after possible webservice update so most current information is set.
        setClassroomInfo();
        return SUCCESS;
    }

    public String inputStudentNumber() throws Exception {
        log.debug("inputStudentNumber");
        return INPUT;
    }
        
    public String fileNumberSearch() throws Exception {
        log.debug("fileNumberSearch");
        return "fileNumberSearch";
    }
    
    public String dateOfBirthSearch() throws Exception {
        log.debug("dateOfBirthSearch");
        return "dateOfBirthSearch";
    }

    public void setClassroomInfo() throws Exception {
        log.debug("...ThirdParty setClassroomInfo");
        if (getCurrentStudent() != null) {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("studentFk", getCurrentStudent().getStudentPk());
            hm.put("classroomPk", getClassroomPk());
            Classroom school = getClassroomService().getSchoolInfo(hm);
            setSchoolName(school.getSchoolName());
            setHomeStudy(school.getHomeStudy());
            setSchoolPk(school.getSchoolFk());
            log.debug("school.getSchoolFk = " + school.getSchoolFk());

            //setCompletionDate();
            setObservationCompletionDate(getCurrentStudent().getObservationCompletionDate());
            setBtwCompletionDate(getCurrentStudent().getBehindWheelCompletionDate());
            setTrainingCompletionDate(getCurrentStudent().getClassroomCompletionDate());
        } else {
            log.error("################# setClassroomInfo ERROR");
        }
    }

    public Student fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for " + tryId);
        Student result = null;
        if (tryId != null) {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("studentPk", tryId);
            result = getStudentService().getStudent(hm);
        }
        log.debug("exit fetch for " + tryId);
        return result;
    }

    public String save() throws Exception {
        log.debug("save");
        return SUCCESS;
    }

    public String mergeFileNumber() throws Exception {
        log.debug("******* mergeFileNumber " + getFileNumber() +" "+ getStudentNumber());
        QueryByDLResponseType wsResponse = getQueryByDl();
        if (wsResponse != null) {
            if ("200".equals(wsResponse.getStatus())) {
                Student webStudent = parseQueryByDlResults(wsResponse, true);
                int i = getStudentService().fullUpdate(webStudent);
                setCurrentStudent(webStudent);
                setStudentPk(webStudent.getStudentPk());
            }
        }
        return SUCCESS;
    }

    public String syncFileNumber() throws Exception {
        log.debug("******* syncFileNumber " + getFileNumber());
        boolean studentMerged = false;
        boolean studentFound = false;

        QueryByDLResponseType wsResponse = getQueryByDl();
        if (wsResponse != null) {
            Student webStudent = parseQueryByDlResults(wsResponse);
            if ("200".equals(wsResponse.getStatus())) {
                if (webStudent.getStudentPk() == null) {
                    log.debug("Student Insert");
                    Student temp = new Student();
                    if (getStudentNumber() != null) {
                        temp.setStudentNumber(getStudentNumber());
                        Student dbStudent = getStudentService().getStudent(temp);
                        if (dbStudent.getDob().equals(webStudent.getDob())) {
                            studentFound = true;
                            if (dbStudent.getLastName().equals(webStudent.getLastName())) {
                                if (dbStudent.getFirstName().equals(webStudent.getFirstName())) {
                                    studentMerged = true;
                                    //merge appropriate values
                                    webStudent.setStudentNumber(dbStudent.getStudentNumber());
                                    webStudent.setStudentPk(dbStudent.getStudentPk());
                                    webStudent.setSchoolFk(getSchoolPk());
                                    //fullUpdate sends nulls
                                    int i = getStudentService().fullUpdate(webStudent);
                                    setCurrentStudent(webStudent);
                                    setStudentPk(webStudent.getStudentPk());
                                }
                            }
                            if (!studentMerged) {
                                List<String> web = new ArrayList<String>();
                                List<String> db = new ArrayList<String>();

                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                                web.add(sdf.format(webStudent.getDob()));
                                web.add(webStudent.getLastName());
                                web.add(webStudent.getFirstName());
                                setMergeWebStudent(web);

                                db.add(sdf.format(dbStudent.getDob()));
                                db.add(dbStudent.getLastName());
                                db.add(dbStudent.getFirstName());
                                setMergeDbStudent(db);
                            }
                        } else {
                            //dob doesnt match.
                            addActionError("File Number cannot sync.  Student dob does not match.");
                        }
                    }
                } else {
                    //filenumber already in database.
                    String note = null;
                    if(webStudent.getStudentNumber() != null){
                        note = webStudent.getStudentNumber().toString();
                    } else {
                        note = webStudent.getStudentFullName();
                    }
                    addActionError("File Number already associated with a student " +
                                   note +
                                   ", please contact SDC administration for assistance.");
                }
            }
        } else {
            log.debug("Webservice RESULTS EMPTY");
            throw new Exception("RESULTS EMPTY");
        }

        if (studentMerged) {
            log.debug("SUCCESS");
            return SUCCESS;
        } else if (studentFound) {
            log.debug("MERGE");
            return Constants.MERGE;
        } else {
            log.debug("INPUT");
            Collection list = getActionErrors();
            setThirdPartyAjaxMessages(list);
            sendSyncErrorEmail((ArrayList) list, getStudentNumber(), getFileNumber());
            return INPUT;
        }
    }

    public String webSearch() throws Exception {
        log.debug("******* thirdParty webSearch " + getFileNumber());

        boolean studentExists = false;
        QueryByDLResponseType wsResponse = getQueryByDl();

        if (wsResponse != null) {
            Student student = parseQueryByDlResults(wsResponse);
            if ("200".equals(wsResponse.getStatus())) {
                studentExists = true;
                setCurrentStudent(student);
                setStudentPk(student.getStudentPk());
            }
        } else {
            log.debug("Webservice RESULTS EMPTY");
            throw new Exception("RESULTS EMPTY");
        }

        if (studentExists) {
            return SUCCESS;
        } else {
            Collection list = getActionErrors();
            setThirdPartyAjaxMessages(list);
            return INPUT;
        }
    }

    public String studentNumberSearch() throws Exception {
        log.debug("...ThirdParty studentNumberSearch");
        boolean studentExists = false;
        boolean studentMultipleExists = false;
        boolean noClassroom = false;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentNumber", getStudentNumber());
        hm.put("schoolFk",getSchoolFk());
        List list = getStudentService().getStudentList(hm);
        
        if (!list.isEmpty()) {
            studentExists = true;
            setCurrentStudent((Student) list.get(0));
            setStudentPk(getCurrentStudent().getStudentPk());
            log.debug("...studentExists = " + studentExists );
            if (studentExists) {
                hm.put("studentPk", getStudentPk());
                List classroomList = getClassroomService().getSchoolInfoByStudent(hm);
                log.debug("...classroomList size = " + classroomList.size());
                if (!classroomList.isEmpty()) {
                    Classroom c = (Classroom) classroomList.get(0);
                    setClassroomPk(c.getClassroomPk());
                    if (classroomList.size() > 1) {
                        studentMultipleExists = true;
                        ArrayList<Object> multipleList = new ArrayList<Object>();
                        for (int i = 0; i < classroomList.size(); i++) {
                            Classroom temp = (Classroom) classroomList.get(i);
                            multipleList.add(temp.getClassroomPk());
                        }
                        setMultipleClassroomList(multipleList);
                    }
                } else {
                    log.debug("Student exists but is not associated with a classroom");
                    //this would happen if a student was removed from a classroom;
                    //should be sent to a separate page which finds all the records regardless of classroom.
                    noClassroom = true;
                }
            }
        }

        if (studentMultipleExists) {
            return Constants.Forward_MultipleClasses;
        }

        if (noClassroom) {
            return Constants.Forward_NoClassStudent;
        }

        if (studentExists) {
            return SUCCESS;
        } else {
            addActionError("Student Not Found");
            Collection col = getActionErrors();
            setCommercialAjaxMessages(col);
            return INPUT;
        }
    }

    public String dbSearch() throws Exception {
		log.debug("...thirdparty dbSearch");
		boolean studentExists = false;
		HashMap hm = new HashMap();

		// Redmine 9505 third party (road tester).
		hm.put("firstName", "%" + currentStudent.getFirstName() + "%");
		hm.put("lastName", "%" + currentStudent.getLastName() + "%");

		hm.put("dob", currentStudent.getDob());

		log.debug("...hm values = " + hm.values());
		List list = getStudentService().getStudentList(hm);

		if (!list.isEmpty()) {
			studentExists = true;

			if (list.size() == 1) {
				setCurrentStudent((Student) list.get(0));
			} else { // multiple result - redmine 9505 high school
				// set school name in list
				setSchoolName(list);

				setStudentListSearchResult(list);
				return "multipleResult";
			}
		}

		if (studentExists) {
			return SUCCESS;
		} else {
			addActionError("Student Not Found");
			return INPUT;
		}
    }

    public void prepare() throws Exception {
        if (getCurrentStudent() != null) {
            log.debug("not null");
        } else {
            Student preFetched = fetch(getStudentPk());
            if (preFetched != null) {
                setCurrentStudent(preFetched);
            }
        }
    }

    private QueryByDLResponseType getQueryByDl() throws Exception {
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();
        
        /* old web service - 12/01/2012
        DriversLicenseValidation dlv = new DriversLicenseValidation();
        dlv.setTransactionParameters(dlv.getQueryByDLTransactionParam(umdLogon, getFileNumber()));
        Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_QueryByDL));
        */
        
        SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        QueryByDLRequestType wsRequest = new QueryByDLRequestType();
        wsRequest.setLicenseNumber(getFileNumber());
        QueryByDLResponseType wsResponse = wsService.queryByDL(wsRequest);
        
        return wsResponse;
    }

    private Student parseQueryByDlResults(QueryByDLResponseType wsResponse) throws Exception {
        return parseQueryByDlResults(wsResponse, false);
    }
    
    private Student parseQueryByDlResults(QueryByDLResponseType wsResponse, boolean lookupByStudentNumber) throws Exception {
        log.debug("LOOKUP BY STUDENT NUMBER "+lookupByStudentNumber);
        boolean studentInsert = false;
        Student temp = new Student();
        if(lookupByStudentNumber){
            temp.setStudentNumber(getStudentNumber());
        } else {
            temp.setFileNumber(getFileNumber());
        }

        if ("200".equals(wsResponse.getStatus())) {
            try {
                log.debug("Status 200");
                Student student = getStudentService().getStudent(temp);
                if (student.getStudentPk() == null) {
                    studentInsert = true;
                }

                student.setDob(wsResponse.getSubject().getBirthDate());
                student.setFirstName(wsResponse.getSubject().getGivenName());
                student.setMiddleName(wsResponse.getSubject().getMiddleName());
                student.setLastName(wsResponse.getSubject().getSurName());
                
                if (wsResponse.getStudentDriverCertificate() != null) {
                	if (wsResponse.getStudentDriverCertificate().getClassroomCompletionDate() != null) {
                		student.setClassroomCompletionDate(wsResponse.getStudentDriverCertificate().getClassroomCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getClassroomSchoolId() != null) {
                		student.setClassroomCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getClassroomSchoolId()));
                	}

                	if (wsResponse.getStudentDriverCertificate().getObservationCompletionDate() != null) {
                		student.setObservationCompletionDate(wsResponse.getStudentDriverCertificate().getObservationCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getObservationSchoolId() != null) {
                		student.setObservationCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getObservationSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getWheelCompletionDate() != null) {
                		student.setBehindWheelCompletionDate(wsResponse.getStudentDriverCertificate().getWheelCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getWheelSchoolId() != null) {
                		student.setBtwCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWheelSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime() != null) {
                		student.setWrittenTestCompletionDate(wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime().getTime());
                	}
                	if (wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId() != null) {
                		student.setWrittenCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId().trim()));
                	}

                	if (wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime() != null) {
                		student.setRoadTestCompletionDate(wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime().getTime());
                	}
                	if (wsResponse.getStudentDriverCertificate().getRoadTestSchoolId() != null) {
                		student.setRoadCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getRoadTestSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getEligibilityDate() != null) {
                		student.setEligibilityDate(wsResponse.getStudentDriverCertificate().getEligibilityDate());
                	}
                	
                	if (wsResponse.getDriverLicense().getLicenseCategoryCode() != null) {
                		student.setLicenseType(wsResponse.getDriverLicense().getLicenseCategoryCode().getValue());
                	}
                	
                    student.setFileNumber(getFileNumber());
                    Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
                    student.setUpdatedBy(loggedInPerson.getEmail());
                }
                
                log.debug("#################### returning student");
                return student;
            } catch (DaoException de) {
                log.error("DAO ", de);
                throw new Exception(de);
            }
		} else {
			addActionError("Status " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
		}
		
		log.debug("#################### returning blank student");
		return null;
    }

    public List getMergeDbStudent() {
        return mergeDbStudent;
    }

    public void setMergeDbStudent(List mergeDbStudent) {
        this.mergeDbStudent = mergeDbStudent;
    }

    public List getMergeWebStudent() {
        return mergeWebStudent;
    }

    public void setMergeWebStudent(List mergeWebStudent) {
        this.mergeWebStudent = mergeWebStudent;
    }

    public List getMultipleClassroomList() {
        return multipleClassroomList;
    }

    public void setMultipleClassroomList(List multipleClassroomList) {
        this.multipleClassroomList = multipleClassroomList;
    }

    public void webserviceUpdate() throws Exception {
        setFileNumber(getCurrentStudent().getFileNumber());
        QueryByDLResponseType wsResponse = getQueryByDl();
        Student student = parseQueryByDlResults(wsResponse);
        if (student != null) {
            //fullUpdate sends nulls
            int i = getStudentService().fullUpdate(student);
            //AUDIT
            checkAuditDates(student);
            setCurrentStudent(student);
            setStudentPk(student.getStudentPk());
        } else {
            log.debug("webserviceUpdate failed");
        }
    }

    public Collection getThirdPartyAjaxMessages() {
        log.debug("getThirdPartyAjaxMessages");
        return (Collection) getSession().get(Constants.ThirdParty_Ajax_Message);
    }

    private void sendSyncErrorEmail(ArrayList list, Long studentNumber, String fileNumber) throws Exception {
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();
        StringBuffer msg = new StringBuffer();
        msg.append(System.getProperty("line.separator"));
        msg.append(umdLogon);
        msg.append(" has attempted to sync the following student in SDC.");
        msg.append(System.getProperty("line.separator"));
        msg.append(System.getProperty("line.separator"));
        msg.append("Student Number: " + studentNumber + " to");
        msg.append(System.getProperty("line.separator"));
        msg.append("File Number: " + fileNumber);
        msg.append(System.getProperty("line.separator"));
        if (!list.isEmpty()) {
            msg.append(System.getProperty("line.separator"));
            msg.append("Error Message(s):");
            msg.append(System.getProperty("line.separator"));
            msg.append(System.getProperty("line.separator"));
            for (int i = 0; i < list.size(); i++) {
                msg.append(list.get(i));
                msg.append(System.getProperty("line.separator"));
            }
        }
        msg.append(System.getProperty("line.separator"));
        msg.append("Possible error resolutions:");
        msg.append(System.getProperty("line.separator"));
        msg.append("If a number is already associated with a student one solution may be to remove the student from the group and re-add them as an \"Add Student By File Number\". ");
        msg.append("However, before doing this you should note the students recorded times, because they will have to be re-entered into the newly added students� record.");
        sendEmail("SDC ThirdParty Sync Error", msg.toString());
    }

    public void sendEmail(String subj, String msg) throws Exception {
        SendMail s = new SendMail();
        s.setMailSubject(subj);
        log.debug("sendEmail message = " + msg);
        s.setMailMessage(msg);
        s.send();
    }
    
    public Student student() {
        return this.student;
    }	
   
   public Student getStudent(){
        return this.student;
    }
   
   public void setStudent(Student student){
        this.student = student;
    }

public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }
    
	public List getStudentListSearchResult() {
		return this.studentListSearchResult;
	}
	
	public void setStudentListSearchResult(List studentListSearchResult) {
		this.studentListSearchResult = studentListSearchResult;
	}
	
	private void setSchoolName(List students) throws Exception {
		
		for (int i = 0; i < students.size(); i++) {
			Student student = (Student)students.get(i);
			HashMap hm = new HashMap();
			hm.put("schoolPk", student.getSchoolFk());
			School school = getSchoolService().getSchool(hm);
			student.setSchoolName(school.getSchoolName());
		}
	}
}