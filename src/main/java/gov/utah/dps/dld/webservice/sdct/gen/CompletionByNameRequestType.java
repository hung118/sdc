/**
 * CompletionByNameRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class CompletionByNameRequestType  implements java.io.Serializable {
    private gov.utah.dps.dld.webservice.sdct.gen.PersonType subject;

    private gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate;

    public CompletionByNameRequestType() {
    }

    public CompletionByNameRequestType(
           gov.utah.dps.dld.webservice.sdct.gen.PersonType subject,
           gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
           this.subject = subject;
           this.studentDriverCertificate = studentDriverCertificate;
    }


    /**
     * Gets the subject value for this CompletionByNameRequestType.
     * 
     * @return subject
     */
    public gov.utah.dps.dld.webservice.sdct.gen.PersonType getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this CompletionByNameRequestType.
     * 
     * @param subject
     */
    public void setSubject(gov.utah.dps.dld.webservice.sdct.gen.PersonType subject) {
        this.subject = subject;
    }


    /**
     * Gets the studentDriverCertificate value for this CompletionByNameRequestType.
     * 
     * @return studentDriverCertificate
     */
    public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType getStudentDriverCertificate() {
        return studentDriverCertificate;
    }


    /**
     * Sets the studentDriverCertificate value for this CompletionByNameRequestType.
     * 
     * @param studentDriverCertificate
     */
    public void setStudentDriverCertificate(gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
        this.studentDriverCertificate = studentDriverCertificate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletionByNameRequestType)) return false;
        CompletionByNameRequestType other = (CompletionByNameRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
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
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getStudentDriverCertificate() != null) {
            _hashCode += getStudentDriverCertificate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletionByNameRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "CompletionByNameRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "Subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "PersonType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studentDriverCertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverCertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverCertificateType"));
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
