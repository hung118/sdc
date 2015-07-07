package gov.utah.dts.sdc.actions;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.service.RoleTypesService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import org.apache.struts2.interceptor.ApplicationAware;

import com.opensymphony.xwork2.ActionSupport;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.service.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SDCSupport extends ActionSupport implements ApplicationAware, SessionAware {

	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(SDCSupport.class);
    private AuditService auditService;
    private SDCService sdcService;
    private StudentService studentService;
    private PersonService personService;
    private VehicleService vehicleService;
    private ClassroomService classroomService;
    private SchoolService schoolService;
    private Map application;
    private Map session;
    private Person loggedInPerson;
    private String umdLogonEmail;
    private Integer umdPersonPk;

	public Map getApplication() {
        return application;
    }

    public void setApplication(Map application) {
        this.application = application;
    }

    public void createStateMap() {
        //A Map containing the names of states and abbreviations
        log.debug("createStateMap");
        Map<String, String> abbrevMap = new TreeMap<String, String>();

        abbrevMap.put("AL", "ALABAMA");
        abbrevMap.put("AK", "ALASKA");
        abbrevMap.put("AZ", "ARIZONA");
        abbrevMap.put("AR", "ARKANSAS");
        abbrevMap.put("CA", "CALIFORNIA");
        abbrevMap.put("CO", "COLORADO");
        abbrevMap.put("CT", "CONNECTICUT");
        abbrevMap.put("DE", "DELAWARE");
        abbrevMap.put("FL", "FLORIDA");
        abbrevMap.put("GA", "GEORGIA");
        abbrevMap.put("HI", "HAWAII");
        abbrevMap.put("ID", "IDAHO");
        abbrevMap.put("IL", "ILLINOIS");
        abbrevMap.put("IN", "INDIANA");
        abbrevMap.put("IA", "IOWA");
        abbrevMap.put("KS", "KANSAS");
        abbrevMap.put("KY", "KENTUCKY");
        abbrevMap.put("LA", "LOUISIANA");
        abbrevMap.put("ME", "MAINE");
        abbrevMap.put("MD", "MARYLAND");
        abbrevMap.put("MA", "MASSACHUSETTS");
        abbrevMap.put("MI", "MICHIGAN");
        abbrevMap.put("MN", "MINNESOTA");
        abbrevMap.put("MS", "MISSISSIPPI");
        abbrevMap.put("MO", "MISSOURI");
        abbrevMap.put("MT", "MONTANA");
        abbrevMap.put("NE", "NEBRASKA");
        abbrevMap.put("NV", "NEVADA");
        abbrevMap.put("NH", "NEW HAMPSHIRE");
        abbrevMap.put("NJ", "NEW JERSEY");
        abbrevMap.put("NM", "NEW MEXICO");
        abbrevMap.put("NY", "NEW YORK");
        abbrevMap.put("NC", "NORTH CAROLINA");
        abbrevMap.put("ND", "NORTH DAKOTA");
        abbrevMap.put("OH", "OHIO");
        abbrevMap.put("OK", "OKLAHOMA");
        abbrevMap.put("OR", "OREGON");
        abbrevMap.put("PA", "PENNSYLVANIA");
        abbrevMap.put("RI", "RHODE ISLAND");
        abbrevMap.put("SC", "SOUTH CAROLINA");
        abbrevMap.put("SD", "SOUTH DAKOTA");
        abbrevMap.put("TN", "TENNESSEE");
        abbrevMap.put("TX", "TEXAS");
        abbrevMap.put("UT", "UTAH");
        abbrevMap.put("VT", "VERMONT");
        abbrevMap.put("VA", "VIRGINIA");
        abbrevMap.put("WA", "WASHINGTON");
        abbrevMap.put("WV", "WEST VIRGINIA");
        abbrevMap.put("WI", "WISCONSIN");
        abbrevMap.put("WY", "WYOMING");
        setStateMap(abbrevMap);
    }

    private Student setAllSchoolNumbers(Student student) throws DaoException {
        if (student.getSchoolFk() != null) {
            String number = getSchoolNumber(student.getSchoolFk());
            Integer schoolNumber = Integer.valueOf(number);
            log.debug("allSchoolNumbers schoolNumber is " + schoolNumber);
            if (student.getBtwCompletionSchoolNumber() == null) {
                student.setBtwCompletionSchoolNumber(schoolNumber);
            }
            if (student.getClassroomCompletionSchoolNumber() == null) {
                student.setClassroomCompletionSchoolNumber(schoolNumber);
            }
            if (student.getObservationCompletionSchoolNumber() == null) {
                student.setObservationCompletionSchoolNumber(schoolNumber);
            }
            if (student.getRoadCompletionSchoolNumber() == null) {
                student.setRoadCompletionSchoolNumber(schoolNumber);
            }
            if (student.getWrittenCompletionSchoolNumber() == null) {
                student.setWrittenCompletionSchoolNumber(schoolNumber);
            }
        }
        return student;
    }

	private void setStateMap(Map stateMap) {
        getApplication().put(Constants.STATE_MAP, stateMap);
    }

    public Map getStateMap() {
        if (getApplication().get(Constants.STATE_MAP) == null) {
            createStateMap();
        }
        return (Map) getApplication().get(Constants.STATE_MAP);
    }

	public Collection getHighSchoolList() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        SchoolService service = new SchoolService();

        List schoolList = new ArrayList();
        try {
            hm.put("personFk", getUmdPersonPk());
            hm.put("schoolType", Constants.SchoolType_HighSchool);
        } catch (Exception e) {
            log.error("* getHighSchoolList ERR********", e);
        }
        schoolList = service.getUserSchoolLabelValue(hm);
        getSession().put(Constants.SchoolType_HighSchool_List, schoolList);
        return schoolList;
    }
	
	public Collection getHighSchoolList2() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        SchoolService service = new SchoolService();

        List schoolList = new ArrayList();
        try {
            hm.put("personFk", getUmdPersonPk());
            hm.put("schoolType", Constants.SchoolType_HighSchool);
        } catch (Exception e) {
            log.error("* getHighSchoolList ERR********", e);
        }
        schoolList = service.getUserSchoolLabelValue2(hm);

        return schoolList;
    }

    public Collection getSchoolListAll() throws Exception {
        SchoolService service = new SchoolService();
        List schoolList = new ArrayList();
        schoolList = service.getAllSchoolLabelValue(null);
        return schoolList;
    }

    public Collection getHighSchoolListAll() throws Exception {
        SchoolService service = new SchoolService();
        List schoolList = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolType", Constants.SchoolType_HighSchool);
        schoolList = service.getAllSchoolLabelValue(hm);
        //getSession().put("allSchoolList", schoolList);
        return schoolList;
    }
    
    public Collection getHighSchoolListAll2() throws Exception {
        SchoolService service = new SchoolService();
        List schoolList = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolType", Constants.SchoolType_HighSchool);
        schoolList = service.getAllSchoolLabelValue2(hm);

        return schoolList;
    }

    public Collection getCommercialSchoolList() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        SchoolService service = new SchoolService();

        List schoolList = new ArrayList();
        try {
            hm.put("personFk", getUmdPersonPk());
            hm.put("schoolType", Constants.SchoolType_Commercial);
        } catch (Exception e) {
            log.debug("* getCommercialSchoolList *ERR********", e);
        }
        schoolList = service.getUserSchoolLabelValue(hm);
        getSession().put(Constants.SchoolType_Commercial_List, schoolList);
        return schoolList;
    }

    public Collection getCommercialSchoolListAll() throws Exception {
        SchoolService service = new SchoolService();
        List schoolList = new ArrayList();
        log.debug("getCommercialSchoolListAll 1");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolType", Constants.SchoolType_Commercial);
        schoolList = service.getAllSchoolLabelValue(hm);
        //getSession().put("allSchoolList", schoolList);
        return schoolList;
    }

    public Collection getAllRolesList() throws Exception {
        RoleTypesService s = new RoleTypesService();
        log.debug("getRolesList");
        return s.getRolesList(null);
    }

    public Collection getNewsList() throws Exception {
        log.debug("getNewsList");
        Map<String, Object> hm = new HashMap<String, Object>();
        NewsService service = new NewsService();
        PersonRolesService prs = new PersonRolesService();

        try {
            hm.put("personPk", getUmdPersonPk());
            hm.put("rolesList", prs.getPersonRoles(hm));
            hm.remove("personPk");
        } catch (Exception e) {
            log.debug("* newsList *ERR********", e);
        }

        return service.getList(hm);
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getUpdatedEmail() {
        log.debug("getUpdatedEmail " + getUmdLogonEmail());
        return getUmdLogonEmail();
    }

    public Collection getHighSchoolRoadTestInstructorList() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        PersonService service = new PersonService();

        try {
            hm.put("schoolList", getHighSchoolListKeys());
        } catch (Exception e) {
            log.debug("******* ERR ********", e);
        }
        return service.getInstructorLabelValue(hm);
    }

    private Integer[] getHighSchoolListKeys() throws Exception {
        List school;

        if (getSession().get(Constants.SchoolType_HighSchool_List) != null) {
            school = (List) getSession().get(Constants.SchoolType_HighSchool_List);
        } else {
            Object[] array = getHighSchoolList().toArray();
            school = Arrays.asList(array);
        }

        Integer[] retVal = new Integer[school.size()];
        for (int i = 0; i < school.size(); i++) {
            School s = (School) school.get(i);
            retVal[i] = s.getSchoolPk();
        }
        return retVal;
    }

    public Date blankDate(String s) {
        return blankDate(s, "");
    }

    public Date blankDate(String s, String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.debug("************** blankDate, " + d + " = " + s);
        if (s != null) {
            if (!s.equals("")) {
                try {
                    if (s.equals("0000-00-00")) {
                        return null;
                    }
                    java.util.Date date = sdf.parse(s);
                    log.debug("blankDate 2, date.toString() " + date.toString());
                    return date;
                } catch (ParseException ex) {
                    log.error("PARSER EXCEPTION", ex);
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Integer blankInteger(String s) {
        return blankInteger(s, "");
    }

    public Integer blankInteger(String s, String d) {
        log.debug("************** blankInteger, " + d + " = " + s);
        if (s != null) {
            if (!s.equals("")) {
                return new Integer(s);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
/*
 *	Test decodeTime
 	public static void main(String args[]) {
		SDCSupport sdcSupport = new SDCSupport();
		int result;
		
		try {
			result = sdcSupport.decodeTime("00:13");
			result = sdcSupport.decodeTime("23:59");
			result = sdcSupport.decodeTime("00:00");
			result = sdcSupport.decodeTime("15:51");
			result = sdcSupport.decodeTime("-19:51");
		} catch (Exception e) {
		}
		try {
			result = sdcSupport.decodeTime("19:-33");
		} catch (Exception e) {
		}
	}
*/	

	// returns # of milliseconds in the time
	// generally this is used to add to a date milliseconds number so as to get a date/time milliseconds combo
    public synchronized int decodeTime(String s) throws Exception {
		String parts[] = s.split(":");
		boolean valid;
		int hour = 0;
		int minute = 0;
		if (valid = parts.length == 2) {
			hour = Integer.parseInt(parts[0]);
			minute = Integer.parseInt(parts[1]);
			valid = hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
		}
		if (!valid) {
            throw new Exception("Invalid time value (hh:mm): \"" + s + "\".");
        }
		return (int) TimeUnit.MILLISECONDS.convert((hour * 60) + minute, TimeUnit.MINUTES);
    }

    /*Returns todays date without the time component.*/
    public Date getTodaysDate() {
        return truncateDate(new Date());
    }

// Truncates the time component from a date/time value.
// (The default time zone is used to determine the begin of the day).
    public Date truncateDate(Date d) {
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.clear();
        gc1.setTime(d);
        int year = gc1.get(Calendar.YEAR);
        int month = gc1.get(Calendar.MONTH);
        int day = gc1.get(Calendar.DAY_OF_MONTH);
        GregorianCalendar gc2 = new GregorianCalendar(year, month, day);
        return gc2.getTime();
    }

// Returns true if string s is blank from position p to the end.
    public boolean isRestOfStringBlank(String s, int p) {
        while (p < s.length() && Character.isWhitespace(s.charAt(p))) {
            p++;
        }
        return p >= s.length();
    }

    public String getSchoolNumber(Integer schoolFk) throws DaoException {
        SchoolService service = new SchoolService();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolPk", schoolFk);
        hm.put("showDeleted", "true");
        School school = service.getSchool(hm);
        String schoolNumber = school.getSchoolNumber();
        return schoolNumber;
    }

    public String getSchoolName(String schoolNumber) throws DaoException {
        SchoolService service = new SchoolService();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolNumber", schoolNumber);
        School school = service.getSchool(hm);

        return school.getSchoolName();
    }
    
    public String sendStringDate(Date date) {
        return sendStringDate(false, date);
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

    public void checkAuditDates(Student student) throws DaoException {
        log.debug("enter checkAuditDates");
        student = setAllSchoolNumbers(student);
        Map<String, Object> btwHm = checkBtwAudit(student);
        Map<String, Object> classroomHm = checkClassroomAudit(student);
        Map<String, Object> observationHm = checkObservationAudit(student);
        Map<String, Object> roadHm = checkRoadAudit(student);
        Map<String, Object> writtenHm = checkWrittenAudit(student);

        if (btwHm != null) {
            int count = countBasicCompletionAudit(btwHm);
            if (count < 1) {
                //means date fk not in db, so add it here.
                insertBasicCompletionAuditDate(btwHm);
            }
        }

        if (classroomHm != null) {
            int count = countBasicCompletionAudit(classroomHm);
            if (count < 1) {
                //means date fk not in db, so add it here.
                insertBasicCompletionAuditDate(classroomHm);
            }
        }

        if (observationHm != null) {
            int count = countBasicCompletionAudit(observationHm);
            if (count < 1) {
                //means date fk not in db, so add it here.
                insertBasicCompletionAuditDate(observationHm);
            }
        }

        if (roadHm != null) {
            int count = countRoadAudit(roadHm);
            if (count < 1) {
                //means date fk not in db, so add it here.
                insertRoadAuditDate(roadHm);
            }
        }

        if (writtenHm != null) {
            int count = countWrittenAudit(writtenHm);
            if (count < 1) {
                //means date fk not in db, so add it here.
                insertWrittenAuditDate(writtenHm);
            }
        }
    }

    private Map<String, Object> checkBtwAudit(Student student) {
        Map<String, Object> hm = null;
        if (student.getBehindWheelCompletionDate() != null) {
            if (student.getBtwCompletionSchoolNumber() != null) {
                hm = new HashMap<String, Object>();
                hm.put("studentFk", student.getStudentPk());
                hm.put("schoolNumber", student.getBtwCompletionSchoolNumber());
                hm.put("completionDate", student.getBehindWheelCompletionDate());
                hm.put("completionType", Constants.CompletionType_BehindTheWheel);
            }
        }
        return hm;
    }

    private Map<String, Object> checkClassroomAudit(Student student) {
        Map<String, Object> hm = null;
        if (student.getClassroomCompletionDate() != null) {
            if (student.getClassroomCompletionSchoolNumber() != null) {
                hm = new HashMap<String, Object>();
                hm.put("studentFk", student.getStudentPk());
                hm.put("schoolNumber", student.getClassroomCompletionSchoolNumber());
                hm.put("completionDate", student.getClassroomCompletionDate());
                hm.put("completionType", Constants.CompletionType_ClassroomTraining);
            }
        }
        return hm;
    }

    private Map<String, Object> checkObservationAudit(Student student) {
        Map<String, Object> hm = null;
        if (student.getObservationCompletionDate() != null) {
            if (student.getObservationCompletionSchoolNumber() != null) {
                hm = new HashMap<String, Object>();
                hm.put("studentFk", student.getStudentPk());
                hm.put("schoolNumber", student.getObservationCompletionSchoolNumber());
                hm.put("completionDate", student.getObservationCompletionDate());
                hm.put("completionType", Constants.CompletionType_Observation);
            }
        }
        return hm;
    }

    private Map<String, Object> checkRoadAudit(Student student) {
        log.debug("checkRoadAudit");
        Map<String, Object> hm = null;
        if (student.getRoadTestCompletionDate() != null) {
            if (student.getRoadCompletionSchoolNumber() != null) {
                hm = new HashMap<String, Object>();
                //if the school is is 1-499 or 6001-6500 its a dl employee. so won't have a score
                //but dont care cause its not going to be viewed by users.
                hm.put("studentFk", student.getStudentPk());
                hm.put("completionDate", student.getRoadTestCompletionDate());

                int fk = student.getRoadCompletionSchoolNumber().intValue();
                //the values of dl employees
                if ((fk >= 1 && fk <= 499) || (fk >= 6001 && fk <= 6500)) {
                    hm.put("schoolNumber", new Integer(0));
                } else {
                    hm.put("schoolNumber", student.getRoadCompletionSchoolNumber());
                }

                if (student.getRoadTestScore() != null) {
                    hm.put("roadScore", student.getRoadTestScore());
                }
                if (student.getRoadTestInstructorFk() != null) {
                    hm.put("roadInstructorFk", student.getRoadTestInstructorFk());
                }
            }
        }
        return hm;
    }

    public Map<String, Object> checkWrittenAudit(Student student) {
        Map<String, Object> hm = null;
        if (student.getWrittenTestCompletionDate() != null) {
            if (student.getWrittenCompletionSchoolNumber() != null) {
                hm = new HashMap<String, Object>();
                hm.put("studentFk", student.getStudentPk());
                hm.put("completionDate", student.getWrittenTestCompletionDate());

                int fk = student.getWrittenCompletionSchoolNumber().intValue();
                //the values of dl employees
                if ((fk >= 1 && fk <= 499) || (fk >= 6001 && fk <= 6500)) {
                    hm.put("writtenScore", new Integer(101));
                    hm.put("schoolNumber", new Integer(0));
                } else {
                    if (student.getWrittenTestScore() != null) {
                    	hm.put("writtenScore", student.getWrittenTestScore());
                    } else {
                    	hm.put("writtenScore", new Integer(0));
                    }
                    
                    hm.put("schoolNumber", student.getWrittenCompletionSchoolNumber());
                }
            }
        }
        return hm;
    }

    private int countBasicCompletionAudit(Map<String, Object> hm) throws DaoException {
        return getAuditService().getBaseEqualsCount(hm);
    }

    private int countRoadAudit(Map<String, Object> hm) throws DaoException {
        return getAuditService().getRoadEqualsCount(hm);
    }

    private int countWrittenAudit(Map<String, Object> hm) throws DaoException {
        return getAuditService().getWrittenEqualsCount(hm);
    }

    public void insertAuditDates(Student student) throws DaoException {
        Map<String, Object> btwHm = checkBtwAudit(student);
        Map<String, Object> classroomHm = checkClassroomAudit(student);
        Map<String, Object> observationHm = checkObservationAudit(student);
        Map<String, Object> roadHm = checkRoadAudit(student);
        Map<String, Object> writtenHm = checkWrittenAudit(student);

        if (btwHm != null) {
            insertBasicCompletionAuditDate(btwHm);
        }

        if (classroomHm != null) {
            insertBasicCompletionAuditDate(classroomHm);
        }

        if (observationHm != null) {
            insertBasicCompletionAuditDate(observationHm);
        }

        if (roadHm != null) {
            insertRoadAuditDate(roadHm);
        }

        if (writtenHm != null) {
            insertWrittenAuditDate(writtenHm);
        }
    }

    private int insertBasicCompletionAuditDate(Map<String, Object> hm) throws DaoException {
        return getAuditService().baseCompletionInsert(hm);
    }

    public int insertRoadAuditDate(Map<String, Object> hm) throws DaoException {
        return getAuditService().roadCompletionInsert(hm);
    }

    public int updateRoadAuditDate(Map<String, Object> hm) throws DaoException {
    	return getAuditService().roadCompletionUpdate(hm);
    }
    
    public int deleteRoadAuditDate(Map<String, Integer> hm) throws DaoException {
    	return getAuditService().roadCompletionDelete(hm);
    }

    public int insertWrittenAuditDate(Map<String, Object> hm) throws DaoException {
        return getAuditService().writtenCompletionInsert(hm);
    }

    public AuditService getAuditService() {
        if (auditService == null) {
            setAuditService(new AuditService(getUmdLogonEmail()));
        }
        return auditService;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public StudentService getStudentService() {
        if (studentService == null) {
            setStudentService(new StudentService(getUmdLogonEmail()));
        }
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public PersonService getPersonService() {
        if (personService == null) {
            setPersonService(new PersonService(getUmdLogonEmail()));
        }
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public SDCService getSdcService() {
        if (sdcService == null) {
            setSdcService(new SDCService());
        }
        return sdcService;
    }

    public void setSdcService(SDCService sdcService) {
        this.sdcService = sdcService;
    }

    public VehicleService getVehicleService() {
        if (vehicleService == null) {
            setVehicleService(new VehicleService(getUmdLogonEmail()));
        }
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public SchoolService getSchoolService() {
        if (schoolService == null) {
            setSchoolService(new SchoolService(getUmdLogonEmail()));
        }
        return schoolService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public ClassroomService getClassroomService() {
        if (classroomService == null) {
            setClassroomService(new ClassroomService(getUmdLogonEmail()));
        }
        return classroomService;
    }

    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    public Person getLoggedInPerson() {
        if (loggedInPerson == null) {
            Person temp = (Person) getSession().get(Constants.USER_KEY);
            setLoggedInPerson(temp);
        }
        return loggedInPerson;
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public String getUmdLogonEmail() {
        if (umdLogonEmail == null) {
            String umdLogon = getLoggedInPerson().getEmail();
            setUmdLogonEmail(umdLogon);
        }
        return umdLogonEmail;
    }

    public void setUmdLogonEmail(String umdLogonEmail) {
        this.umdLogonEmail = umdLogonEmail;
    }

    public Integer getUmdPersonPk() {
        if (umdPersonPk == null) {
            Integer pk = getLoggedInPerson().getPersonPk();
            setUmdPersonPk(pk);
        }
        return umdPersonPk;
    }

    public void setUmdPersonPk(Integer pk) {
        this.umdPersonPk = pk;
    }
	
}