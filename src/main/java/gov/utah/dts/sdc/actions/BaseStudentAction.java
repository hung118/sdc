package gov.utah.dts.sdc.actions;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "serial", "rawtypes", "unused", "unchecked" })
public class BaseStudentAction extends SDCSupport {

    protected static Log log = LogFactory.getLog(BaseStudentAction.class);

    private String fileNumber;
    private Integer studentPk;
    private Integer schoolFk;
    private Student currentStudent;
    

    public String dlSearch() throws Exception {
        log.debug("******* dlSearch " + getFileNumber());
        boolean studentExists = false;
        
        String umdLogon = getUmdLogonEmail();
        
        /* old Web service - 12/01/2012
        DriversLicenseValidation dlv = new DriversLicenseValidation();
        dlv.setTransactionParameters(dlv.getQueryByDLTransactionParam(umdLogon, getFileNumber()));
        Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_QueryByDL));
        */
        SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        QueryByDLRequestType wsRequest = new QueryByDLRequestType();
        wsRequest.setLicenseNumber(getFileNumber());
        QueryByDLResponseType wsResponse = wsService.queryByDL(wsRequest);

        if (wsResponse != null) {
            if ("200".equals(wsResponse.getStatus())) {
                try {
                    log.info("Status 200");
                    studentExists = true;
                    Student student = parseWebSearchResults(wsResponse, umdLogon);
                   
                    student.setAlmLog("No");
                    if (student.getStudentPk() == null) {
                        log.debug("Student Insert");
                        int i = getStudentService().insert(student);
                        student.setStudentPk(getSdcService().getLastInsertedId());
                        insertAuditDates(student);
                    } else {
                        //fullUpdate sends nulls
                        int i = getStudentService().fullUpdate(student);
                        checkAuditDates(student);
                    }
                    setCurrentStudent(student);
                } catch (DaoException de) {
                    log.error("DAO ", de);
                    throw new Exception(de);
                }
            } else if ("404".equals(wsResponse.getStatus())) {
                log.warn("status " + wsResponse.getStatus());
                addActionError("Student Not Found.");
            } else if ("412".equals(wsResponse.getStatus())) {
                //ResponseException available after 412 not 404 not 200
                //DL available but no permit.
                log.warn("status " + wsResponse.getStatus());
                addActionError(wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
            } else {
            	addActionError(wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
            }
       } else {
            log.warn("Webservice RESULTS EMPTY");
            //throw new Exception("RESULTS EMPTY");
            addActionError("Webservice not available, further information in logs.");
            return INPUT;
        }

        if (studentExists) {
            return SUCCESS;
        } else {
            return INPUT;
        }
    }

    private Student parseWebSearchResults_old(Map results, String umdLogon) throws DaoException {
        boolean studentInsert = false;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("fileNumber", getFileNumber());

        Student student = getStudentService().getStudent(hm);
        if (student.getStudentPk() == null) {
            studentInsert = true;
        }

        if (results.get("PersonBirthDate") != null) {
            student.setDob(blankDate(results.get("PersonBirthDate").toString().trim(), "dob"));
        }
        if (results.get("PersonGivenName") != null) {
            student.setFirstName(results.get("PersonGivenName").toString().trim());
        }
        if (results.get("PersonMiddleName") != null) {
            student.setMiddleName(results.get("PersonMiddleName").toString().trim());
        }
        if (results.get("PersonSurName") != null) {
            student.setLastName(results.get("PersonSurName").toString().trim());
            log.debug("LASTNAME IS " + student.getLastName());
        }
        if (results.get("PersonSuffixName") != null) {
            student.setSuffix(results.get("PersonSuffixName").toString().trim());
        }
        if (results.get("WrittenExamDate") != null) {
            student.setWrittenTestCompletionDate(blankDate(results.get("WrittenExamDate").toString().trim(), "writtenTest"));
            /* if dmv blanked out a date we have to remove the score */
            if (results.get("WrittenExamDate").toString().trim().equals("")) {
                student.setWrittenTestScore(null);
            } else {
                if (!studentInsert) {
                    if (student.getWrittenTestScore() == null) {
                        log.info("Previously deleted date has been reentered on dmv side... repopulating writtenScore");
                        student.setWrittenTestScore(new Integer(101));
                    }
                } else {
                    log.info("LASTNAME IS ");
                    student.setWrittenTestScore(new Integer(101));
                }
            }
        }
        if (results.get("WrittenExamSchoolID") != null) {
            student.setWrittenCompletionSchoolNumber(blankInteger(results.get("WrittenExamSchoolID").toString().trim()));
        }
        
        if (results.get("ClassroomCompletionDate") != null) {
            student.setClassroomCompletionDate(blankDate(results.get("ClassroomCompletionDate").toString().trim(), "classroomCompletion"));
        }
        if (results.get("ObservationCompletionDate") != null) {
            student.setObservationCompletionDate(blankDate(results.get("ObservationCompletionDate").toString().trim(), "observationCompletion"));
        }
        if (results.get("WheelCompletionDate") != null) {
            student.setBehindWheelCompletionDate(blankDate(results.get("WheelCompletionDate").toString().trim(), "roadTestDate"));
        }
        if (results.get("RoadTestCompletionDate") != null) {
            student.setRoadTestCompletionDate(blankDate(results.get("RoadTestCompletionDate").toString().trim(), "roadTestDate"));
        }
        if (results.get("EligibilityDate") != null) {
            student.setEligibilityDate(blankDate(results.get("EligibilityDate").toString().trim(), "eligibilityDate"));
        }
        if (results.get("ObservationCompletionSchoolID") != null) {
            student.setObservationCompletionSchoolNumber(blankInteger(results.get("ObservationCompletionSchoolID").toString().trim()));
        }

        if (results.get("ClassroomCompletionSchoolID") != null) {
            student.setClassroomCompletionSchoolNumber(blankInteger(results.get("ClassroomCompletionSchoolID").toString().trim()));
        }

        if (results.get("RoadTestCompletionSchoolID") != null) {
            student.setRoadCompletionSchoolNumber(blankInteger(results.get("RoadTestCompletionSchoolID").toString().trim()));
        }

        if (results.get("WheelCompletionSchoolID") != null) {
            student.setBtwCompletionSchoolNumber(blankInteger(results.get("WheelCompletionSchoolID").toString().trim()));
        }

        if (results.get("IDTypeDescriptionText") != null) {
            student.setLicenseType(results.get("IDTypeDescriptionText").toString().trim());
        }

        student.setFileNumber(getFileNumber());
        student.setUpdatedBy(umdLogon);
        return student;
    }
    
    private Student parseWebSearchResults(QueryByDLResponseType wsResponse, String umdLogon) throws DaoException {
        boolean studentInsert = false;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("fileNumber", fileNumber);
        Student student = getStudentService().getStudent(hm);
        
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
        	} else {	// redmine 33478
        		student.setRoadTestCompletionDate(null);
        		// needs to delete road test list also, if there is.
            	HashMap<String, Integer> hm2 = new HashMap<String, Integer>();
            	hm2.put("studentFk", student.getStudentPk());
            	deleteRoadAuditDate(hm2);        			
        	}
        	if (wsResponse.getStudentDriverCertificate().getRoadTestSchoolId() != null) {
        		student.setRoadCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getRoadTestSchoolId()));
        	} else {
        		student.setRoadCompletionSchoolNumber(null);
        	}
        	
        	if (wsResponse.getStudentDriverCertificate().getEligibilityDate() != null) {
        		student.setEligibilityDate(wsResponse.getStudentDriverCertificate().getEligibilityDate());
        	}
        	
        	if (wsResponse.getDriverLicense().getLicenseCategoryCode() != null) {
        		student.setLicenseType(wsResponse.getDriverLicense().getLicenseCategoryCode().getValue());
        	}
        	
            student.setFileNumber(fileNumber);
            student.setUpdatedBy(umdLogon);
        }
                
    	return student;
    }
    
    public String roadEligibleSearch() throws DaoException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", getCurrentStudent().getStudentPk());
        hm.put("schoolFk", getSchoolFk());
        log.info("roadEligibleSearch");
        int retCode = getAuditService().getRoadEligibleCount(hm);
        
        // this code doesn't make sense ... No body can enter/update score if retCode > 0?
        /*if (retCode > 0){
        	//Redmine 4203 item 2 - addActionError("Student Not Available For Testing");
            addActionError("TESTER NOT ALLOWED TO ENTER SCORE FOR THIS STUDENT.");
        	return INPUT;
        } else {
            getSession().put(Constants.RoadSearch_SchoolFk, getSchoolFk());            
        }*/
        
        
        getSession().put(Constants.RoadSearch_SchoolFk, getSchoolFk());
        return SUCCESS;
    }
    
    public Integer getRoadSearchSchool() {
       Integer fk = (Integer) getSession().get(Constants.RoadSearch_SchoolFk); 
       return fk;
    }
    
    public Collection getRoadSearchSchoolList() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        List schoolList = new ArrayList();
        try {
            hm.put("schoolPk", getRoadSearchSchool());
        } catch (Exception e) {
            log.debug("* getCommercialSchoolList *ERR********", e);
        }
        schoolList = getSchoolService().getSchoolList(hm);
        return schoolList;
    }
    
    public Student fetchStudent(Integer tryId) throws DaoException {
        log.debug("enter fetchStudent for " + tryId);
        Student result = null;
        if (tryId != null) {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("studentPk", tryId);
            result = getStudentService().getStudent(hm);
        }
        log.debug("exit fetchStudent for " + tryId);
        return result;
    }
    
    public String getCurrentRoadTestInstructor() throws DaoException {
        log.debug("getCurrentRoadTestInstructor");
        Integer personPk = currentStudent.getRoadTestInstructorFk();
        if (personPk == null) {
            return "DL Instructor";
        } else {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("personPk", personPk);
            Person instructor = getPersonService().getPerson(hm);
            return instructor.getFullName();
        }
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    
    
    public Collection getRoadTestList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        //hm.put("schoolFk", getRoadSearchSchool());
        hm.put("studentFk", getStudentPk());
        list = getStudentService().getCommercialRoad(hm);
        return list;
    }
    
}
