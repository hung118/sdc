package gov.utah.dts.sdc.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

@SuppressWarnings("serial")
public class LogoutAction  extends SDCSupport implements ServletRequestAware {

	HttpServletRequest request;
	
	public String execute() {
		request.getSession().invalidate();
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
