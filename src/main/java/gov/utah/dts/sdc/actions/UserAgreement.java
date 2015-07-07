package gov.utah.dts.sdc.actions;

import gov.utah.dts.sdc.Constants;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;


public class UserAgreement extends SDCSupport implements ServletRequestAware{
	private static final long serialVersionUID = 7327334440263027571L;
	private Log log = LogFactory.getLog(UserAgreement.class);
	private HttpServletRequest request;
    
    public String execute() {
    	log.debug("********** USER AGREEMENT");
    	return SUCCESS;
    }

    public String doAgree() {
        log.debug("********** USER AGREEMENT - USER AGREED");
        return Constants.AGREE;
    }

    public String doDisagree() {
        log.debug("********** USER AGREEMENT - USER DISAGREED");
		request.getSession().invalidate();
        return Constants.DISAGREE;
    }

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}   
    
}
