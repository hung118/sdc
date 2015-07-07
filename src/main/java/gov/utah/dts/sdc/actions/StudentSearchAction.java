package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType;
import gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType;
import gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingQSService;
import gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingQSServiceLocator;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class StudentSearchAction extends SDCSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(StudentSearchAction.class);
    private Integer studentPk;
    private Student currentStudent;
    private String fileNumber;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private List studentListSearchResult;

    public String execute() throws Exception {
        log.debug("execute");
        return SUCCESS;
    }

    public String editStudent() throws Exception {
        log.debug("editStudent");
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("save");
        return SUCCESS;
    }

    public String webSearch() throws Exception {
        log.debug("******* webSearch " + currentStudent.getFileNumber());
        boolean studentExists = false;
        boolean studentInsert = false;
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();

        /* old web service - 12/01/2012
        DriversLicenseValidation dlv = new DriversLicenseValidation();
        dlv.setTransactionParameters(dlv.getQueryByDLTransactionParam(umdLogon, currentStudent.getFileNumber()));
        Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_QueryByDL));
		*/
        SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        QueryByDLRequestType wsRequest = new QueryByDLRequestType();
        wsRequest.setLicenseNumber(currentStudent.getFileNumber());
        QueryByDLResponseType wsResponse = wsService.queryByDL(wsRequest);
        

        if (wsResponse != null) {
            if ("200".equals(wsResponse.getStatus())) {
                try {
                    log.info("Status 200");
                    studentExists = true;
                    Student student = getStudentService().getStudent(currentStudent);
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
                    	} else {
                    		student.setClassroomCompletionDate(null);
                    	}
                    	if (wsResponse.getStudentDriverCertificate().getClassroomSchoolId() != null) {
                    		student.setClassroomCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getClassroomSchoolId()));
                    	}

                    	if (wsResponse.getStudentDriverCertificate().getObservationCompletionDate() != null) {
                    		student.setObservationCompletionDate(wsResponse.getStudentDriverCertificate().getObservationCompletionDate());
                    	} else {
                    		student.setObservationCompletionDate(null);
                    	}
                    	if (wsResponse.getStudentDriverCertificate().getObservationSchoolId() != null) {
                    		student.setObservationCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getObservationSchoolId()));
                    	}
                    	
                    	if (wsResponse.getStudentDriverCertificate().getWheelCompletionDate() != null) {
                    		student.setBehindWheelCompletionDate(wsResponse.getStudentDriverCertificate().getWheelCompletionDate());
                    	} else {
                    		student.setBehindWheelCompletionDate(null);
                    	}
                    	if (wsResponse.getStudentDriverCertificate().getWheelSchoolId() != null) {
                    		student.setBtwCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWheelSchoolId()));
                    	}
                    	
                    	if (wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime() != null) {
                    		Calendar c = dateRoundUp(wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime());
                    		student.setWrittenTestCompletionDate(c.getTime());
                    		
                            if (!studentInsert) {
                                if (student.getWrittenTestScore() == null) {
                                    log.info("Previously deleted date has been reentered on dmv side... repopulating writtenScore");
                                    student.setWrittenTestScore(new Integer(101));
                                }
                            } else {
                                log.info("DMV PASSED TEST SCORE AS 101 on SDC SIDE");
                                student.setWrittenTestScore(new Integer(101));
                            }
                    	} else {	// dmv blanked out a date we have to remove the score and exam completion date
                    		student.setWrittenTestScore(null);
                    		student.setWrittenTestCompletionDate(null);
                    	}
                    	
                    	if (wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId() != null) {
                    		student.setWrittenCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId().trim()));
                    	}

                    	if (wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime() != null) {
                    		Calendar c = dateRoundUp(wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime());
                    		student.setRoadTestCompletionDate(c.getTime());
                    	} else {
                    		student.setRoadTestCompletionDate(null);
                    		student.setRoadTestScore(null);
                    	}
                    	if (wsResponse.getStudentDriverCertificate().getRoadTestSchoolId() != null) {
                    		student.setRoadCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getRoadTestSchoolId()));
                    	}
                    	
                    	if (wsResponse.getStudentDriverCertificate().getEligibilityDate() != null) {
                    		student.setEligibilityDate(wsResponse.getStudentDriverCertificate().getEligibilityDate());
                    	} else {
                    		student.setEligibilityDate(null);
                    	}
                    	
                    	if (wsResponse.getDriverLicense().getLicenseCategoryCode() != null) {
                    		student.setLicenseType(wsResponse.getDriverLicense().getLicenseCategoryCode().getValue());
                    	}
                    }
                    
                    student.setFileNumber(currentStudent.getFileNumber());
                    student.setUpdatedBy(umdLogon);
                    student.setAlmLog("No"); // don't do alm log
                    if (studentInsert) {
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
                //addActionError("Status " + status);
                addActionError("Student not found!");	// redmine 11931
            } else if ("412".equals(wsResponse.getStatus())) {
                //ResponseException available after 412 not 404 not 200
                //DL available but no permit.
                log.warn("status " + wsResponse.getStatus());
                addActionError("Status: " + wsResponse.getStatusDescription());
            } else {
                addActionError("Status: " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
            }
        } else {
            log.debug("Webservice RESULTS EMPTY");
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

    public String dbSearch() throws Exception {
        log.debug("dbSearch");
        boolean studentExists = false;
        HashMap hm = new HashMap();
		
        // Redmine 9505 high school
		hm.put("firstName", "%" + getCurrentStudent().getFirstName() + "%");
		hm.put("lastName", "%" + getCurrentStudent().getLastName() + "%");
		
		hm.put("dob", getCurrentStudent().getDob());
		
        List list = getStudentService().getStudentList(hm);
        if (!list.isEmpty()) {
            studentExists = true;

            if (list.size() == 1) {
            	setCurrentStudent((Student) list.get(0));
            } else {	// multiple result - redmine 9505 high school
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

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
        if (currentStudent == null) {
            Student student = new Student();
            student.setFileNumber(fileNumber);
            setCurrentStudent(student);
        } else {
        currentStudent.setFileNumber(fileNumber);
        }
    }
    
    public void prepareKnowledgeTestGenerator() {
    	
    }
    
    public void prepareGenerateKnowledgeTest() {
    	
    }

    public String dateOfBirthSearch() throws Exception {
            log.debug("StudentSearchAction dateOfBirthSearch()");
            return "dateOfBirthSearch";
        }
    
    public String knowledgeTestGenerator() throws Exception {
    	log.debug("StudentSearchAction knowledgeTestGenerator");
    	return "knowledgeTestGenerator";
    }
    
    /**
     * Calls test generator Web service.
	 * 1) Create the actual handler which implements the SOAPHandler interface.
	 * 2) Create the class that implements the HandlerResolver interface. This class decides what handlers 
	 * should be called and in what specific order. The handler above is added to this class.
	 * 3) Add the HandlerResolver instance to the Web Service Client.
     * 
     * @return
     * @throws Exception
     */
    public String generateKnowledgeTest() throws Exception {    
    	/* The test generator requires TestGeneratorPortTypePortBindingQSPort_address in TestGeneratorPortTypePortBindingQSServiceLocator class point
    	 * to production url https://ws-osb.ps.utah.gov:443/driverlicense-osb/dld-testgenerator-1.0" as a proxy class.
    	 */
    	
    	try {
        	log.debug("StudentSearchAction generateKnowledgeTest");
        	
        	log.info("Connecting to webservice: " + Constants.Webservice_Test_Generator);
    		QName serviceName = new QName("http://dps.utah.gov/dld/webservice/testgenerator/1.0", "TestGeneratorPortTypePortBindingQSService");
    		TestGeneratorPortTypePortBindingQSService service = new TestGeneratorPortTypePortBindingQSServiceLocator(Constants.Webservice_Test_Generator, serviceName);

    		TestGeneratorPortType port = service.getTestGeneratorPortTypePortBindingQSPort();
    		String instructor = ((Person)request.getSession().getAttribute(Constants.USER_KEY)).getFirstName() + " " + ((Person)request.getSession().getAttribute(Constants.USER_KEY)).getLastName();
    		byte[] contents;
    		switch (Integer.parseInt(request.getParameter("knowledgeTest"))) {
    		case 1:
    			contents = port.getStandardTest(instructor);
    			pdfOut(contents, "StandardTest.pdf");
    			break;
    		case 2:
    			contents = port.getTeacherCopy(instructor);
    			pdfOut(contents, "TeacherCopy.pdf");
    			break;
    		case 3:
    			contents = port.getInstructions(instructor);
    			pdfOut(contents, "ExamInstructions.pdf");
    		}    		
    	} catch (Exception e) {
    		log.error("*** Test generator err ***" + e.getMessage());
    		e.printStackTrace();
    		throw new Exception("Web service test generator error: " + e.getMessage());
    	}
		
    	return null;
    }
    
    public void validateGenerateKnowledgeTest() {
    	
    }
    
    public void validateKnowledgeTestGenerator() {
    	
    }

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	private void pdfOut(byte[] contents, String fileName) throws Exception {
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		ServletOutputStream out = response.getOutputStream();
		
		out.write(contents);
		
		out.flush();
		out.close();
		
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
	
	/** bug???
	 * When date in XML is <ns0:WrittenExamCompletionDateTime>2012-03-19T00:00:00.0</ns0:WrittenExamCompletionDateTime>,
	 * it returns 03/18/2012.
	 * When date in XML is <ns0:WrittenExamCompletionDateTime>2013-07-10T07:50:00.0</ns0:WrittenExamCompletionDateTime>,
	 * it returns 07/10/2013.
	 *  
	 * @param cal
	 * @return calendar date object
	 */
	private Calendar dateRoundUp(Calendar cal) {
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0) {
			cal.add(Calendar.DATE, 1);
		} else if (cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0) {
			cal.add(Calendar.DATE, 1);
		}
		
		return cal;
	}
}


