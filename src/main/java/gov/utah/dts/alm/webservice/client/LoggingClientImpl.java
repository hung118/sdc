package gov.utah.dts.alm.webservice.client;

import gov.utah.dts.alm.webservice.model.Application;
import gov.utah.dts.alm.webservice.model.LogData;
import gov.utah.dts.alm.webservice.model.LogDataFilter;
import gov.utah.dts.alm.webservice.model.LogDataService;
import gov.utah.dts.alm.webservice.model.LogData_Type;
import gov.utah.dts.alm.webservice.model.ReadLogRequest;
import gov.utah.dts.alm.webservice.model.ReadLogResponse;
import gov.utah.dts.alm.webservice.model.WriteLogRequest;
import gov.utah.dts.alm.webservice.model.WriteLogResponse;
import gov.utah.dts.sdc.Constants;

import java.net.URL;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * Implements ALM client using JDK built-in jax-ws (wsimport command line). For Axis implementation, see CDL app.
 * 
 * @author HNGUYEN
 *
 */
public class LoggingClientImpl implements LoggingClient {
	
	private String userNameText;
	private String passwordText;
	private String ipAddress;
	private LogData logData;
	
	/** 
	 * Constructor to pass in common parameters for read and write logs.
	 */
	public LoggingClientImpl() throws Exception {
		
		String alm_endpoint = Constants.Webservice_alm_endpoint;
		userNameText = Constants.Webservice_alm_applicationUserName;
		passwordText = Constants.Webservice_alm_secureToken;
		ipAddress = Constants.Webservice_alm_ipAddress;
		
		HeaderHandlerResolver hhr = new HeaderHandlerResolver(userNameText, passwordText);
		URL wsldLocation = new URL(alm_endpoint);
		QName serviceName = new QName("http://dts.utah.gov/alm/webservice/model", "LogDataService"); // where http://dts.utah.gov/alm/webservice/model can be found in ALM wsdl under targetNamespace.
		LogDataService logDataService = new LogDataService(wsldLocation, serviceName);
		logDataService.setHandlerResolver(hhr);
		
		logData = logDataService.getLogDataSoap11();
	}
	
	@Override
	public ReadLogResponse readLog(XMLGregorianCalendar logDateStart, XMLGregorianCalendar logDateEnd, 
			String userIdFilter, String logTypeCodeFilter) throws Exception {

		ReadLogRequest readLogRequest = new ReadLogRequest();
		
		Application application = new Application();
		application.setUsername(userNameText);
		application.setSecureToken(passwordText);
		readLogRequest.setApplication(application);
		
		LogDataFilter logDataFilter = new LogDataFilter();
		logDataFilter.setStartDate(logDateStart);
		logDataFilter.setEndDate(logDateEnd);
		logDataFilter.setUserId(userIdFilter);
		logDataFilter.setLogTypeCode(logTypeCodeFilter);
		//logDataFilter.setIPAddress(ipAddress);
		readLogRequest.setLogDataFilter(logDataFilter);		
		
		ReadLogResponse readLogResponse = logData.readLog(readLogRequest);
		
		return readLogResponse;
	}

	@Override
	public WriteLogResponse writeLog(String userId, String logTypeCode, String logEntry, 
			String userComment) throws Exception {

		WriteLogRequest writeLogRequest = new WriteLogRequest();
		
		Application application = new Application();
		application.setUsername(userNameText);
		application.setSecureToken(passwordText);
		writeLogRequest.setApplication(application);
		
		LogData_Type logData_Type = new LogData_Type();
		logData_Type.setUserId(userId);
		logData_Type.setLogTypeCode(logTypeCode);
		logData_Type.setIPAddress(ipAddress);
		logData_Type.setLogEntry(logEntry);
		logData_Type.setUserComment(userComment);
		writeLogRequest.setLogData(logData_Type);
		
		WriteLogResponse writeLogResponse = logData.writeLog(writeLogRequest);
		
		return writeLogResponse;
	}

	
}
