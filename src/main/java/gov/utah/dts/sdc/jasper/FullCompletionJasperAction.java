package gov.utah.dts.sdc.jasper;

/**
 *
 * @author CHWARDLE
 */

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.actions.SDCSupport;
import gov.utah.dts.sdc.model.CompletionSlip;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.service.NewsService;
import gov.utah.dts.sdc.service.SchoolService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FullCompletionJasperAction extends SDCSupport implements SessionAware{
    
	private static final long serialVersionUID = 1L;
	
	protected static Log log = LogFactory.getLog(FullCompletionJasperAction.class);
	private List myList;
    private Map session;
    
    public String execute() throws Exception {
        if(session.get(Constants.Report_FullCompletionStudent) !=null){
            Student student = (Student) session.get(Constants.Report_FullCompletionStudent);
            
            SchoolService service = new SchoolService();
            Map hm = new HashMap();
            
//            hm.put("schoolPk",student.getSchoolFk());
//            School school = service.getSchool(hm);
//            
//            student.setSchoolName(school.getSchoolName());
//            student.setSchoolNumber(school.getSchoolNumber());
            
            if (student.getSchoolFk() != null) {
                hm.put("schoolPk", student.getSchoolFk());
                School school = service.getSchool(hm);
                student.setSchoolName(school.getSchoolName());
                student.setSchoolNumber(school.getSchoolNumber());
                student.setHomeStudy(school.getHomeStudy());
            }
            
            if (student.getWrittenCompletionSchoolNumber() != null)
            	student.setWrittenCompletionSchoolName(getSchoolName(String.valueOf(student.getWrittenCompletionSchoolNumber().intValue())));
            if (student.getClassroomCompletionSchoolNumber() != null)
            	student.setClassroomCompletionSchoolName(getSchoolName(String.valueOf(student.getClassroomCompletionSchoolNumber().intValue())));
            if (student.getObservationCompletionSchoolNumber() != null)
            	student.setObservationCompletionSchoolName(getSchoolName(String.valueOf(student.getObservationCompletionSchoolNumber().intValue())));
            if (student.getBtwCompletionSchoolNumber() != null)
            	student.setBtwCompletionSchoolName(getSchoolName(String.valueOf(student.getBtwCompletionSchoolNumber().intValue())));
            if (student.getRoadCompletionSchoolNumber() != null)
            	student.setRoadCompletionSchoolName(getSchoolName(String.valueOf(student.getRoadCompletionSchoolNumber().intValue())));

            // Redmine 30825 - get db completion slip total hours
            NewsService csService = new NewsService();
            List csList =  csService.getCompletionSlips();
            for (int i = 0; i < csList.size(); i++) {
            	CompletionSlip cs = (CompletionSlip)csList.get(i);
            	switch (cs.getName()) {
            	case "highschool.homestudy":
            		student.setClassroomHoursHighHome(cs.getValue());
            		break;
            	case "highschool.classroom":
            		student.setClassroomHoursHigh(cs.getValue());
            		break;
            	case "highschool.observation":
            		student.setObservationHoursHigh(cs.getValue());
            		break;
            	case "highschool.btw":
            		student.setBtwHoursHigh(cs.getValue());
            		break;
            	case "commercial.homestudy":
            		student.setClassroomHoursComHome(cs.getValue());
            		break;
            	case "commercial.classroom":
            		student.setClassroomHoursCom(cs.getValue());
            		break;
            	case "commercial.observation":
            		student.setObservationHoursCom(cs.getValue());
            		break;
            	case "commercial.btw":
            		student.setBtwHoursCom(cs.getValue());
            	}         	
            }
            
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
