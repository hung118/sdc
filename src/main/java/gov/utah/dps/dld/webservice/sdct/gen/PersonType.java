/**
 * PersonType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class PersonType  implements java.io.Serializable {
    private java.lang.String givenName;

    private java.lang.String middleName;

    private java.lang.String surName;

    private java.lang.String suffixName;

    private java.util.Date birthDate;

    public PersonType() {
    }

    public PersonType(
           java.lang.String givenName,
           java.lang.String middleName,
           java.lang.String surName,
           java.lang.String suffixName,
           java.util.Date birthDate) {
           this.givenName = givenName;
           this.middleName = middleName;
           this.surName = surName;
           this.suffixName = suffixName;
           this.birthDate = birthDate;
    }


    /**
     * Gets the givenName value for this PersonType.
     * 
     * @return givenName
     */
    public java.lang.String getGivenName() {
        return givenName;
    }


    /**
     * Sets the givenName value for this PersonType.
     * 
     * @param givenName
     */
    public void setGivenName(java.lang.String givenName) {
        this.givenName = givenName;
    }


    /**
     * Gets the middleName value for this PersonType.
     * 
     * @return middleName
     */
    public java.lang.String getMiddleName() {
        return middleName;
    }


    /**
     * Sets the middleName value for this PersonType.
     * 
     * @param middleName
     */
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }


    /**
     * Gets the surName value for this PersonType.
     * 
     * @return surName
     */
    public java.lang.String getSurName() {
        return surName;
    }


    /**
     * Sets the surName value for this PersonType.
     * 
     * @param surName
     */
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }


    /**
     * Gets the suffixName value for this PersonType.
     * 
     * @return suffixName
     */
    public java.lang.String getSuffixName() {
        return suffixName;
    }


    /**
     * Sets the suffixName value for this PersonType.
     * 
     * @param suffixName
     */
    public void setSuffixName(java.lang.String suffixName) {
        this.suffixName = suffixName;
    }


    /**
     * Gets the birthDate value for this PersonType.
     * 
     * @return birthDate
     */
    public java.util.Date getBirthDate() {
        return birthDate;
    }


    /**
     * Sets the birthDate value for this PersonType.
     * 
     * @param birthDate
     */
    public void setBirthDate(java.util.Date birthDate) {
        this.birthDate = birthDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonType)) return false;
        PersonType other = (PersonType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.givenName==null && other.getGivenName()==null) || 
             (this.givenName!=null &&
              this.givenName.equals(other.getGivenName()))) &&
            ((this.middleName==null && other.getMiddleName()==null) || 
             (this.middleName!=null &&
              this.middleName.equals(other.getMiddleName()))) &&
            ((this.surName==null && other.getSurName()==null) || 
             (this.surName!=null &&
              this.surName.equals(other.getSurName()))) &&
            ((this.suffixName==null && other.getSuffixName()==null) || 
             (this.suffixName!=null &&
              this.suffixName.equals(other.getSuffixName()))) &&
            ((this.birthDate==null && other.getBirthDate()==null) || 
             (this.birthDate!=null &&
              this.birthDate.equals(other.getBirthDate())));
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
        if (getGivenName() != null) {
            _hashCode += getGivenName().hashCode();
        }
        if (getMiddleName() != null) {
            _hashCode += getMiddleName().hashCode();
        }
        if (getSurName() != null) {
            _hashCode += getSurName().hashCode();
        }
        if (getSuffixName() != null) {
            _hashCode += getSuffixName().hashCode();
        }
        if (getBirthDate() != null) {
            _hashCode += getBirthDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "PersonType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("givenName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "GivenName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "MiddleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "SurName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suffixName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "SuffixName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "BirthDate"));
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
