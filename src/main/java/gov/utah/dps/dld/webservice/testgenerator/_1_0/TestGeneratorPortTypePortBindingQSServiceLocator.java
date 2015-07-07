/**
 * TestGeneratorPortTypePortBindingQSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.testgenerator._1_0;

import gov.utah.dts.sdc.Constants;

public class TestGeneratorPortTypePortBindingQSServiceLocator extends org.apache.axis.client.Service implements gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingQSService {

    public TestGeneratorPortTypePortBindingQSServiceLocator() {
    }


    public TestGeneratorPortTypePortBindingQSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TestGeneratorPortTypePortBindingQSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TestGeneratorPortTypePortBindingQSPort
    //private java.lang.String TestGeneratorPortTypePortBindingQSPort_address = "https://ws-osb.ps.utah.gov:443/driverlicense-osb/dld-testgenerator-1.0";
    private java.lang.String TestGeneratorPortTypePortBindingQSPort_address = Constants.Webservice_Test_Generator;

    public java.lang.String getTestGeneratorPortTypePortBindingQSPortAddress() {
        return TestGeneratorPortTypePortBindingQSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TestGeneratorPortTypePortBindingQSPortWSDDServiceName = "TestGeneratorPortTypePortBindingQSPort";

    public java.lang.String getTestGeneratorPortTypePortBindingQSPortWSDDServiceName() {
        return TestGeneratorPortTypePortBindingQSPortWSDDServiceName;
    }

    public void setTestGeneratorPortTypePortBindingQSPortWSDDServiceName(java.lang.String name) {
        TestGeneratorPortTypePortBindingQSPortWSDDServiceName = name;
    }

    public gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType getTestGeneratorPortTypePortBindingQSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TestGeneratorPortTypePortBindingQSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTestGeneratorPortTypePortBindingQSPort(endpoint);
    }

    public gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType getTestGeneratorPortTypePortBindingQSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingStub _stub = new gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingStub(portAddress, this);
            _stub.setPortName(getTestGeneratorPortTypePortBindingQSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTestGeneratorPortTypePortBindingQSPortEndpointAddress(java.lang.String address) {
        TestGeneratorPortTypePortBindingQSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingStub _stub = new gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingStub(new java.net.URL(TestGeneratorPortTypePortBindingQSPort_address), this);
                _stub.setPortName(getTestGeneratorPortTypePortBindingQSPortWSDDServiceName());
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
        if ("TestGeneratorPortTypePortBindingQSPort".equals(inputPortName)) {
            return getTestGeneratorPortTypePortBindingQSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/testgenerator/1.0", "TestGeneratorPortTypePortBindingQSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dps.utah.gov/dld/webservice/testgenerator/1.0", "TestGeneratorPortTypePortBindingQSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TestGeneratorPortTypePortBindingQSPort".equals(portName)) {
            setTestGeneratorPortTypePortBindingQSPortEndpointAddress(address);
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
