package gov.utah.dts.sdc.actions;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import gov.utah.dts.sdc.Constants;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author chwardle
 */
public class SecurityAction extends SDCSupport implements ServletRequestAware, SessionAware {

    private Log log = LogFactory.getLog(SecurityAction.class);
    private HttpServletRequest request;
    private Map session;

    @SuppressWarnings("unchecked")
public String execute() {
        log.debug("XXXXXXXXXXXXXXXX Security XXXXXXXXXXXXXXXXX");
        if (request.getHeader("sm_user") != null) {
            log.debug("0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0");
            String sm_user = request.getHeader("sm_user");
            
            String firstname = request.getHeader("GivenName");
            String lastname = request.getHeader("Surname");
            String middlename = request.getHeader("initials");
            String mobilePhone = request.getHeader("mobile");
            String workPhone = request.getHeader("telephoneNumber");
            String homePhone = request.getHeader("homePhone");
            String homeAddress = request.getHeader("homePostalAddress");
            String homeCity = request.getHeader("homeCity");
            String homeState = request.getHeader("homeState");
            String homeZipCode = request.getHeader("homeZipCode");


            String authPersonEmail = request.getHeader("email");

            getSession().put(Constants.USER_DN, sm_user);
            getSession().put(Constants.USER_FirstName, firstname);
            getSession().put(Constants.USER_LastName, lastname);
            getSession().put(Constants.USER_MiddleName, middlename);
            getSession().put(Constants.USER_MobilePhone, mobilePhone);
            getSession().put(Constants.USER_WorkPhone, workPhone);
            getSession().put(Constants.USER_HomePhone, homePhone);
            getSession().put(Constants.USER_HomeAddress, homeAddress);
            getSession().put(Constants.USER_HomeCity, homeCity);
            getSession().put(Constants.USER_HomeState, homeState);
            getSession().put(Constants.USER_HomeZip, homeZipCode);
            getSession().put(Constants.USER_EMAIL, authPersonEmail);
            
            log.info("sm_user = " + sm_user + " firstname = " + firstname + " lastname = " + lastname + " email = " + authPersonEmail);
            //log.debug("middleName = " + middlename + " mobilePhone = " + mobilePhone + " workPhone = " + workPhone + " homePhone = " + homePhone);
            //log.debug("homeAddress = " + homeAddress + " homeCity = " + homeCity + " homeState = " + homeState + " homeZipCode = " + homeZipCode);
            log.debug("0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0");
        } else {
            log.error("Siteminder user is null");
        }
        return LOGIN;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    
}
