/**
 * CompletionByDLRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class CompletionByDLRequestType  implements java.io.Serializable {
    private java.lang.String licenseNumber;

    private gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate;

    public CompletionByDLRequestType() {
    }

    public CompletionByDLRequestType(
           java.lang.String licenseNumber,
           gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
           this.licenseNumber = licenseNumber;
           this.studentDriverCertificate = studentDriverCertificate;
    }


    /**
     * Gets the licenseNumber value for this CompletionByDLRequestType.
     * 
     * @return licenseNumber
     */
    public java.lang.String getLicenseNumber() {
        return licenseNumber;
    }


    /**
     * Sets the licenseNumber value for this CompletionByDLRequestType.
     * 
     * @param licenseNumber
     */
    public void setLicenseNumber(java.lang.String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }


    /**
     * Gets the studentDriverCertificate value for this CompletionByDLRequestType.
     * 
     * @return studentDriverCertificate
     */
    public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType getStudentDriverCertificate() {
        return studentDriverCertificate;
    }


    /**
     * Sets the studentDriverCertificate value for this CompletionByDLRequestType.
     * 
     * @param studentDriverCertificate
     */
    public void setStudentDriverCertificate(gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType studentDriverCertificate) {
        this.studentDriverCertificate = studentDriverCertificate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletionByDLRequestType)) return false;
        CompletionByDLRequestType other = (CompletionByDLRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.licenseNumber==null && other.getLicenseNumber()==null) || 
             (this.licenseNumber!=null &&
              this.licenseNumber.equals(other.getLicenseNumber()))) &&
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
        if (getLicenseNumber() != null) {
            _hashCode += getLicenseNumber().hashCode();
        }
        if (getStudentDriverCertificate() != null) {
            _hashCode += getStudentDriverCertificate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletionByDLRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "CompletionByDLRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "LicenseNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
