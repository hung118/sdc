package gov.utah.dts.sdc.interceptors;

import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Action;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.model.Person;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthenticationInterceptor implements Interceptor  {
    
    public void destroy() {}
    
    public void init() {}
    
    private String LOGIN_INPUT = "login_input";
    private Log log = LogFactory.getLog(AuthenticationInterceptor.class);
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        
        Map session = actionInvocation.getInvocationContext().getSession();
        Person person = (Person) session.get(Constants.USER_KEY);
        boolean isAuthenticated = (person != null) ;
        log.debug("Authenticating a " + actionInvocation.getProxy().getNamespace());
        log.debug("Authenticating b " + actionInvocation.getInvocationContext().getName());
        
        if (!isAuthenticated) {
            //return LOGIN_INPUT;
            return Action.LOGIN;
        } else {
            return actionInvocation.invoke();
        }
    }
}
