package gov.utah.dts.util;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.i18n.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom handler logs all request and response SOAP messages to catalina_sdc.log file which is defined in 
 * log4j.properties file.
 * 
 * To log SOAP messages, the Axis configuration file client-config.wsdd must include the following line:
 * 		<handler name="log" type="java:gov.utah.dts.util.AxisClientSOAPLogger" /> 
 * and specify it in the request and response flows.
 * 
 * @author hnguyen
 *
 */
public class AxisClientSOAPLogger extends BasicHandler {

	private static final long serialVersionUID = 1L;
	
	protected static Log log = LogFactory.getLog(AxisClientSOAPLogger.class);
	
	long start = -1;
	
	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		if (msgContext.getPastPivot() == false) {
			start = System.currentTimeMillis();
		} else {
			log.info("Axis client SOAP messages:");
			logSoapMessages(msgContext);
		}
	}

	private void logSoapMessages(MessageContext msgContext) throws AxisFault {
		try {
			Message inMsg = msgContext.getRequestMessage();
			Message outMsg = msgContext.getResponseMessage();
			
			log.info("Begin SOAP.");
			if (start != -1) {
				log.info(Messages.getMessage("elapsed00", "" + (System.currentTimeMillis() - start)));
			}
            log.info(Messages.getMessage("inMsg00",
                    (inMsg == null ? "null" : inMsg.getSOAPPartAsString())));
            log.info(Messages.getMessage("outMsg00",
                    (outMsg == null ? "null" : outMsg.getSOAPPartAsString())));
            log.info("End SOAP");
		} catch (Exception e) {
			log.error(Messages.getMessage("exception00"), e);
			throw AxisFault.makeFault(e);
		}
	}
	
	public void onFault(MessageContext msgContext) {
        try {
            logSoapMessages(msgContext);
        } catch (AxisFault axisFault) {
            log.error(Messages.getMessage("exception00"), axisFault);
        }
	}
}
