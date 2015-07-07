package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.PersonType;
import gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.model.StudentAudit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

@SuppressWarnings({"unchecked", "unused"})
public class StudentCreateAction extends SDCSupport implements Preparable, ServletRequestAware, ServletResponseAware {

    private static final long serialVersionUID = 1L;
    private static final int WRITTEN_TEST = 1;
    private static final int CLASSROOM = 2;
    private static final int OBSERVATION = 3;
    private static final int BTW = 4;
    private static final int ROAD_TEST = 5;
    protected static Log log = LogFactory.getLog(StudentCreateAction.class);
    private Integer studentPk;
    private Student currentStudent;
    private String GENERATE_REPORT = "generateWrittenTest";
    private boolean roadCheck;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CompletionByDLResponseType wsResponse = null;

    public String execute() throws Exception {
        log.debug("execute");
        return SUCCESS;
    }

    public String input() throws Exception {
        log.debug("input");

        // Redmine 11677 - electronic multi high school enhancement
        if (currentStudent != null) {
            if (currentStudent.getWrittenCompletionSchoolNumber() != null) {
                currentStudent.setWrittenCompletionSchoolName(getSchoolName(String.valueOf(currentStudent.getWrittenCompletionSchoolNumber().intValue())));
            }
            if (currentStudent.getClassroomCompletionSchoolNumber() != null) {
                currentStudent.setClassroomCompletionSchoolName(getSchoolName(String.valueOf(currentStudent.getClassroomCompletionSchoolNumber().intValue())));
            }
            if (currentStudent.getObservationCompletionSchoolNumber() != null) {
                currentStudent.setObservationCompletionSchoolName(getSchoolName(String.valueOf(currentStudent.getObservationCompletionSchoolNumber().intValue())));
            }
            if (currentStudent.getBtwCompletionSchoolNumber() != null) {
                currentStudent.setBtwCompletionSchoolName(getSchoolName(String.valueOf(currentStudent.getBtwCompletionSchoolNumber().intValue())));
            }
            if (currentStudent.getRoadCompletionSchoolNumber() != null) {
                currentStudent.setRoadCompletionSchoolName(getSchoolName(String.valueOf(currentStudent.getRoadCompletionSchoolNumber().intValue())));
            }
        }
        return INPUT;
    }

    public String update() throws Exception {

        log.debug("update");
        int webCode = 0;
        boolean oneTimeDone = false;
        
        if (currentStudent != null && currentStudent.getWrittenTestScore() == -1) {
        	currentStudent.setWrittenTestScore(null);
        }

        // send multiple training updates to Web service - redmine 11677
        if (currentStudent.getWrittenCompletionSchoolNumber() != null) {
            webCode = newWebSend(String.valueOf(currentStudent.getWrittenCompletionSchoolNumber().intValue()), WRITTEN_TEST);
            // ignore webCode = 512. eg. 512 PRECONDITIONS NOT MET: MISSING OBSERVATION COMPLETION DATE
            if (webCode == 200 || webCode == 512) {
                if (!oneTimeDone) {
                    update_others();
                    oneTimeDone = true;
                }
            } else {
                handleWsError(webCode, "Written Test");
            }
        }
        if (currentStudent.getClassroomCompletionSchoolNumber() != null) {
            webCode = newWebSend(String.valueOf(currentStudent.getClassroomCompletionSchoolNumber().intValue()), CLASSROOM);
            if (webCode == 200 || webCode == 512) {
                if (!oneTimeDone) {
                    update_others();
                    oneTimeDone = true;
                }
            } else {
                handleWsError(webCode, "Classroom");
            }
        }
        if (currentStudent.getObservationCompletionSchoolNumber() != null) {
            webCode = newWebSend(String.valueOf(currentStudent.getObservationCompletionSchoolNumber().intValue()), OBSERVATION);
            if (webCode == 200 || webCode == 512) {
                if (!oneTimeDone) {
                    update_others();
                    oneTimeDone = true;
                }
            } else {
                handleWsError(webCode, "Observation");
            }
        }
        if (currentStudent.getBtwCompletionSchoolNumber() != null) {
            webCode = newWebSend(String.valueOf(currentStudent.getBtwCompletionSchoolNumber().intValue()), BTW);
            if (webCode == 200 || webCode == 512) {
                if (!oneTimeDone) {
                    update_others();
                    oneTimeDone = true;
                }
            } else {
                handleWsError(webCode, "Behind The Wheel");
            }
        }
        if (currentStudent.getRoadCompletionSchoolNumber() != null) {
            webCode = newWebSend(String.valueOf(currentStudent.getRoadCompletionSchoolNumber().intValue()), ROAD_TEST);
            if (webCode == 200 || webCode == 512) {
                if (!oneTimeDone) {
                    update_others();
                    oneTimeDone = true;
                }
            } else {
                handleWsError(webCode, "Road Test");
            }
        }

        return SUCCESS;
    }

    public String generateOcsFullCompletion() throws Exception {
        log.debug("generateOcsFullCompletion");

        // get student info from db
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentPk", currentStudent.getStudentPk());
        Student tmpStudent = getStudentService().getStudent(hm);
        setCurrentStudent(tmpStudent);

        getSession().put(Constants.Report_OcsCompletionStudent, currentStudent);
        return Constants.REPORT; //return pdf
    }

    public String generateFullCompletion() throws Exception {

        log.debug("generateFullCompletion");

        // get student info from db
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentPk", currentStudent.getStudentPk());
        Student tmpStudent = getStudentService().getStudent(hm);
        setCurrentStudent(tmpStudent);

        getSession().put(Constants.Report_FullCompletionStudent, currentStudent);
        return Constants.REPORT; //return pdf
    }

    public String createCommercialStudent() throws Exception {
        log.debug("createCommercialStudent");
        return SUCCESS;
    }

    public String createStudent() throws Exception {
        log.debug("createStudent");
        boolean studentCreated = false;
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();
        
        if (currentStudent != null && currentStudent.getWrittenTestScore() == -1) {
        	currentStudent.setWrittenTestScore(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstName = currentStudent.getFirstName();
        String middleName = currentStudent.getMiddleName();
        String lastName = currentStudent.getLastName();
        //String dob = sdf.format(currentStudent.getDob());
        String dob = sdf.format(currentStudent.getDob());
        //String wtDate = sdf.format(currentStudent.getWrittenTestCompletionDate());
        String wtDate = sdf.format(currentStudent.getWrittenTestCompletionDate());

        String schoolNumber = getSchoolNumber(currentStudent.getSchoolFk());
        currentStudent.setWrittenCompletionSchoolNumber(Integer.valueOf(schoolNumber));

        log.debug("Values " + firstName + ", " + middleName + ", " + lastName + ", " + dob + ", " + wtDate + ", " + schoolNumber);

        int retCode = existingStudent(firstName, middleName, lastName, currentStudent.getDob(), currentStudent.getWrittenTestCompletionDate(), currentStudent.getWrittenCompletionSchoolNumber());
        if (retCode < 1) {
            /* old web service - 12/01/2012
        	DriversLicenseValidation dlv = new DriversLicenseValidation();
            dlv.setTransactionParameters(dlv.getWrittenTestTransactionParam(umdLogon, firstName, middleName, lastName, dob, wtDate, schoolNumber));
            Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_SendWrittenTest));
            */
        	SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        	CompleteWrittenByNameRequestType wsRequest = new CompleteWrittenByNameRequestType();
        	PersonType subject = new PersonType();
        	subject.setGivenName(firstName);
        	subject.setMiddleName(middleName);
        	subject.setSurName(lastName);
        	subject.setBirthDate(currentStudent.getDob());
        	wsRequest.setSubject(subject);
        	
        	StudentDriverCertificateType sdc = new StudentDriverCertificateType();
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(currentStudent.getWrittenTestCompletionDate());
        	sdc.setWrittenExamCompletionDateTime(cal);
        	sdc.setWrittenExamSchoolId(schoolNumber);
        	wsRequest.setStudentDriverCertificate(sdc);
        	
        	CompleteWrittenByNameResponseType wsResponse = wsService.completeWrittenByName(wsRequest);
        	
            if (wsResponse != null) {
                if ("200".equals(wsResponse.getStatus())) {
                    try {
                        log.debug("Status 200");
                        studentCreated = true;
                        if (wsResponse.getLicenseNumber() != null) {
                            currentStudent.setFileNumber(wsResponse.getLicenseNumber());
                        }

                        currentStudent.setUpdatedBy(umdLogon);
                        int i = getStudentService().insert(currentStudent);
                        Integer studentPk = getSdcService().getLastInsertedId();
                        currentStudent.setStudentPk(studentPk);
                        insertWrittenAuditDate(checkWrittenAudit(currentStudent));
                    } catch (DaoException de) {
                        log.error("DAO ", de);
                        throw new Exception(de);
                    }
                } else if ("404".equals(wsResponse.getStatus())) {
                    log.debug("STUDENT INSERT A" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    addActionError("Webservice Status " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                } else if ("412".equals(wsResponse.getStatus())) {
                    //ResponseException available after 412 not 404 not 200
                    //DL available but no permit.
                    log.debug("STUDENT INSERT B" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    addActionError("Webservice " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                } else {
                    addActionError("DAO " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                }
            } else {
                log.debug("Webservice ");
                throw new Exception("ResponseStatusDescription");
            }
        } else {
            log.info("Student Already in SDC Database");
            currentStudent.setFileNumber(getStudentFileNumber(firstName, middleName, lastName, currentStudent.getDob(), currentStudent.getWrittenTestCompletionDate(), currentStudent.getSchoolFk()));
            studentCreated = true;
        }
        if (studentCreated) {
            return SUCCESS;
        } else {
            return INPUT;
        }
    }

    public String createSpecialStudent() throws Exception {
        log.debug("createSpecialStudent");
        boolean studentCreated = false;
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();

        if (currentStudent != null && currentStudent.getWrittenTestScore() == -1) {
        	currentStudent.setWrittenTestScore(null);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstName = currentStudent.getFirstName();
        String middleName = currentStudent.getMiddleName();
        String lastName = currentStudent.getLastName();
        String dob = sdf.format(currentStudent.getDob());
        String schoolNumber = getSchoolNumber(currentStudent.getSchoolFk());
        boolean foundOne = false;
        String classroomDate = "";
        String observationDate = "";

        // redmine 35542 #4
        StudentAudit sa = new StudentAudit();

        if (currentStudent.getClassroomCompletionDate() != null) {
            foundOne = true;
            classroomDate = sdf.format(currentStudent.getClassroomCompletionDate());
            currentStudent.setClassroomCompletionSchoolNumber(Integer.valueOf(schoolNumber));
            sa.setClassroomCompletionDate_datestamp(new Date());
            sa.setClassroomCompletionDate_userid(umdLogon);
        }
        if (currentStudent.getObservationCompletionDate() != null) {
            foundOne = true;
            observationDate = sdf.format(currentStudent.getObservationCompletionDate());
            currentStudent.setObservationCompletionSchoolNumber(Integer.valueOf(schoolNumber));
            sa.setObservationCompletionDate_datestamp(new Date());
            sa.setObservationCompletionDate_userid(umdLogon);
        }
        currentStudent.setStudentAudit(sa);
        	
        log.debug("Values " + firstName + ", " + middleName + ", " + lastName + ", " + dob + ", " + classroomDate + ", " + observationDate + ", " + schoolNumber);

        if (foundOne) {
            int retCode = existingSpecialStudent(firstName, middleName, lastName,
            		currentStudent.getDob(), currentStudent.getClassroomCompletionDate(),
            		currentStudent.getObservationCompletionDate(), currentStudent.getClassroomCompletionSchoolNumber(),
                    currentStudent.getObservationCompletionSchoolNumber());
            if (retCode < 1) {
            	/* old web service - 12/01/2012
                DriversLicenseValidation dlv = new DriversLicenseValidation();
                dlv.setTransactionParameters(dlv.getCompletionByNameTransactionParam(umdLogon, firstName, middleName, lastName, dob, classroomDate, observationDate, schoolNumber));
                Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_CompletionByName));
				*/
        		SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        		CompletionByNameRequestType wsRequest = new CompletionByNameRequestType();
        		PersonType subject = new PersonType();
        		subject.setGivenName(firstName);
        		subject.setMiddleName(middleName);
        		subject.setSurName(lastName);
        		subject.setBirthDate(currentStudent.getDob());
        		wsRequest.setSubject(subject);
        		
        		StudentDriverCertificateType sdc = new StudentDriverCertificateType();
        		if (currentStudent.getClassroomCompletionDate() != null) {
        			sdc.setClassroomCompletionDate(currentStudent.getClassroomCompletionDate());
        			sdc.setClassroomSchoolId(schoolNumber);			
        		}
        		if (currentStudent.getObservationCompletionDate() != null) {
        			sdc.setObservationCompletionDate(currentStudent.getObservationCompletionDate());
        			sdc.setObservationSchoolId(schoolNumber);			
        		}
        		wsRequest.setStudentDriverCertificate(sdc);
        		CompletionByNameResponseType wsResponse = wsService.completionByName(wsRequest);
        		
                if (wsResponse != null) {
                    if ("200".equals(wsResponse.getStatus())) {
                        try {
                            log.debug("Status 200");
                            studentCreated = true;
                            if (wsResponse.getLicenseNumber() != null) {
                                currentStudent.setFileNumber(wsResponse.getLicenseNumber());
                            }

                            currentStudent.setUpdatedBy(umdLogon);
                            int i = getStudentService().insert(currentStudent);
                            Integer studentPk = getSdcService().getLastInsertedId();
                            currentStudent.setStudentPk(studentPk);
                            //insertWrittenAuditDate(checkWrittenAudit(currentStudent));
                        } catch (DaoException de) {
                            log.error("DAO ", de);
                            throw new Exception(de);
                        }
                    } else if ("404".equals(wsResponse.getStatus())) {
                        log.debug("STUDENT INSERT A" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                        addActionError("Webservice Status " +wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    } else if ("412".equals(wsResponse.getStatus())) {
                        //ResponseException available after 412 not 404 not 200
                        //DL available but no permit.
                        log.debug("STUDENT INSERT B" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                        addActionError("Webservice " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    } else {
                        addActionError("DAO " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    }
                } else {
                    log.debug("Webservice ");
                    throw new Exception("ResponseStatusDescription");
                }
            } else {
                log.info("Student Already in SDC Database");
                currentStudent.setFileNumber(getStudentFileNumber(firstName, middleName, lastName, currentStudent.getDob(), currentStudent.getWrittenTestCompletionDate(), currentStudent.getSchoolFk()));
                studentCreated = true;
            }
        } else {
            log.info("Student Completion Dates not sent by user");
            studentCreated = false;
        }
        
        if (studentCreated) {
        	update_others();	// redmine 35542 comment 4
            return SUCCESS;
        } else {
            return INPUT;
        }
    }

    public String generateWrittenTest() throws Exception {
        log.debug("generateWrittenTest");
        String retVal = createStudent();

        if (retVal.equals(SUCCESS)) {
            log.debug("return the jasper report");
            getSession().put(Constants.Report_WrittenCompletionStudent, currentStudent);

            // redmine 35542 - insert audit for Generate Written Test Completion Slip button
            Student auditStudent = new Student();
            auditStudent.setStudentPk(currentStudent.getStudentPk());
            StudentAudit sa = new StudentAudit();
            sa.setWrittenCompletionDate_datestamp(new Date());
            sa.setWrittenCompletionDate_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());
            sa.setWrittenCompletionSchool_datestamp(new Date());
            sa.setWrittenCompletionSchool_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());
            sa.setWrittenTestScore_datestamp(new Date());
            sa.setWrittenTestScore_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());

            auditStudent.setStudentAudit(sa);
            getStudentService().insertStudentAudit(auditStudent);

            return GENERATE_REPORT;
        }
        return INPUT;
    }

    public String generateWrittenPDF() throws Exception {
        log.debug("generateWrittenPDF");

        // get student info from db
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentPk", currentStudent.getStudentPk());
        Student tmpStudent = getStudentService().getStudent(hm);
        setCurrentStudent(tmpStudent);

        getSession().put(Constants.Report_WrittenCompletionStudent, currentStudent);
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("save");
        return SUCCESS;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void prepare() throws Exception {
        log.debug("prepare");
        if (getCurrentStudent() == null) {
            Student preFetched = fetch(getStudentPk());
            if (preFetched != null) {
                setCurrentStudent(preFetched);
            }
        }
        
        if (currentStudent != null) {
            if (currentStudent.getWrittenTestScore() != null && currentStudent.getWrittenTestScore() == -1) {
            	currentStudent.setWrittenTestScore(null);
            }
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

    public int updateStudent() throws DaoException {
        int retVal = 0;
        
        if (currentStudent != null && currentStudent.getWrittenTestScore() == -1) {
        	currentStudent.setWrittenTestScore(null);
        }
        
        retVal = getStudentService().update(currentStudent);
        return retVal;
    }

    public String getCurrentRoadTestInstructor() throws DaoException {
        log.debug("getCurrentRoadTestInstructor");
        Integer personPk = currentStudent.getRoadTestInstructorFk();
        if (personPk == null) {
            return "DL Instructor";
        } else {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("personPk", personPk);
            Person instructor = getPersonService().getPerson(hm);
            return instructor.getFullName();
        }
    }

    public int existingStudent(String firstName, String middleName, String lastName, Date dob, Date wtDate, Integer schoolNumber) throws Exception {
        log.debug("checking if existingStudent");
        int retVal = 0;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", firstName);
        hm.put("middleName", middleName);
        hm.put("lastName", lastName);
        hm.put("dob", dob);
        hm.put("writtenTestCompletionDate", wtDate);
        hm.put("writtenTestCompletionSchoolNumber", schoolNumber);
        retVal = (getStudentService().getEqualsCount(hm)).intValue();

        return retVal;
    }

    public int existingSpecialStudent(String firstName, String middleName, String lastName, Date dob, Date classroomDate, Date observationDate, Integer classroomNumber, Integer observationNumber) throws Exception {
        log.debug("checking if existingStudent");
        int retVal = 0;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", firstName);
        hm.put("middleName", middleName);
        hm.put("lastName", lastName);
        hm.put("dob", dob);
        hm.put("classroomCompletionDate", classroomDate);
        hm.put("observationCompletionDate", observationDate);
        hm.put("classroomCompletionSchoolNumber", classroomNumber);
        hm.put("observationCompletionSchoolNumber", observationNumber);
        retVal = (getStudentService().getEqualsCount(hm)).intValue();

        return retVal;
    }

    public int webSend() throws Exception {

        log.debug("webSend");

        return newWebSend(null, 0);
    }

    public String sendStringDate(boolean set, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String retVal = "";

        //false send date
        //true send blank
        if (date != null) {
            log.debug("sendDate " + date.toString());
            if (!set) {
                retVal = sdf.format(date);
            }
        }
        return retVal;
    }

    public boolean isRoadCheck() {
        return roadCheck;
    }

    public void setRoadCheck(boolean roadCheck) {
        this.roadCheck = roadCheck;
    }

    public String getStudentFileNumber(String firstName, String middleName, String lastName, Date dob, Date wtDate, Integer schoolFk) throws Exception {
        log.debug("Getting StudentFileNumber");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", firstName);
        hm.put("middleName", middleName);
        hm.put("lastName", lastName);
        hm.put("dob", dob);
        hm.put("writtenTestCompletionDate", wtDate);
        hm.put("schoolFk", schoolFk);
        Student student = getStudentService().getStudent(hm);

        return student.getFileNumber();
    }

    public void ajaxCheckUser() throws Exception {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dob = null;
        try {
        	dob = sdf.parse(request.getParameter("dob"));
        } catch (Exception e) {
        	response.getWriter().write("<message>dob_error</message>");
        	return;
        }

        int retVal = 0;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", request.getParameter("firstName"));
        hm.put("lastName", request.getParameter("lastName"));
        hm.put("dob", dob);
        hm.put("schoolFk", new Integer(request.getParameter("schoolFk")));
        retVal = (getStudentService().getEqualsCount(hm)).intValue();
        
        if (retVal > 0) {	// exist
            response.getWriter().write("<message>Yes</message>");
        } else {			// not exist
            response.getWriter().write("<message>No</message>");
        }
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Redmine 11677
     * @param schoolNumber
     * @param trainingType
     * @return
     * @throws Exception
     */
    private int newWebSend(String schoolNumber, int trainingType) throws Exception {

        log.debug("newWebSend");

        if (schoolNumber == null && trainingType == 0) {	// impossible
            throw new Exception("*** Completion School Number is not specified!  ***");
        }

		SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
		CompletionByDLRequestType wsRequest = new CompletionByDLRequestType();
		wsRequest.setLicenseNumber(currentStudent.getFileNumber());
		StudentDriverCertificateType sdc = new StudentDriverCertificateType();
		Calendar cal = Calendar.getInstance();
		
        switch (trainingType) {
            case 1:
            	if (currentStudent.getWrittenTestCompletionDate() != null) {
                    cal.setTime(currentStudent.getWrittenTestCompletionDate());
                    sdc.setWrittenExamCompletionDateTime(cal);            		
            	}
                sdc.setWrittenExamSchoolId(schoolNumber);
                break;
            case 2:
                // old web service - String ccDate = sendStringDate(currentStudent.isClassroomCompletionCheck(), currentStudent.getClassroomCompletionDate());
                //dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, currentStudent.getFileNumber(), ccDate, "", "", "", "", schoolNumber));

                if (!currentStudent.isClassroomCompletionCheck()) {
                	sdc.setClassroomCompletionDate(currentStudent.getClassroomCompletionDate());
                	sdc.setClassroomSchoolId(schoolNumber);
                }
                break;
            case 3:
                //String ocDate = sendStringDate(currentStudent.isObservationCompletionCheck(), currentStudent.getObservationCompletionDate());
                //dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, currentStudent.getFileNumber(), "", ocDate, "", "", "", schoolNumber));
            	
            	if (!currentStudent.isObservationCompletionCheck()) {
            		sdc.setObservationCompletionDate(currentStudent.getObservationCompletionDate());
            		sdc.setObservationSchoolId(schoolNumber);
            	}
                break;
            case 4:
                //String btwDate = sendStringDate(currentStudent.isBehindWheelCompletionCheck(), currentStudent.getBehindWheelCompletionDate());
                //dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, currentStudent.getFileNumber(), "", "", "", btwDate, "", schoolNumber));
            	
            	if (!currentStudent.isBehindWheelCompletionCheck()) {
            		sdc.setWheelCompletionDate(currentStudent.getBehindWheelCompletionDate());
            		sdc.setWheelSchoolId(schoolNumber);
            	}
                break;
            case 5:
                if (isRoadCheck()) {
                    // rtcDate = sendStringDate(false, currentStudent.getRoadTestCompletionDate());
                	if (currentStudent.getRoadTestCompletionDate() != null) {
                        cal.setTime(currentStudent.getRoadTestCompletionDate());
                        sdc.setRoadTestCompletionDateTime(cal);                		
                	}
                    sdc.setRoadTestSchoolId(schoolNumber);
                }
                //dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, currentStudent.getFileNumber(), "", "", rtcDate, "", "", schoolNumber));
        }

        wsRequest.setStudentDriverCertificate(sdc);
        wsResponse = wsService.completionByDL(wsRequest);

        int status = 0;
        if (wsResponse != null) {
            String statusString = wsResponse.getStatus();
            //ResponseException available after 412 not 404 not 200
            status = Integer.parseInt(statusString.trim());
        } else {
            log.debug("Webservice RESULTS EMPTY");
            addActionError("Webservice not available, further information in logs.");
        }

        return status;
    }

    private void update_others() throws Exception {

        if (wsResponse != null && wsResponse.getEligibilityDate() != null) {
            Date elDate = wsResponse.getEligibilityDate();
            currentStudent.setEligibilityDate(elDate);
            if (elDate == null) {
                addActionError(Constants.Webservice_Error_EligibilityDate);
            }
        }

        checkAuditDates(currentStudent);
        
        boolean insert = false;
        if (getStudentService().getStudentAuditCount(currentStudent.getStudentPk()) == 0) {	// rm 30821
        	getStudentService().insertStudentAudit(currentStudent);
        	insert = true;
        } else {
        	getStudentService().updateStudentAudit(currentStudent);
        }

        if (insert) {
        	addActionMessage("SDC inserted student record.");
        } else {
            if (updateStudent() > 0) {
                addActionMessage("SDC updated student record.");
            }        	
        }
    }

    private void handleWsError(int status, String trainingType) {

        if (status == 400) {
            addActionError(Constants.Webservice_Error_EligibilityDate);
            //hack... till dps fix their error message.
            String dpsError = wsResponse.getStatusDescription();
            if ("Wheel Date can not be set when lic type is LRN".equals(dpsError.trim())) {
                addActionError("This driver cannot complete behind the wheel training or the driving skills test because they have not been issued a learner permit by the division.");
            } else {
                addActionError(trainingType + ": " + dpsError);
            }
            //end hack
        } else if (status == 404) {
            log.debug("status " + status);
            addActionError(trainingType + ": Status " + status);
        } else if (status == 412) {
            //ResponseException available after 412 not 404 not 200
            //DL available but no permit.
            log.debug(trainingType + ": Status " + status);

            if (wsResponse.getStatusDescription().contains("invalid School Identification")) {	// redmine 7272
                addActionError("Invalid school identification: Selected school is not defined in SDCT Web service.");
            } else {
                addActionError(trainingType + ": " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
            }
        } else {
        	addActionError(trainingType + ": " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
        }
    }
    
}
