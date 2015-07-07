package gov.utah.dts.sdc.actions;

import gov.utah.dts.sdc.Constants;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.SessionAware;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class Home extends SDCSupport implements
        SessionAware, PrincipalAware{
    private Log log = LogFactory.getLog(Home.class);
	private Map session;
    private PrincipalProxy proxy;
    
    public String execute() {
        log.debug("********** HOME *********************************");
        if (getProxy().isUserInRole(Constants.Role_Admin)) {
            log.debug("********** USER IS ADMIN *************************");
            return Constants.ADMIN;
        }else if (getProxy().isUserInRole(Constants.Role_HighSchool)){
            log.debug("********** USER IS IN HIGHSCHOOL ROLE ***********");
            return Constants.HIGHSCHOOL;
        }else if (getProxy().isUserInRole(Constants.Role_Commercial)){
            log.debug("********** USER IS IN COMMERCIAL ROLE ***********");
            return Constants.COMMERCIAL;
        }else if (getProxy().isUserInRole(Constants.Role_ThirdParty)){
            log.debug("********** USER IS IN THIRD PARTY ROLE ***********");
            return Constants.THIRDPARTY;
        } else if(getProxy().isUserInRole(Constants.Role_Guest)){
            log.debug("********** USER IS IN GUEST ROLE ****************");
            session.put("guestMessage", Constants.Guest_No_Role_Message);
            return Constants.GUEST;
        }
        log.debug("********** USER NOT IN HIGHSCHOOL OR GUEST ROLE ***********");
        
        return SUCCESS;
        
    }
    
    public String highSchoolFront() {
        log.debug("********** HIGH SCHOOL FRONT");
        return SUCCESS;
    }
    
   public String thirdPartyFront() {
        log.debug("********** THIRD PARTY FRONT");
        return SUCCESS;
    }
    
    public Map getSession() {
        return session;
    }
    
    public void setSession(Map session) {
        this.session = session;
    }
    
    public void setPrincipalProxy(PrincipalProxy proxy) {
        this.proxy = proxy;
    }
    
    public PrincipalProxy getProxy() {
        return proxy;
        
    }
}
