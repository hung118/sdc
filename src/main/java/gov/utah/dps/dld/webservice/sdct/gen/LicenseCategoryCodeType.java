/**
 * LicenseCategoryCodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class LicenseCategoryCodeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LicenseCategoryCodeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _LRN = "LRN";
    public static final java.lang.String _FAL = "FAL";
    public static final java.lang.String _TPT = "TPT";
    public static final java.lang.String _HWT = "HWT";
    public static final java.lang.String _OCS = "OCS";
    public static final LicenseCategoryCodeType LRN = new LicenseCategoryCodeType(_LRN);
    public static final LicenseCategoryCodeType FAL = new LicenseCategoryCodeType(_FAL);
    public static final LicenseCategoryCodeType TPT = new LicenseCategoryCodeType(_TPT);
    public static final LicenseCategoryCodeType HWT = new LicenseCategoryCodeType(_HWT);
    public static final LicenseCategoryCodeType OCS = new LicenseCategoryCodeType(_OCS);
    public java.lang.String getValue() { return _value_;}
    public static LicenseCategoryCodeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        LicenseCategoryCodeType enumeration = (LicenseCategoryCodeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static LicenseCategoryCodeType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LicenseCategoryCodeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "LicenseCategoryCodeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
