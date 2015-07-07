package gov.utah.dts.alm.webservice.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * Implementation class for HandlerResolver interface that is to contain the SOAP HeaderHandler class.
 * 
 * @author HNGUYEN
 *
 */
public class HeaderHandlerResolver implements HandlerResolver {
	
	private String userNameText;
	private String passwordText;

	public HeaderHandlerResolver(String userNameText, String passwordText) {
		this.userNameText = userNameText;
		this.passwordText = passwordText;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {

		List<Handler> handlerChain = new ArrayList<Handler>();
		HeaderHandler hh = new HeaderHandler(getUserNameText(), getPasswordText());
		
		handlerChain.add(hh);
		
		return handlerChain;
	}

	public void setUserNameText(String userNameText) {
		this.userNameText = userNameText;
	}

	public String getUserNameText() {
		return userNameText;
	}

	public void setPasswordText(String passwordText) {
		this.passwordText = passwordText;
	}

	public String getPasswordText() {
		return passwordText;
	}

	
}
