
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
 *         &lt;element name="LogDataFilter" type="{http://dts.utah.gov/alm/webservice/model}LogDataFilter"/>
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
    "logDataFilter"
})
@XmlRootElement(name = "ReadLogRequest")
public class ReadLogRequest {

    @XmlElement(name = "Application", required = true)
    protected Application application;
    @XmlElement(name = "LogDataFilter", required = true)
    protected LogDataFilter logDataFilter;

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
     * Gets the value of the logDataFilter property.
     * 
     * @return
     *     possible object is
     *     {@link LogDataFilter }
     *     
     */
    public LogDataFilter getLogDataFilter() {
        return logDataFilter;
    }

    /**
     * Sets the value of the logDataFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogDataFilter }
     *     
     */
    public void setLogDataFilter(LogDataFilter value) {
        this.logDataFilter = value;
    }

}
