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


public class CompletionTestsJasperAction extends SDCSupport implements SessionAware{
    
    protected static Log log = LogFactory.getLog(FullCompletionJasperAction.class);
    private List myList;
    private Map session;
    private Date reportStartDate;
    private Date reportEndDate;
    private Integer schoolFk;
    
    public String execute() throws Exception {
        log.debug("execute");
        if(session.get(Constants.Report_CompletionTest) !=null){
             myList = (List) session.get(Constants.Report_CompletionTest);
             if (myList.isEmpty()){
                 addActionError("Selected School Currently Does Not Have Any Data");
                 return INPUT;
             }
           log.debug("got session report");
            return SUCCESS;
        }else {
            throw new Exception("Error creating completion slip. Could not find student records.");
        }
    }
    
    public String input() throws Exception {
        return SUCCESS;
    }
    
    
public String generateCompletionDatesReport() throws Exception {
        log.debug("generateCompletionDatesReport");
        SchoolService service = new SchoolService();
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("schoolPk",getSchoolFk());
        hm.put("reportStartDate",getReportStartDate());
        hm.put("reportEndDate",getReportEndDate());
        hm.put("schoolType","0");
        List reportList = service.getCompletionTests(hm);
        //
                
        session.put(Constants.Report_CompletionTest, reportList);
        return Constants.REPORT; //return pdf
       
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

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }
    
}
