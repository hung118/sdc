package gov.utah.dts.sdc.jasper;

/**
 *
 * @author CHWARDLE
 */

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.actions.SDCSupport;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.service.SchoolService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OcsCompletionJasperAction extends SDCSupport implements SessionAware{
    
	private static final long serialVersionUID = 1L;
	
	protected static Log log = LogFactory.getLog(OcsCompletionJasperAction.class);
    private List myList;
    private Map session;
    
    public String execute() throws Exception {
        if(session.get(Constants.Report_OcsCompletionStudent) !=null){
            Student student = (Student) session.get(Constants.Report_OcsCompletionStudent);
            
            SchoolService service = new SchoolService();
            Map hm = new HashMap();
            hm.put("schoolPk",student.getSchoolFk());
            School school = service.getSchool(hm);
            
            student.setSchoolName(school.getSchoolName());
            student.setSchoolNumber(school.getSchoolNumber());
                    
            if (student.getClassroomCompletionSchoolNumber() != null)
            	student.setClassroomCompletionSchoolName(getSchoolName(String.valueOf(student.getClassroomCompletionSchoolNumber().intValue())));
            if (student.getObservationCompletionSchoolNumber() != null)
            	student.setObservationCompletionSchoolName(getSchoolName(String.valueOf(student.getObservationCompletionSchoolNumber().intValue())));
            
            myList = new ArrayList();
            myList.add(student);
            //if all goes well ..
            log.debug("got session student");
            return SUCCESS;
        }else {
            throw new Exception("Error creating completion slip. Could not find student records.");
        }
    }
    
    /**
     * @return Returns the myList.
     */
    public List getMyList() {
        return myList;
    }
    
    public Map getSession() {
        return session;
    }
    
    public void setSession(Map session) {
        this.session = session;
    }
    
}
