/*
 * JasperAction.java
 *
 * Created on April 27, 2007, 12:37 PM
 *
 */

package gov.utah.dts.sdc.jasper;

/**
 *
 * @author CHWARDLE
 */

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.actions.SDCSupport;
import gov.utah.dts.sdc.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "rawtypes" })
public class WrittenCompletionJasperAction extends SDCSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(WrittenCompletionJasperAction.class);
    private List<Object> myList;
    private Map session;

    @Override
    public String execute() throws Exception {
        if (session.get(Constants.Report_WrittenCompletionStudent) != null) {
            Student student = (Student) session.get(Constants.Report_WrittenCompletionStudent);

            /* don't need it. redmine 11677
            SchoolService service = new SchoolService();
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("schoolPk", student.getSchoolFk());
            School school = service.getSchool(hm);

            student.setSchoolName(school.getSchoolName());
            student.setSchoolNumber(school.getSchoolNumber()); */
            if (student.getWrittenCompletionSchoolNumber() != null) {
            	student.setWrittenCompletionSchoolName(getSchoolName(String.valueOf(student.getWrittenCompletionSchoolNumber().intValue())));
            } else {
            	student.setWrittenCompletionSchoolName("Division of Driver License");
            }
            

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

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
