/**
 * StudentDriverPortTypePortBindingQSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public class StudentDriverPortTypePortBindingQSServiceLocator extends org.apache.axis.client.Service implements gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingQSService {

    public StudentDriverPortTypePortBindingQSServiceLocator() {
    }


    public StudentDriverPortTypePortBindingQSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StudentDriverPortTypePortBindingQSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StudentDriverPortTypePortBindingQSPort
    private java.lang.String StudentDriverPortTypePortBindingQSPort_address = "https://ws-osb-test.ps.utah.gov:443/driverlicense-osb/dld-sdct-3.0";

    public java.lang.String getStudentDriverPortTypePortBindingQSPortAddress() {
        return StudentDriverPortTypePortBindingQSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StudentDriverPortTypePortBindingQSPortWSDDServiceName = "StudentDriverPortTypePortBindingQSPort";

    public java.lang.String getStudentDriverPortTypePortBindingQSPortWSDDServiceName() {
        return StudentDriverPortTypePortBindingQSPortWSDDServiceName;
    }

    public void setStudentDriverPortTypePortBindingQSPortWSDDServiceName(java.lang.String name) {
        StudentDriverPortTypePortBindingQSPortWSDDServiceName = name;
    }

    public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType getStudentDriverPortTypePortBindingQSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StudentDriverPortTypePortBindingQSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStudentDriverPortTypePortBindingQSPort(endpoint);
    }

    public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType getStudentDriverPortTypePortBindingQSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingStub _stub = new gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingStub(portAddress, this);
            _stub.setPortName(getStudentDriverPortTypePortBindingQSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStudentDriverPortTypePortBindingQSPortEndpointAddress(java.lang.String address) {
        StudentDriverPortTypePortBindingQSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingStub _stub = new gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingStub(new java.net.URL(StudentDriverPortTypePortBindingQSPort_address), this);
                _stub.setPortName(getStudentDriverPortTypePortBindingQSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("StudentDriverPortTypePortBindingQSPort".equals(inputPortName)) {
            return getStudentDriverPortTypePortBindingQSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverPortTypePortBindingQSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/sdct/1.0", "StudentDriverPortTypePortBindingQSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StudentDriverPortTypePortBindingQSPort".equals(portName)) {
            setStudentDriverPortTypePortBindingQSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
