package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.PersonType;
import gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.CommercialTimes;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "serial", "unused", "rawtypes", "unchecked" })
public class ThirdPartyStudentAction extends BaseStudentAction implements Preparable {

    protected static Log log = LogFactory.getLog(ThirdPartyStudentAction.class);
    private Integer studentPk;
    private Long studentNumber;
    private String schoolName;
    private Integer vehicleFk;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private Date roadTestCompletionDate;
    private String roadTestStartTime;
    private String roadTestEndTime;
    private Integer roadTestScore;
    private Integer routeNumber;
    private Integer roadInstructorPk;
    private String routeArea;
    private String licenseType;
    private String updatedBy;
    private String GENERATE_REPORT = "generateRoadTest";
    private List multipleClassroomList;
    private Map session;
    private Integer classroomPk;
    private Integer roadTestListIndex;
    private Integer completionRoadPk;
    
    private String schoolAudit = "N";
    private String completionDateAudit = "N";
    private String scoreAudit = "N";
    private String instructorAudit = "N";
    private String notesAudit = "N";
    private String notes;
    private String notes_userid;
    private String school_userid;
    private Date school_datestamp;
    private String completion_date_userid;
    private Date completion_date_datestamp;
    private String score_userid;
    private Date score_datestamp;
    private String instructor_userid;
    private Date instructor_datestamp;

    @Override
    public String input() throws Exception {
        log.debug("input");
        return INPUT;
    }

    public String inputCreate() throws Exception {
        log.debug("inputCreate");
        getSession().put(Constants.RoadSearch_SchoolFk, getSchoolFk());
        return INPUT;
    }

	public String inputEditChange() throws Exception {
        log.debug("inputEditChange");
        
        // get RoadTestList
        Map<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("studentFk", getStudentPk());
        List<CommercialTimes> list = getStudentService().getCommercialRoad(hm);
        
        CommercialTimes roadTest = (CommercialTimes)list.get(getRoadTestListIndex());
        setCompletionRoadPk(roadTest.getTimePk());
        setRoadTestCompletionDate(roadTest.getCompletionDate());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        setRoadTestStartTime(roadTest.getStartTime() == null ? "" : sdf.format(roadTest.getStartTime()));
        setRoadTestEndTime(roadTest.getEndTime() == null ? "" : sdf.format(roadTest.getEndTime()));
        setRoadTestScore(roadTest.getRoadScore());
        setRouteNumber(roadTest.getRouteNumber());
        setRouteArea(roadTest.getRouteArea());
        setRoadInstructorPk(roadTest.getRoadInstructorFk());
        setVehicleFk(roadTest.getVehicleFk());
        
        setNotes(roadTest.getNotes());
        setNotes_userid(roadTest.getNotes_userid());
        setSchool_userid(roadTest.getSchool_userid());
        setSchool_datestamp(roadTest.getSchool_datestamp());
        setCompletion_date_userid(roadTest.getCompletion_date_userid());
        setCompletion_date_datestamp(roadTest.getCompletion_date_datestamp());
        setScore_userid(roadTest.getScore_userid());
        setScore_datestamp(roadTest.getScore_datestamp());
        setInstructor_userid(roadTest.getInstructor_userid());
        setInstructor_datestamp(roadTest.getInstructor_datestamp());
        
        return INPUT;
    }
    
    public String thirdPartyFront() {
        log.debug("********** ThirdParty FRONT");
        return SUCCESS;
    }
    
    public String dateOfBirthSearch(){
       log.debug("*********** dateOfBirthSearch() method");
            return "dateOfBirthSearch";
    }
    
    public String inputStudentNumber() throws Exception {
        log.debug("********** inputStudentNumber");
        return INPUT;
    }
    
     public String webSearch() throws Exception {
        log.debug("webSearch");
        String retCode = dlSearch();
        if (retCode.equals(SUCCESS)) {
            return roadEligibleSearch();
        } else {
            return INPUT;
        }
    }

    public int webSend() throws Exception {
        log.debug("webSend");
        int retVal = 0;
        CompletionByDLResponseType wsResponse = getCompletionByDl();

        if (wsResponse != null) {
            if ("200".equals(wsResponse.getStatus())) {
                retVal = 1;
            }
        } else {
            log.debug("Webservice RESULTS EMPTY");
            addActionError("Webservice not available, further information in logs.");
        }
        return retVal;
    }

    public CompletionByDLResponseType getCompletionByDl() throws Exception {
        /*String umdLogon = getUmdLogonEmail();
        DriversLicenseValidation dlv = new DriversLicenseValidation();
        String rcDate = sendStringDate(getRoadTestCompletionDate());

        // old web service - 12/01/2012
        dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, getFileNumber(), "", "", rcDate, "", "", schoolNumber));
        Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_CompletionByDL));
        */
        String schoolNumber = getSchoolNumber(getSchoolFk());
		SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
		CompletionByDLRequestType wsRequest = new CompletionByDLRequestType();
		wsRequest.setLicenseNumber(getFileNumber());
		StudentDriverCertificateType sdc = new StudentDriverCertificateType();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getRoadTestCompletionDate());
		sdc.setRoadTestCompletionDateTime(cal);
		sdc.setRoadTestSchoolId(schoolNumber);
		wsRequest.setStudentDriverCertificate(sdc);
		
		return wsService.completionByDL(wsRequest);    
	}

    public String createStudent() throws Exception {
        log.debug("thirdParty createStudent");
        boolean studentCreated = false;
        boolean studentExists = false;
        String umdLogon = getUmdLogonEmail();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String schoolNumber = getSchoolNumber(getSchoolFk());

        log.debug("Values " + getFirstName() + ", " + getMiddleName() + ", " + getLastName() + ", " + sdf.format(getDob()) + ", " + sdf.format(getRoadTestCompletionDate()) + ", " + schoolNumber);

        int retCode = existingStudent(getFirstName(), getMiddleName(), getLastName(), getDob(), getRoadTestCompletionDate());
        if (retCode < 1) {
        	/* old web service - 12/01/2012
            DriversLicenseValidation dlv = new DriversLicenseValidation();
            dlv.setTransactionParameters(dlv.getCompleteRoadByNameTransactionParam(umdLogon, getFirstName(), getMiddleName(), getLastName(), sdf.format(getDob()), sdf.format(getRoadTestCompletionDate()), schoolNumber));
            Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_CompleteRoadByName));
			*/
        	SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        	CompleteRoadByNameRequestType wsRequest = new CompleteRoadByNameRequestType();
        	PersonType subject = new PersonType();
        	subject.setGivenName(getFirstName());
        	subject.setMiddleName(getMiddleName());
        	subject.setSurName(getLastName());
        	subject.setBirthDate(getDob());
        	wsRequest.setSubject(subject);
        	
        	StudentDriverCertificateType sdc = new StudentDriverCertificateType();
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(getRoadTestCompletionDate());
        	sdc.setRoadTestCompletionDateTime(cal);
        	sdc.setRoadTestSchoolId(schoolNumber);
        	wsRequest.setStudentDriverCertificate(sdc);

        	CompleteRoadByNameResponseType wsResponse = wsService.completeRoadByName(wsRequest);
        	
            if (wsResponse != null) {
                log.debug("A");
                if ("200".equals(wsResponse.getStatus())) {
                    try {
                        log.info("Status 200");
                        studentCreated = true;
                        if (wsResponse.getLicenseNumber() != null) {
                            setFileNumber(wsResponse.getLicenseNumber());
                        }

                        setUpdatedBy(umdLogon);
                        log.debug("STUDENT INSERT A");
                        //Set up hm?
                        int i = getStudentService().insert(getStudent());
                        log.debug("STUDENT INSERT B");
                        Integer studentPk = getSdcService().getLastInsertedId();
                        setStudentPk(studentPk);
                        log.debug("STUDENT INSERT C");
                    } catch (DaoException de) {
                        log.error("DAO ", de);
                        throw new Exception(de);
                    }
                } else if ("404".equals(wsResponse.getStatus())) {
                    log.warn("STUDENT INSERT A" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    addActionError("Webservice Status " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                } else if ("412".equals(wsResponse.getStatus())) {
                    //ResponseException available after 412 not 404 not 200
                    //DL available but no permit.
                    log.warn("STUDENT INSERT B" + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                    addActionError("Webservice " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                } else {
                    addActionError("DAO " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
                }
            } else {
                log.debug("Webservice RESULTS EMPTY");
                addActionError("Student does not exist in Web service database or Web service is not available.");
                return INPUT;
            }
        } else {
            log.info("Student Already in SDC Database");
            setFileNumber(getStudentFileNumber(getFirstName(), getMiddleName(), getLastName(), getDob(), getRoadTestCompletionDate()));
            studentExists = true;
        }
        if (studentCreated) {
            return SUCCESS;
        } else if (studentExists) {
            return Constants.EDITSTUDENT;
        } else {
            return INPUT;
        }
    }

    public String generateRoadTest() throws Exception {
        log.debug("generateRoadTest");
        int start = decodeTime(getRoadTestStartTime());
        Date startDate = new Date(getRoadTestCompletionDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(getRoadTestEndTime());
        Date endDate = new Date(getRoadTestCompletionDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
            return INPUT;
        }

        String retVal = createStudent();
        if (retVal.equals(SUCCESS)) {
            log.debug("return the jasper report");
            insertRoadAuditDate(getRoadMap(startDate, endDate));
            getSession().put(Constants.Report_WrittenCompletionStudent, getStudent());
            return GENERATE_REPORT;
        } else if (retVal.equals(Constants.EDITSTUDENT)) {
            log.debug("Existing Student Jasper report");
            getSession().put(Constants.Report_WrittenCompletionStudent, getStudent());
            return GENERATE_REPORT;
        }
        return INPUT;
    }

    public String generateRoadTestPDF() throws Exception {
        log.debug("generateRoadTestPDF");
        getSession().put(Constants.Report_WrittenCompletionStudent, getStudent());
        return SUCCESS;
    }

    public int existingStudent(String firstName, String middleName, String lastName, Date dob, Date rtDate) throws Exception {
        log.debug("checking if existingStudent");
        int retVal = 0;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", firstName);
        hm.put("middleName", middleName);
        hm.put("lastName", lastName);
        hm.put("dob", dob);
        hm.put("roadTestCompletionDate", rtDate);
        retVal = (getStudentService().getEqualsCount(hm)).intValue();

        return retVal;
    }

    public String getStudentFileNumber(String firstName, String middleName, String lastName, Date dob, Date rtDate) throws Exception {
        log.debug("Getting StudentFileNumber");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("firstName", firstName);
        hm.put("middleName", middleName);
        hm.put("lastName", lastName);
        hm.put("dob", dob);
        hm.put("roadTestCompletionDate", rtDate);
        Student student = getStudentService().getStudent(hm);

        return student.getFileNumber();
    }

    public Student getStudent() {
        Student student = new Student();
        student.setDob(getDob());
        student.setFileNumber(getFileNumber());
        student.setFirstName(getFirstName());
        student.setLastName(getLastName());
        student.setMiddleName(getMiddleName());
        student.setRoadTestCompletionDate(getRoadTestCompletionDate());
        student.setRoadTestScore(getRoadTestScore());
        student.setRoadTestInstructorFk(getRoadInstructorPk());
        student.setUpdatedBy(getUpdatedBy());
        student.setSchoolFk(getSchoolFk());
        return student;
    }

    public String update() throws Exception {
        log.debug("update");
        
        //getCurrentStudent().setSchoolFk(getSchoolFk());	// remedy ticket 360423
        
        int dbCode = 0;
        int webCode = 0;

        int start = decodeTime(getRoadTestStartTime());
        Date startDate = new Date(getRoadTestCompletionDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(getRoadTestEndTime());
        Date endDate = new Date(getRoadTestCompletionDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
            //set variables for jsp
            getCurrentStudent().setFileNumber(getFileNumber());
            getCurrentStudent().setFirstName(getFirstName());
            getCurrentStudent().setMiddleName(getMiddleName());
            getCurrentStudent().setLastName(getLastName());
            getCurrentStudent().setDob(getDob());
            getCurrentStudent().setStudentPk(getStudentPk());
            getCurrentStudent().setLicenseType(getLicenseType());
            return INPUT;
        }

        if (getRoadTestScore().intValue() <= 20) {
            //add completion_data and send Completion update student record.
            webCode = webSend();
            if (webCode > 0) {
                //update Student
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("studentPk", getStudentPk());
                hm.put("roadTestCompletionDate", getRoadTestCompletionDate());
                hm.put("roadTestScore", getRoadTestScore());
                hm.put("roadTestInstructorFk", getRoadInstructorPk());
                hm.put("roadTestCompletionSchoolNumber", getSchoolNumber(getSchoolFk()));
                hm.put("schoolFk", getSchoolFk());
                dbCode = getStudentService().update(hm);
                //insert audit completion data
                insertRoadAuditDate(getRoadMap(startDate, endDate));
            }
            log.debug("Passed road test");
        } else {
            //just insert completion data.
            log.debug("Failed road test inserting audit info only");
            insertRoadAuditDate(getRoadMap(startDate, endDate));
        }

        if (dbCode > 0) {
            addActionMessage("SDC saved student record.");
        }
        log.debug("update webResponse " + webCode + " dbResponse " + dbCode);

        return SUCCESS;
    }
        
    public String update_change() throws Exception {
        log.debug("update_change");
        int dbCode = 0;
        int webCode = 0;

        int start = decodeTime(getRoadTestStartTime());
        Date startDate = new Date(getRoadTestCompletionDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(getRoadTestEndTime());
        Date endDate = new Date(getRoadTestCompletionDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
            //set variables for jsp
            getCurrentStudent().setFileNumber(getFileNumber());
            getCurrentStudent().setFirstName(getFirstName());
            getCurrentStudent().setMiddleName(getMiddleName());
            getCurrentStudent().setLastName(getLastName());
            getCurrentStudent().setDob(getDob());
            getCurrentStudent().setStudentPk(getStudentPk());
            getCurrentStudent().setLicenseType(getLicenseType());
            return INPUT;
        }

        if (getRoadTestScore().intValue() <= 20) {
            //add completion_data and send Completion update student record.
            webCode = webSend();
            if (webCode > 0) {
                //update Student
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("studentPk", getStudentPk());
                hm.put("roadTestCompletionDate", getRoadTestCompletionDate());
                hm.put("roadTestScore", getRoadTestScore());
                hm.put("roadTestInstructorFk", getRoadInstructorPk());
                hm.put("roadTestCompletionSchoolNumber", getSchoolNumber(getSchoolFk()));
                hm.put("schoolFk", getSchoolFk());
                dbCode = getStudentService().update(hm);
                
                //update audit completion data
                updateRoadAuditDate(getRoadMap(startDate, endDate));
            }
            log.debug("Passed road test");
        } else {
            //just update completion data.
            log.debug("Failed road test inserting audit info only");
            updateRoadAuditDate(getRoadMap(startDate, endDate));
        }

        if (dbCode > 0) {
            addActionMessage("SDC saved student record.");
        }
        log.debug("update webResponse " + webCode + " dbResponse " + dbCode);

        return SUCCESS;
    }
    
    public String update_delete() throws Exception {
    	
    	log.debug("update_delete");
    	HashMap<String, Integer> hm = new HashMap<String, Integer>();
    	hm.put("completionRoadPk", getCompletionRoadPk());
    	deleteRoadAuditDate(hm);
    
    	// Redmine 4316 comment 12 - need to set road_completion_date (roadTestCompletionDate) = null in student table
    	hm.put("studentPk", getStudentPk());
    	getStudentService().updateStudentRoadTestCompletionDate(hm);
    	
    	// Redmine 33478 also delete it from webservice --> not supported in webservice
    	//setRoadTestCompletionDate(null);
    	//setSchoolFk(null);
    	//getCompletionByDl();
    	
    	return SUCCESS;
    }
    
    public Collection getRoadInstructorList() throws Exception {
        List instructorList = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolFk", getRoadSearchSchool());
        instructorList = getPersonService().getRoadTesterInstructorSearch(hm);
        return instructorList;
    }

    public Collection getRoadVehicleList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolFk", getRoadSearchSchool());
        list = getVehicleService().getCommercialVehicleSearch(hm);
        return list;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getRoadTestCompletionDate() {
        return roadTestCompletionDate;
    }

    public void setRoadTestCompletionDate(Date roadTestCompletionDate) {
        this.roadTestCompletionDate = roadTestCompletionDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Log getLog() {
        return log;
    }

    public static void setLog(Log log) {
        ThirdPartyStudentAction.log = log;
    }

    public Integer getRoadInstructorPk() {
        return roadInstructorPk;
    }

    public void setRoadInstructorPk(Integer roadInstructorPk) {
        this.roadInstructorPk = roadInstructorPk;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getRoadTestEndTime() {
        return roadTestEndTime;
    }

    public void setRoadTestEndTime(String roadTestEndTime) {
        this.roadTestEndTime = roadTestEndTime;
    }

    public Integer getRoadTestScore() {
        return roadTestScore;
    }

    public void setRoadTestScore(Integer roadTestScore) {
        this.roadTestScore = roadTestScore;
    }

    public String getRoadTestStartTime() {
        return roadTestStartTime;
    }

    public void setRoadTestStartTime(String roadTestStartTime) {
        this.roadTestStartTime = roadTestStartTime;
    }

    public Integer getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    public Integer getVehicleFk() {
        return vehicleFk;
    }

    public void setVehicleFk(Integer vehicleFk) {
        this.vehicleFk = vehicleFk;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void prepare() throws Exception {
        log.debug("prepare");
        if (getCurrentStudent() == null) {
            Student preFetched = fetchStudent(getStudentPk());
            if (preFetched != null) {
                log.debug("prepare setCurrentStudent");
                setCurrentStudent(preFetched);
            }
        }
    }

    private Map<String, Object> getRoadMap(Date startTime, Date endTime) throws DaoException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", getStudentPk());
        hm.put("schoolNumber", getSchoolNumber(getSchoolFk()));
        hm.put("completionDate", getRoadTestCompletionDate());
        hm.put("roadScore", getRoadTestScore());
        hm.put("routeNumber", getRouteNumber());
        hm.put("routeArea", getRouteArea());
        hm.put("roadStartTime", startTime);
        hm.put("roadEndTime", endTime);
        hm.put("vehicleFk", getVehicleFk());
        hm.put("roadInstructorFk", getRoadInstructorPk());
        
        if ("Y".equals(getNotesAudit())) {
        	hm.put("notes", getNotes());
        	hm.put("notes_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
        }
        if ("Y".equals(getSchoolAudit())) {
        	hm.put("school_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
        	hm.put("school_datestamp", new Date());
        }
        if ("Y".equals(getCompletionDateAudit())) {
        	hm.put("completion_date_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
        	hm.put("completion_date_datestamp", new Date());
        }
        if ("Y".equals(getScoreAudit())) {
        	hm.put("score_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
        	hm.put("score_datestamp", new Date());    	
        }
        if ("Y".equals(getInstructorAudit())) {
        	hm.put("instructor_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
        	hm.put("instructor_datestamp", new Date());     	
        }
        
		hm.put("road_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
		hm.put("road_datestamp", new Date());
        
        if (getCompletionRoadPk() != null) { // for update_change method (Redmine 4316)
        	hm.put("completionRoadPk", getCompletionRoadPk());
        }
        
        return hm;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getRouteArea() {
        return routeArea;
    }

    public void setRouteArea(String routeArea) {
        this.routeArea = routeArea;
    }
    
    public List getMultipleClassroomList() {
        return multipleClassroomList;
    }

    public void setMultipleClassroomList(List multipleClassroomList) {
        this.multipleClassroomList = multipleClassroomList;
    }
    
    public void setCommercialAjaxMessages(Collection commercialAjaxMessages) {
        session.put(Constants.Commercial_Ajax_Message, commercialAjaxMessages);
    }

    public void setClassroomPk(Integer classroomPk) {
        this.classroomPk = classroomPk;
    }
    
    public Integer getRoadTestListIndex() {
    	return this.roadTestListIndex;
    }
    
    public void setRoadTestListIndex(Integer roadTestListIndex) {
    	this.roadTestListIndex = roadTestListIndex;
    }
    
    public Integer getCompletionRoadPk() {
    	return this.completionRoadPk;
    }
    
    public void setCompletionRoadPk(Integer completionRoadPk) {
    	this.completionRoadPk = completionRoadPk;
    }

	public String getSchoolAudit() {
		return schoolAudit;
	}

	public void setSchoolAudit(String schoolAudit) {
		this.schoolAudit = schoolAudit;
	}

	public String getCompletionDateAudit() {
		return completionDateAudit;
	}

	public void setCompletionDateAudit(String completionDateAudit) {
		this.completionDateAudit = completionDateAudit;
	}

	public String getScoreAudit() {
		return scoreAudit;
	}

	public void setScoreAudit(String scoreAudit) {
		this.scoreAudit = scoreAudit;
	}

	public String getInstructorAudit() {
		return instructorAudit;
	}

	public void setInstructorAudit(String instructorAudit) {
		this.instructorAudit = instructorAudit;
	}

	public String getNotesAudit() {
		return notesAudit;
	}

	public void setNotesAudit(String notesAudit) {
		this.notesAudit = notesAudit;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes_userid() {
		return notes_userid;
	}

	public void setNotes_userid(String notes_userid) {
		this.notes_userid = notes_userid;
	}

	public String getSchool_userid() {
		return school_userid;
	}

	public void setSchool_userid(String school_userid) {
		this.school_userid = school_userid;
	}

	public Date getSchool_datestamp() {
		return school_datestamp;
	}

	public void setSchool_datestamp(Date school_datestamp) {
		this.school_datestamp = school_datestamp;
	}

	public String getCompletion_date_userid() {
		return completion_date_userid;
	}

	public void setCompletion_date_userid(String completion_date_userid) {
		this.completion_date_userid = completion_date_userid;
	}

	public Date getCompletion_date_datestamp() {
		return completion_date_datestamp;
	}

	public void setCompletion_date_datestamp(Date completion_date_datestamp) {
		this.completion_date_datestamp = completion_date_datestamp;
	}

	public String getScore_userid() {
		return score_userid;
	}

	public void setScore_userid(String score_userid) {
		this.score_userid = score_userid;
	}

	public Date getScore_datestamp() {
		return score_datestamp;
	}

	public void setScore_datestamp(Date score_datestamp) {
		this.score_datestamp = score_datestamp;
	}

	public String getInstructor_userid() {
		return instructor_userid;
	}

	public void setInstructor_userid(String instructor_userid) {
		this.instructor_userid = instructor_userid;
	}

	public Date getInstructor_datestamp() {
		return instructor_datestamp;
	}

	public void setInstructor_datestamp(Date instructor_datestamp) {
		this.instructor_datestamp = instructor_datestamp;
	}
}