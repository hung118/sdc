
package gov.utah.dts.alm.webservice.model;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.utah.dts.alm.webservice.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.utah.dts.alm.webservice.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadLogResponse }
     * 
     */
    public ReadLogResponse createReadLogResponse() {
        return new ReadLogResponse();
    }

    /**
     * Create an instance of {@link ReadLogRequest }
     * 
     */
    public ReadLogRequest createReadLogRequest() {
        return new ReadLogRequest();
    }

    /**
     * Create an instance of {@link WriteLogRequest }
     * 
     */
    public WriteLogRequest createWriteLogRequest() {
        return new WriteLogRequest();
    }

    /**
     * Create an instance of {@link LogDataFilter }
     * 
     */
    public LogDataFilter createLogDataFilter() {
        return new LogDataFilter();
    }

    /**
     * Create an instance of {@link LogData_Type }
     * 
     */
    public LogData_Type createLogData_Type() {
        return new LogData_Type();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link ResponseMessage }
     * 
     */
    public ResponseMessage createResponseMessage() {
        return new ResponseMessage();
    }

    /**
     * Create an instance of {@link WriteLogResponse }
     * 
     */
    public WriteLogResponse createWriteLogResponse() {
        return new WriteLogResponse();
    }

}
