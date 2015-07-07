
package gov.utah.dts.alm.webservice.model;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="LogData" type="{http://dts.utah.gov/alm/webservice/model}LogData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ResponseMessage" type="{http://dts.utah.gov/alm/webservice/model}ResponseMessage"/>
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
    "logData",
    "responseMessage"
})
@XmlRootElement(name = "ReadLogResponse")
public class ReadLogResponse {

    @XmlElement(name = "Application", required = true)
    protected Application application;
    @XmlElement(name = "LogData")
    protected List<LogData_Type> logData;
    @XmlElement(name = "ResponseMessage", required = true)
    protected ResponseMessage responseMessage;

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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogData_Type }
     * 
     * 
     */
    public List<LogData_Type> getLogData() {
        if (logData == null) {
            logData = new ArrayList<LogData_Type>();
        }
        return this.logData;
    }

    /**
     * Gets the value of the responseMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMessage }
     *     
     */
    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    /**
     * Sets the value of the responseMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMessage }
     *     
     */
    public void setResponseMessage(ResponseMessage value) {
        this.responseMessage = value;
    }

}
