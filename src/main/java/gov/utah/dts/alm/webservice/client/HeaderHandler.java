package gov.utah.dts.alm.webservice.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Implement class for SOAPHandler interface.
 * 
 * @author HNGUYEN
 *
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {
	
	private String userNameText;
	private String passwordText;

	public HeaderHandler (String userNameText, String passwordText) {
		this.userNameText = userNameText;
		this.passwordText = passwordText;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outboundProperty.booleanValue()) {
			SOAPMessage message = context.getMessage();
			try {
				SOAPEnvelope envelop = context.getMessage().getSOAPPart().getEnvelope();
				SOAPHeader header = envelop.addHeader();
				
				SOAPElement security = header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
				SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
				usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
				
				SOAPElement username = usernameToken.addChildElement("Username", "wsse");
				username.addTextNode(getUserNameText());
				
				SOAPElement password = usernameToken.addChildElement("Password", "wsse");
				//password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
				password.addTextNode(getPasswordText());

				// print the outbound SOAP message to System.out
				message.writeTo(System.out);
				System.out.println("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
                //This handler does nothing with the response from the Web Service so
                //we just print out the SOAP message.
                SOAPMessage message = context.getMessage();
                message.writeTo(System.out);
                System.out.println("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return outboundProperty;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
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
