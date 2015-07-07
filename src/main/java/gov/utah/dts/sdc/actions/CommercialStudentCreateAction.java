 package gov.utah.dts.sdc.actions;

import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameResponseType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.CommercialTimes;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.model.StudentAudit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes", "unused" })
public class CommercialStudentCreateAction extends BaseCommercialStudentAction implements SessionAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(CommercialStudentCreateAction.class);
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Date dob;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /** This insert also updates classroom completion date - Redmine 4315.
     * 
     * @return
     * @throws Exception
     */
    public String insertClassroomCompletion() throws Exception {
        log.debug("insertClassroomCompletion");
        
        // Redmine 4314 - validate completion date
        Collection clList = getTrainingList();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (clList.isEmpty()) {	// not possible.
            if (!isValidCompletionDate(getTrainingCompletionDate())) {
            	addActionError("The Classroom Training Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        } else {
        	Object[] array = clList.toArray();
            CommercialTimes lastRec = (CommercialTimes) array[clList.size() - 1];
        	if (!isValidCompletionDate(getTrainingCompletionDate(), lastRec.getEndTime())) {
                    addActionError("The Classroom Training Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
//                addActionError("Classroom training completion date must be in the between " + sdf.format(lastRec.getEndTime())
//                        + " and " + sdf.format(new Date()) + " inclusively.");
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        }
        
        int webSend = webSend();
        Collection<Object> col = new ArrayList<Object>();
        col.add("Classroom training completion date updated.");
        setCommercialAjaxMessages(col);
        //Don't insert here cause it just gets overridden when control is forwarded to CommercialStudentSearchAction.editStudent.
        //return insertCompletionDates();
        return SUCCESS;
    }

    /** This insert also updates observertion completion date - Redmine 4315.
     * 
     * @return
     * @throws Exception
     */
    public String insertObservationCompletion() throws Exception {
        log.debug("insertObservationCompletion");

        // Redmine 4314 - validate completion date
        Collection obList = getObservationList();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (obList.isEmpty()) {	// not possible.
        	if (!isValidCompletionDate(getObservationCompletionDate(), new Date())) {
            	addActionError("The Observation Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        } else {
        	Object[] array = obList.toArray();
        	CommercialTimes lastRec = (CommercialTimes)array[obList.size() - 1];
        	if (!isValidCompletionDate(getObservationCompletionDate(), lastRec.getEndTime())) {
                    addActionError("The Observation Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
//            	addActionError("Observation completion date must be in the between " + sdf.format(lastRec.getEndTime()) + 
//            			" and " + sdf.format(new Date()) + " inclusively.");
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        }
        
        int webSend = webSend();
        //Don't insert here cause it just gets overridden when control is forwarded to CommercialStudentSearch.editStudent.
        //return insertCompletionDates();
		if (webSend > 0) {
	        Collection<Object> col = new ArrayList<Object>();
	        col.add("Observation completion date updated.");
	        setCommercialAjaxMessages(col);
	        return SUCCESS;
		} else {
			Collection list = getActionErrors();
			setCommercialAjaxMessages(list);
			return INPUT;
		}
    }

    /** This insert also updates btw completion date - Redmine 4315.
     * 
     * @return
     * @throws Exception
     */
    public String insertBtwCompletion() throws Exception {
        log.debug("insertBtwCompletion");
        
        // Redmine 4314 - validate completion date
        Collection btwList = getBehindTheWheelList();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (btwList.isEmpty()) {	// not possible.
        	if (!isValidCompletionDate(getBtwCompletionDate(), new Date())) {
            	addActionError("The Behind The Wheel Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        } else {
        	Object[] array = btwList.toArray();
        	CommercialTimes lastRec = (CommercialTimes)array[btwList.size() - 1];
        	if (!isValidCompletionDate(getBtwCompletionDate(), lastRec.getEndTime())) {
                    addActionError("The Behind The Wheel Completion Date cannot be greater than Today's Date of " + sdf.format(new Date()));
//            	addActionError("Behind the wheel completion date must be in the between " + sdf.format(lastRec.getEndTime()) + 
//            			" and " + sdf.format(new Date()) + " inclusively.");
            	Collection list = getActionErrors();
				setCommercialAjaxMessages(list);
				return INPUT;
        	}
        }
        
        int webSend = webSend();
        //Don't insert here cause it just gets overridden when control is forwarded to CommercialStudentSearch.editStudent.
        //return insertCompletionDates();
        Collection<Object> col = new ArrayList<Object>();
        col.add("Behind the wheel completion date updated.");
        setCommercialAjaxMessages(col);
        return SUCCESS;
    }

    public String inputCommercialStudent() throws Exception {
        log.debug("inputCommercialStudent");
        return INPUT;
    }

    public String createCommercialStudent() throws Exception {
        log.debug("createCommercialStudent ");

        Long studentNumber = createStudentNumber(getClassroomPk());
        log.info("studentNumber is " + studentNumber);
        setStudentNumber(studentNumber);

        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentNumber", getStudentNumber());
        hm.put("firstName", getFirstName());
        hm.put("middleName", getMiddleName());
        hm.put("lastName", getLastName());
        hm.put("homePhone", getPhoneNumber());
        hm.put("dob", getDob());
        hm.put("address1",getAddress1());
        hm.put("address2",getAddress2());
        hm.put("city",getCity());
        hm.put("state",getState());
        hm.put("zip",getZip());
        int student = getStudentService().insert(hm);
        setStudentPk(getSdcService().getLastInsertedId());
        addRoster(getStudentPk(), getClassroomPk());
        if (student > 0) {
            return SUCCESS;
        } else {
            Collection list = getActionErrors();
            setCommercialAjaxMessages(list);
            return INPUT;
        }
    }

    public String updateStudentInformation() throws Exception{
        log.debug("updateStudentInformation");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentPk", getStudentPk());
        hm.put("firstName", getFirstName());
        hm.put("middleName", getMiddleName());
        hm.put("lastName", getLastName());
        hm.put("homePhone", getPhoneNumber());
        hm.put("dob", getDob());
        hm.put("address1",getAddress1());
        hm.put("address2",getAddress2());
        hm.put("city",getCity());
        hm.put("state",getState());
        hm.put("zip",getZip());
        int student = 0;
		try {
			student = getStudentService().updateStudentInformation(hm);
		} catch (DaoException ex) {
			log.error(ex.getMessage(),ex);
		}

		StringBuffer sb = new StringBuffer();
        if (student > 0) {
            sb.append("<br/>Student Information Updated");
        } else {
			sb.append("<br/>Unable to Update Student Information");
        }
            Collection<Object> col = new ArrayList<Object>();
            col.add(sb);
            setCommercialAjaxMessages(col);
            return SUCCESS;
        }
    
    public String viewUpdateStudentInformation() throws Exception {
    	log.debug("viewUpdateStudentInformation");
    	Map<String, Object> hm = new HashMap<String, Object>();
    	hm.put("studentPk", getStudentPk());
    	setCurrentStudent((Student) getStudentService().getStudent(hm));
        setFirstName(getCurrentStudent().getFirstName());
        setMiddleName(getCurrentStudent().getMiddleName());
        setLastName(getCurrentStudent().getLastName());
        setPhoneNumber(getCurrentStudent().getHomePhone());
        setDob(getCurrentStudent().getDob());
        setAddress1(getCurrentStudent().getAddress1());
        setAddress2(getCurrentStudent().getAddress2());
        setCity(getCurrentStudent().getCity());
        setState(getCurrentStudent().getState());
        setZip(getCurrentStudent().getZip());
        setStudentPk(getCurrentStudent().getStudentPk());

    	return INPUT;
    }
    
    
    public String save() throws Exception {
        log.debug("save");
        return SUCCESS;
    }

    public int webSend() throws Exception {
        log.debug("webSend");
        int retVal = 0;
        boolean byName = true;
        
        Object wsResponseObj;
        if (getFileNumber() == null || getFileNumber().length() < 1) {
        	wsResponseObj = getCompletionByName();
        	byName = true;
        } else {
        	wsResponseObj = getCompletionByDl();
        	byName = false;
        }

        String status = "";
        if (wsResponseObj != null) {
        	if (byName) {
        		status = ((CompletionByNameResponseType)wsResponseObj).getStatus();
        	} else {
        		status = ((CompletionByDLResponseType)wsResponseObj).getStatus();
        	}

        	if ("200".equals(status)) {
                retVal = 1;
                
                if (byName) {	// getCompletionByName is executed, update file number
                	HashMap<String, Object> hm = new HashMap<String, Object>();
                	hm.put("studentPk", getCurrentStudent().getStudentPk());
                	
                	// make sure file number is returned from Web service
                	if (((CompletionByNameResponseType)wsResponseObj).getLicenseNumber() != null) {
                		hm.put("fileNumber", ((CompletionByNameResponseType)wsResponseObj).getLicenseNumber());
                	} else {
						retVal = 0;
						String error = "Web service error: File Number is not returned from DL Web service. Please try again.";
                    	addActionError(error);
                    	log.error("Exception thrown by CommercialStudentCreateAction.webSend() " + error);
                	}
                	
                    hm.put("firstName", getCurrentStudent().getFirstName());
                    hm.put("middleName", getMiddleName());
                    hm.put("lastName", getCurrentStudent().getLastName());
                    hm.put("homePhone", getCurrentStudent().getHomePhone());
                    hm.put("dob", getCurrentStudent().getDob());
                	getStudentService().updateStudentInformation(hm);
                } else {		// getCompletionByDl is executed
                    setEligibilityDate(((CompletionByDLResponseType)wsResponseObj).getEligibilityDate());
                    if (getEligibilityDate() == null) {
                        Collection list = getActionErrors();
                        setCommercialAjaxMessages(list);
                    }                	
                }
                
            	// student audit - rm 30821
            	Student auditStudent = new Student();
            	auditStudent.setStudentPk(getStudentPk());
            	StudentAudit sa = new StudentAudit();
            	if (getObservationCompletionDate() != null) {
            		sa.setObservationCompletionDate_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());
            		sa.setObservationCompletionDate_datestamp(new Date());
            	} else if (getTrainingCompletionDate() != null) {
            		sa.setClassroomCompletionDate_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());
            		sa.setClassroomCompletionDate_datestamp(new Date());            		
            	} else if (getBtwCompletionDate() != null) {
            		sa.setBtwCompletionDate_userid(((Person) getSession().get(Constants.USER_KEY)).getEmail());
            		sa.setBtwCompletionDate_datestamp(new Date());
            	}
            	
            	auditStudent.setStudentAudit(sa);
                if (getStudentService().getStudentAuditCount(getStudentPk()) == 0) {
                	getStudentService().insertStudentAudit(auditStudent);
                } else {
                	getStudentService().updateStudentAudit(auditStudent);
                }
            } else {
            	String errorMsg = null;
            	if (byName) {
            		errorMsg = "Web service error: " + ((CompletionByNameResponseType)wsResponseObj).getStatus() + " " + ((CompletionByNameResponseType)wsResponseObj).getStatusDescription(); 
            	} else {
            		errorMsg = "Web service error: " + ((CompletionByDLResponseType)wsResponseObj).getStatus() + " " + ((CompletionByDLResponseType)wsResponseObj).getStatusDescription();
            	}
            	addActionError(errorMsg);
            	log.error("Exception thrown by CommercialStudentCreateAction.webSend() " + errorMsg);
            }
        } else {
            log.debug("Webservice RESULTS EMPTY");
            addActionError("Webservice not available, further information in logs.");
        }
        return retVal;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void ajaxCheckUser() throws Exception {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	Date dob = sdf.parse(request.getParameter("dob"));
    	
    	Map<String, Object> hm = new HashMap<String, Object>();
    	hm.put("firstName", request.getParameter("firstName"));
    	hm.put("lastName", request.getParameter("lastName"));
    	hm.put("dob", dob);
    	hm.put("classroomFk", new Integer(request.getParameter("classroomPk")));
    	
    	List list = getStudentService().getCommercialStudentList(hm);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
    	if (list.size() > 0) {	// exist
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
	 * Checks if date1 is in the between date2 and today date inclusively.
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean isValidCompletionDate(Date date1, Date date2) {
		
		Calendar today = Calendar.getInstance();
		Calendar date1Cal = Calendar.getInstance();
		date1Cal.setTime(date1);
		Calendar date2Cal = Calendar.getInstance();
		date2Cal.setTime(date2);
		
		// set hour, minute, and secod to 0
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		date1Cal.set(Calendar.HOUR_OF_DAY, 0);
		date1Cal.set(Calendar.MINUTE, 0);
		date1Cal.set(Calendar.SECOND, 0);
		date1Cal.set(Calendar.MILLISECOND, 0);
		date2Cal.set(Calendar.HOUR_OF_DAY, 0);
		date2Cal.set(Calendar.MINUTE, 0);
		date2Cal.set(Calendar.SECOND, 0);
		
		if (date2Cal.after(today)) return false;
		
		if (date1Cal.equals(date2Cal) || date1Cal.equals(today)) return true;
		
		if (date1Cal.before(today) && date1Cal.after(date2Cal)) return true;
		
		return false;
	}

    private boolean isValidCompletionDate(Date date1) {

        Calendar today = Calendar.getInstance();
        Calendar date1Cal = Calendar.getInstance();
        date1Cal.setTime(date1);

        // set hour, minute, and secod to 0
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        date1Cal.set(Calendar.HOUR_OF_DAY, 0);
        date1Cal.set(Calendar.MINUTE, 0);
        date1Cal.set(Calendar.SECOND, 0);
        date1Cal.set(Calendar.MILLISECOND, 0);

        if (date1Cal.before(today) || date1Cal.equals(today)) {
            return true;
        }

        return false;
    }

}
