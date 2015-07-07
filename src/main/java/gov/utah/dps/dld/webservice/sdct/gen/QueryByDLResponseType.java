/**
 * QueryByDLResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class QueryByDLResponseType  implements java.io.Serializable {
    private java.util.Calendar messageDateTime;

    private java.lang.String status;

    private java.lang.String statusDescription;

    private gov.utah.dps.dld.webservice.sdct.gen.PersonType subject;

    private java.lang.String[] alert;

    private gov.utah.dps.dld.webservice.sdct.gen.DriverLicenseType driverLicense;

    private gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate;

    public QueryByDLResponseType() {
    }

    public QueryByDLResponseType(
           java.util.Calendar messageDateTime,
           java.lang.String status,
           java.lang.String statusDescription,
           gov.utah.dps.dld.webservice.sdct.gen.PersonType subject,
           java.lang.String[] alert,
           gov.utah.dps.dld.webservice.sdct.gen.DriverLicenseType driverLicense,
           gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
           this.messageDateTime = messageDateTime;
           this.status = status;
           this.statusDescription = statusDescription;
           this.subject = subject;
           this.alert = alert;
           this.driverLicense = driverLicense;
           this.studentDriverCertificate = studentDriverCertificate;
    }


    /**
     * Gets the messageDateTime value for this QueryByDLResponseType.
     * 
     * @return messageDateTime
     */
    public java.util.Calendar getMessageDateTime() {
        return messageDateTime;
    }


    /**
     * Sets the messageDateTime value for this QueryByDLResponseType.
     * 
     * @param messageDateTime
     */
    public void setMessageDateTime(java.util.Calendar messageDateTime) {
        this.messageDateTime = messageDateTime;
    }


    /**
     * Gets the status value for this QueryByDLResponseType.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this QueryByDLResponseType.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the statusDescription value for this QueryByDLResponseType.
     * 
     * @return statusDescription
     */
    public java.lang.String getStatusDescription() {
        return statusDescription;
    }


    /**
     * Sets the statusDescription value for this QueryByDLResponseType.
     * 
     * @param statusDescription
     */
    public void setStatusDescription(java.lang.String statusDescription) {
        this.statusDescription = statusDescription;
    }


    /**
     * Gets the subject value for this QueryByDLResponseType.
     * 
     * @return subject
     */
    public gov.utah.dps.dld.webservice.sdct.gen.PersonType getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this QueryByDLResponseType.
     * 
     * @param subject
     */
    public void setSubject(gov.utah.dps.dld.webservice.sdct.gen.PersonType subject) {
        this.subject = subject;
    }


    /**
     * Gets the alert value for this QueryByDLResponseType.
     * 
     * @return alert
     */
    public java.lang.String[] getAlert() {
        return alert;
    }


    /**
     * Sets the alert value for this QueryByDLResponseType.
     * 
     * @param alert
     */
    public void setAlert(java.lang.String[] alert) {
        this.alert = alert;
    }

    public java.lang.String getAlert(int i) {
        return this.alert[i];
    }

    public void setAlert(int i, java.lang.String _value) {
        this.alert[i] = _value;
    }


    /**
     * Gets the driverLicense value for this QueryByDLResponseType.
     * 
     * @return driverLicense
     */
    public gov.utah.dps.dld.webservice.sdct.gen.DriverLicenseType getDriverLicense() {
        return driverLicense;
    }


    /**
     * Sets the driverLicense value for this QueryByDLResponseType.
     * 
     * @param driverLicense
     */
    public void setDriverLicense(gov.utah.dps.dld.webservice.sdct.gen.DriverLicenseType driverLicense) {
        this.driverLicense = driverLicense;
    }


    /**
     * Gets the studentDriverCertificate value for this QueryByDLResponseType.
     * 
     * @return studentDriverCertificate
     */
    public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType getStudentDriverCertificate() {
        return studentDriverCertificate;
    }


    /**
     * Sets the studentDriverCertificate value for this QueryByDLResponseType.
     * 
     * @param studentDriverCertificate
     */
    public void setStudentDriverCertificate(gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
        this.studentDriverCertificate = studentDriverCertificate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryByDLResponseType)) return false;
        QueryByDLResponseType other = (QueryByDLResponseType) obj;
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
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.alert==null && other.getAlert()==null) || 
             (this.alert!=null &&
              java.util.Arrays.equals(this.alert, other.getAlert()))) &&
            ((this.driverLicense==null && other.getDriverLicense()==null) || 
             (this.driverLicense!=null &&
              this.driverLicense.equals(other.getDriverLicense()))) &&
            ((this.studentDriverCertificate==null && other.getStudentDriverCertificate()==null) || 
             (this.studentDriverCertificate!=null &&
              this.studentDriverCertificate.equals(other.getStudentDriverCertificate())));
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
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getAlert() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlert());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlert(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDriverLicense() != null) {
            _hashCode += getDriverLicense().hashCode();
        }
        if (getStudentDriverCertificate() != null) {
            _hashCode += getStudentDriverCertificate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryByDLResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "QueryByDLResponseType"));
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
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "Subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "PersonType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alert");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "Alert"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverLicense");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "DriverLicense"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "DriverLicenseType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studentDriverCertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverCertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverCertificateType"));
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
