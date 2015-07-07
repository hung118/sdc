package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.service.ClassroomService;
import gov.utah.dts.sdc.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClassRoomAction extends SDCSupport implements Preparable, ModelDriven, PrincipalAware, SessionAware {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(ClassRoomAction.class);
                    
    private Integer classroomPk;
    private Integer studentFk;
    private String alias;
    private String showHidden;
    private ArrayList studentArray;
    private Classroom currentClassroom;
    private ClassroomService classroomService;
    private PrincipalProxy proxy;
    private Map session;
    private Collection studentList;
    private Collection studentListSave;
    
    private String resultMsg;
    
    public String removeMultiStudents() throws Exception {
        Map<String, Object> hm = new LinkedHashMap<String, Object>();
        Map<String, Object> hm2 = new HashMap<String, Object>();	// for ALM logging - Redmine 6393
        hm.put("classroomPk", classroomPk);
        int result = 0;
        int total = getStudentList().size();
        setStudentListSave(getStudentList());
        for (int x = 0; x < studentArray.size(); x++) {
            hm.put("studentFk", studentArray.get(x));

            hm2.put("studentPk", studentArray.get(x));
            Student student = getStudentService().getStudent(hm2);
            hm.put("studentName", student.getStudentFullName());
            hm.put("groupName", currentClassroom.getAlias());
            hm.put("schoolName", currentClassroom.getSchoolName());
            
            if (getClassroomService().removeStudent(hm) == 1) {
            	result++;
            }
        }
        
        resultMsg = "These " + result + " students of " + total + " available have been deleted from this class.";
        
		if (hasErrors()) {
			return INPUT;
		} else {
			return SUCCESS;
		}
    }

    public String removeStudent() throws Exception {
        log.debug("removeStudent");
        Map<String, Object> hm = new LinkedHashMap<String, Object>();
        hm.put("classroomPk", classroomPk);
        hm.put("studentFk", studentFk);
        
        // for ALM logging - Redmine 6393
        Map<String, Object> hm2 = new HashMap<String, Object>();
        hm2.put("studentPk", studentFk);
        Student student = getStudentService().getStudent(hm2);
        hm.put("studentName", student.getStudentFullName());
        hm.put("groupName", currentClassroom.getAlias());
        hm.put("schoolName", currentClassroom.getSchoolName());
        
        getClassroomService().removeStudent(hm);
        return SUCCESS;
    }

    public String editClassroom() {
        log.debug("********** edit Classroom");
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("enter save");
        log.debug("a " + currentClassroom.getClassroomPk() + " " + currentClassroom.getClassroomNumber());
        getClassroomService().update(currentClassroom);
        return SUCCESS;
    }

    public String createClassroom() throws Exception {
        log.debug("enter createClassroom");
        return insert();
    }

    public String insert() throws Exception {
        log.debug("insert");
        getClassroomService().insert(currentClassroom);
        Integer pk = getSdcService().getLastInsertedId();
        getCurrentClassroom().setClassroomPk(pk);
        return SUCCESS;
    }

    public String doList() throws Exception {
        log.debug("doList");
        return SUCCESS;
    }

    public Collection getAvailableList() throws Exception {
        ClassroomService ps = new ClassroomService(getUmdLogonEmail());
        log.debug("getAvailableList");
        if (getProxy().isUserInRole(Constants.Role_Admin)) {
            log.debug("*** USER IS ADMIN ***");
            return ps.getClassroomList(null);
        } else {
            return ps.getClassroomList(null);
        }
    }

    public Collection getStudentList() throws Exception {
    	if (studentList == null) {
    		studentList = new ArrayList<Student>();
    		StudentService ps = new StudentService();
    		log.debug("getStudentList for classroom " + classroomPk);
    		Classroom c = (Classroom) getModel();
    		Map<String, Object> hm = new HashMap<String, Object>();
    		if (c.getClassroomPk() != null) {
    			hm.put("classroomFk", c.getClassroomPk());
    		} else {
    			hm.put("classroomFk", classroomPk);
    		}
    		if (showHidden == null || "".equals(showHidden) || "0".equals(showHidden)) {
    			hm.put("showHidden", "0");
    		}
            studentList = ps.getCommercialStudentList(hm);
    	}

        return studentList;
    }

    public void setStudentList(Collection studentList) {
    	this.studentList = studentList;
    }
    
    /* public Collection getStudentLabelList() throws Exception {
    StudentService ps = new StudentService();
    log.debug("getStudentLabelList");
    Classroom c = (Classroom) getModel();
    Map hm = new HashMap();
    hm.put("classroomFk",c.getClassroomPk());
    return ps.getCommercialStudentList(hm);
    }
     */

    public void prepare() throws Exception {
        if (getCurrentClassroom() == null) {
            Classroom preFetched = fetch(getClassroomPk());
            if (preFetched != null) {
                setCurrentClassroom(preFetched);
            }
        }
    }

    public Collection getCommercialAjaxMessages() {
        log.debug("getCommercialAjaxMessages");
        List list = (List) session.get(Constants.Commercial_Ajax_Message);
        if(list.isEmpty()){
            log.debug("getCommercialAjaxMessages empty");
        } else {
            for(int x=0;x<list.size();x++){
                log.debug(x + " "+list.get(x));
            }
        }
        return (Collection) session.get(Constants.Commercial_Ajax_Message);
    }

    public Classroom fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for " + tryId);
        Classroom result = null;
        if (tryId != null) {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("classroomPk", tryId);
            result = getClassroomService().getClassroom(hm);
        } else {
            result = new Classroom();
        }
        log.debug("exit fetch for " + tryId);
        return result;
    }

    public String hideStudent() throws Exception {
        log.debug("hideStudent");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", studentFk);
        hm.put("classroomPk", classroomPk);
        hm.put("hide", "1");
        getClassroomService().hideClassroomStudent(hm);
        addActionMessage("Student hidden successfully");
        return SUCCESS;
    }

    public String unhideStudent() throws Exception {
        log.debug("unhideStudent");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", studentFk);
        hm.put("classroomPk", classroomPk);
        hm.put("hide", "0");
        getClassroomService().hideClassroomStudent(hm);
        addActionMessage("Student unhidden successfully");
        return SUCCESS;
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

    public Classroom getCurrentClassroom() {
        return currentClassroom;
    }

    public void setCurrentClassroom(Classroom currentClassroom) {
        this.currentClassroom = currentClassroom;
    }

    public Object getModel() {
        return currentClassroom;
    }

    public void setPrincipalProxy(PrincipalProxy proxy) {
        this.proxy = proxy;
    }

    public PrincipalProxy getProxy() {
        return proxy;
    }

    public String commercialFront() {
        log.debug("********** COMMERCIAL FRONT");
        return SUCCESS;
    }

    public String addStudentNumberAjaxInput() {
        log.debug("********** addStudentNumberAjaxInput");
        return INPUT;
    }

    public String addMultiObservationTimeInput() { // rm 30828
        return INPUT;
    }
    
    public String addMultiTrainingTimeInput() {	// rm 30828
    	return INPUT;
    }
    
    public String removeMultiStudentsInput() {	// rm 30828
        return INPUT;
    }
    
    @Override
    public Map getSession() {
        return session;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public Integer getStudentFk() {
        return studentFk;
    }

    public void setStudentFk(Integer studentFk) {
        this.studentFk = studentFk;
    }
    
    public void setCommercialAjaxMessages(Collection commercialAjaxMessages) {
        session.put(Constants.Commercial_Ajax_Message, commercialAjaxMessages);
    }

    public Integer getClassroomPk() {
        return classroomPk;
    }

    public void setClassroomPk(Integer classroomPk) {
        this.classroomPk = classroomPk;
    }

    public ArrayList getStudentArray() {
        return studentArray;
    }

    public void setStudentArray(ArrayList studentArray) {
        this.studentArray = studentArray;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

	public String getShowHidden() {
		return showHidden;
	}

	public void setShowHidden(String showHidden) {
		this.showHidden = showHidden;
	}
	
	public Collection getInstructorList() throws Exception {
		List instructorList = new ArrayList();
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomPk", getClassroomPk());
		instructorList = getPersonService().getClassroomInstructorSearch(hm);
		return instructorList;
	}
	
	public Collection getBranchList() throws Exception {
		List list = new ArrayList();
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomPk", getClassroomPk());
		list = getClassroomService().getBranchList(hm);
		return list;
	}
	
	public Collection getVehicleList() throws Exception {
		List list = new ArrayList();
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomPk", getClassroomPk());
		list = getVehicleService().getClassroomVehicleSearch(hm);
		return list;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public List<Student> getStudentFromStudentArray() throws Exception { // get student info for student array.
        List<Student> studentFromStudentArray = new ArrayList<Student>();
        for (Object o : studentArray) {
        	for (Object s : getStudentListSave()) {
        		if (Integer.parseInt((String)o) == ((Student)s).getStudentPk().intValue()) {
        			studentFromStudentArray.add((Student)s);
        		}
        	}
        }
        
        return studentFromStudentArray;
	}

	public Collection getStudentListSave() {
		return studentListSave;
	}

	public void setStudentListSave(Collection studentListSave) {
		this.studentListSave = studentListSave;
	}
	
}
