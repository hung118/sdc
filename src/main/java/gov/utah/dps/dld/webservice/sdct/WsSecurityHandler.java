package gov.utah.dps.dld.webservice.sdct;

import gov.utah.dts.sdc.Constants;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

/**
 * Web service security handler which is invoked (_call.invoke) by StudentDriverPortTypePortBindingStub class to add soap header with
 * wsse:Security before sending a request to Web services. Make sure the handler is defined in client-config.wsdd. Also for test generator.
 * 
 * @author Hnguyen
 * 
 */
public class WsSecurityHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;
	private static final String OASIS_200401_WSSECURITY_SECEXT_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

	@Override
	public void invoke(MessageContext context) throws AxisFault {
		SOAPEnvelope envelope;
		SOAPHeader header;
		SOAPElement security;
		SOAPElement usernameToken;
		SOAPElement username;
		String userNameText;
		SOAPElement password;
		
		// insert security header for dps webservices http://dps.utah.gov/dld/webservice/sdct/1.0
		if (context.getOperation().getReturnQName() != null && context.getOperation().getReturnQName().getNamespaceURI().contains("/dld/webservice/sdct")) {
			try {
				envelope = context.getMessage().getSOAPPart().getEnvelope();
				header = envelope.getHeader();

				if (header == null) {
					header = envelope.addHeader();
				}

				security = header.addChildElement("Security", "wsse", OASIS_200401_WSSECURITY_SECEXT_NS);
				usernameToken = security.addChildElement("UsernameToken", "wsse");
				
				// set user name
				username = usernameToken.addChildElement("Username", "wsse");
				userNameText = Constants.Webservice_Agency + ":" + Constants.Webservice_User + ":" + Constants.Webservice_Ori;
				username.addTextNode(userNameText);

				// set password
				password = usernameToken.addChildElement("Password", "wsse");
				password.addTextNode(Constants.Webservice_Pass);

			} catch (SOAPException e) {
				e.printStackTrace();
			}
			// insert security header for dps webservices http://dps.utah.gov/dld/webservice/testgenerator/1.0
		} else if (context.getOperation().getReturnQName() != null && context.getOperation().getReturnQName().getNamespaceURI().contains("/testgenerator/1.0")) {
			try {
				envelope = context.getMessage().getSOAPPart().getEnvelope();
				header = envelope.getHeader();

				if (header == null) {
					header = envelope.addHeader();
				}

				security = header.addChildElement("Security", "wsse", OASIS_200401_WSSECURITY_SECEXT_NS);
				usernameToken = security.addChildElement("UsernameToken", "wsse");
				
				// set user name
				username = usernameToken.addChildElement("Username", "wsse");
				userNameText = Constants.Webservice_Agency_testGen + ":" + Constants.Webservice_User_testGen + ":" + Constants.Webservice_Ori_testGen;
				username.addTextNode(userNameText);

				// set password
				password = usernameToken.addChildElement("Password", "wsse");
				password.addTextNode(Constants.Webservice_Pass_testGen);

			} catch (SOAPException e) {
				e.printStackTrace();
			}			
		}
	}
}
