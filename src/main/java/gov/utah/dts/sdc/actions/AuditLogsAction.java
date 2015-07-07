package gov.utah.dts.sdc.actions;

import gov.utah.dts.alm.webservice.client.LoggingClientImpl;
import gov.utah.dts.alm.webservice.model.ReadLogResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Class to handle read logs Web service client.
 * 
 * @author HNGUYEN
 *
 */
public class AuditLogsAction extends SDCSupport implements ServletRequestAware {
    
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(AuditLogsAction.class);
	
	private HttpServletRequest request;
	private String fromDate;
	private String toDate;
	private String userEmail;
       
    public String doSearchAuditLogs() throws Exception {
        log.debug("doSearchAuditLogs");

        return SUCCESS;
    }
    
    public String doListAuditLogs() throws Exception {
        
    	log.debug("doListAuditLogs");
    	GregorianCalendar gDate = new GregorianCalendar();
    	
    	Date fDate = null;
    	if (fromDate.length() <= 10) {
    		fDate = (new SimpleDateFormat("MM/dd/yyyy")).parse(fromDate); // e.g. 02/3/2011
    	} else {
    		fDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(fromDate.substring(0, 10)); // e.g. 2011-03-21T11:31:19.000-06:00
    	}
    	gDate.setTime(fDate);   	
    	XMLGregorianCalendar xmlFromDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    	xmlFromDate.setDay(gDate.get(Calendar.DAY_OF_MONTH));
    	xmlFromDate.setMonth(gDate.get(Calendar.MONTH) + 1);
    	xmlFromDate.setYear(gDate.get(Calendar.YEAR));
    	
    	Date tDate = null;
    	if (toDate.length() <= 10) {
    		tDate = (new SimpleDateFormat("MM/dd/yyyy")).parse(toDate); // e.g. 02/3/2011
    	} else {
    		tDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(toDate.substring(0, 10)); // e.g. 2011-03-21T11:31:19.000-06:00
    	}
    	gDate.setTime(tDate);
    	XMLGregorianCalendar xmlToDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    	xmlToDate.setDay(gDate.get(Calendar.DAY_OF_MONTH));
    	xmlToDate.setMonth(gDate.get(Calendar.MONTH) + 1);
    	xmlToDate.setYear(gDate.get(Calendar.YEAR));
    	
    	ReadLogResponse readResponse = (new LoggingClientImpl()).readLog(xmlFromDate, xmlToDate, userEmail.toUpperCase(), "");
    	request.getSession().setAttribute("logsList", readResponse.getLogData());
    	
        return SUCCESS;
    }

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToDate() {
		return toDate;
	}

	public String doListLogs() throws Exception {
		return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
