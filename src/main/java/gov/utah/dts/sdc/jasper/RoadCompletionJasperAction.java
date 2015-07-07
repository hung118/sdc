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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;


public class RoadCompletionJasperAction extends SDCSupport {

    protected static Log log = LogFactory.getLog(RoadCompletionJasperAction.class);
    private List<Object> myList;

    @Override
    public String execute() throws Exception {
        if (getSession().get(Constants.Report_WrittenCompletionStudent) != null) {
            Student student = (Student) getSession().get(Constants.Report_WrittenCompletionStudent);

            SchoolService service = new SchoolService();
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("schoolPk", student.getSchoolFk());
            School school = service.getSchool(hm);

            student.setSchoolName(school.getSchoolName());
            student.setSchoolNumber(school.getSchoolNumber());

            myList = new ArrayList<Object>();
            myList.add(student);
            //if all goes well ..
            log.debug("got session student");
            return SUCCESS;
        } else {
            throw new Exception("Error creating completion slip. Could not find student records.");
        }
    }

    /**
     * @return Returns the myList.
     */
    public List getMyList() {
        return myList;
    }
}
