/**
 * CompletionByDLResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class CompletionByDLResponseType  implements java.io.Serializable {
    private java.util.Calendar messageDateTime;

    private java.lang.String status;

    private java.lang.String statusDescription;

    private java.util.Date eligibilityDate;

    public CompletionByDLResponseType() {
    }

    public CompletionByDLResponseType(
           java.util.Calendar messageDateTime,
           java.lang.String status,
           java.lang.String statusDescription,
           java.util.Date eligibilityDate) {
           this.messageDateTime = messageDateTime;
           this.status = status;
           this.statusDescription = statusDescription;
           this.eligibilityDate = eligibilityDate;
    }


    /**
     * Gets the messageDateTime value for this CompletionByDLResponseType.
     * 
     * @return messageDateTime
     */
    public java.util.Calendar getMessageDateTime() {
        return messageDateTime;
    }


    /**
     * Sets the messageDateTime value for this CompletionByDLResponseType.
     * 
     * @param messageDateTime
     */
    public void setMessageDateTime(java.util.Calendar messageDateTime) {
        this.messageDateTime = messageDateTime;
    }


    /**
     * Gets the status value for this CompletionByDLResponseType.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CompletionByDLResponseType.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the statusDescription value for this CompletionByDLResponseType.
     * 
     * @return statusDescription
     */
    public java.lang.String getStatusDescription() {
        return statusDescription;
    }


    /**
     * Sets the statusDescription value for this CompletionByDLResponseType.
     * 
     * @param statusDescription
     */
    public void setStatusDescription(java.lang.String statusDescription) {
        this.statusDescription = statusDescription;
    }


    /**
     * Gets the eligibilityDate value for this CompletionByDLResponseType.
     * 
     * @return eligibilityDate
     */
    public java.util.Date getEligibilityDate() {
        return eligibilityDate;
    }


    /**
     * Sets the eligibilityDate value for this CompletionByDLResponseType.
     * 
     * @param eligibilityDate
     */
    public void setEligibilityDate(java.util.Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletionByDLResponseType)) return false;
        CompletionByDLResponseType other = (CompletionByDLResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.messageDateTime==null && other.getMessageDateTime()==null) || 
             (this.messageDateTime!=null &&
              this.messageDateTime.equals(other.getMessageDateTime()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.statusDescription==null && other.getStatusDescription()==null) || 
             (this.statusDescription!=null &&
              this.statusDescription.equals(other.getStatusDescription()))) &&
            ((this.eligibilityDate==null && other.getEligibilityDate()==null) || 
             (this.eligibilityDate!=null &&
              this.eligibilityDate.equals(other.getEligibilityDate())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMessageDateTime() != null) {
            _hashCode += getMessageDateTime().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getStatusDescription() != null) {
            _hashCode += getStatusDescription().hashCode();
        }
        if (getEligibilityDate() != null) {
            _hashCode += getEligibilityDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletionByDLResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "CompletionByDLResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "MessageDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StatusDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibilityDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "EligibilityDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
