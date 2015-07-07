
package gov.utah.dts.alm.webservice.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for LogData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LogData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LogTypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IPAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LogEntry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationUsername" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="LogId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LogDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogData", propOrder = {

})
public class LogData_Type {

    @XmlElement(name = "UserId", required = true)
    protected String userId;
    @XmlElement(name = "LogTypeCode", required = true)
    protected String logTypeCode;
    @XmlElement(name = "IPAddress", required = true)
    protected String ipAddress;
    @XmlElement(name = "LogEntry")
    protected String logEntry;
    @XmlElement(name = "UserComment")
    protected String userComment;
    @XmlElement(name = "ApplicationUsername")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String applicationUsername;
    @XmlElement(name = "LogId")
    protected BigInteger logId;
    @XmlElement(name = "LogDate")
    protected XMLGregorianCalendar logDate;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the logTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogTypeCode() {
        return logTypeCode;
    }

    /**
     * Sets the value of the logTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogTypeCode(String value) {
        this.logTypeCode = value;
    }

    /**
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the logEntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogEntry() {
        return logEntry;
    }

    /**
     * Sets the value of the logEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogEntry(String value) {
        this.logEntry = value;
    }

    /**
     * Gets the value of the userComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserComment() {
        return userComment;
    }

    /**
     * Sets the value of the userComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserComment(String value) {
        this.userComment = value;
    }

    /**
     * Gets the value of the applicationUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationUsername() {
        return applicationUsername;
    }

    /**
     * Sets the value of the applicationUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationUsername(String value) {
        this.applicationUsername = value;
    }

    /**
     * Gets the value of the logId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLogId() {
        return logId;
    }

    /**
     * Sets the value of the logId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLogId(BigInteger value) {
        this.logId = value;
    }

    /**
     * Gets the value of the logDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLogDate() {
        return logDate;
    }

    /**
     * Sets the value of the logDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLogDate(XMLGregorianCalendar value) {
        this.logDate = value;
    }

}
