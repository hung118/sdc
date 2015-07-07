
package gov.utah.dts.alm.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Application" type="{http://dts.utah.gov/alm/webservice/model}Application"/>
 *         &lt;element name="LogData" type="{http://dts.utah.gov/alm/webservice/model}LogData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "application",
    "logData"
})
@XmlRootElement(name = "WriteLogRequest")
public class WriteLogRequest {

    @XmlElement(name = "Application", required = true)
    protected Application application;
    @XmlElement(name = "LogData", required = true)
    protected LogData_Type logData;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link Application }
     *     
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link Application }
     *     
     */
    public void setApplication(Application value) {
        this.application = value;
    }

    /**
     * Gets the value of the logData property.
     * 
     * @return
     *     possible object is
     *     {@link LogData_Type }
     *     
     */
    public LogData_Type getLogData() {
        return logData;
    }

    /**
     * Sets the value of the logData property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogData_Type }
     *     
     */
    public void setLogData(LogData_Type value) {
        this.logData = value;
    }

}
