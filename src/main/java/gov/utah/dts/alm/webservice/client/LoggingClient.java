package gov.utah.dts.alm.webservice.client;

import javax.xml.datatype.XMLGregorianCalendar;

import gov.utah.dts.alm.webservice.model.ReadLogResponse;
import gov.utah.dts.alm.webservice.model.WriteLogResponse;

public interface LoggingClient {
	
	public WriteLogResponse writeLog(String userId, String logTypeCode, String logEntry, String userComment) throws Exception; 
	
	public ReadLogResponse readLog(XMLGregorianCalendar logDateStart, XMLGregorianCalendar logDateEnd, 
			String userIdFilter, String logTypeCodeFilter) throws Exception;
}
