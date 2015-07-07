/**
 * TestGeneratorPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.testgenerator._1_0;

public interface TestGeneratorPortType extends java.rmi.Remote {
    public byte[] getStandardTest(java.lang.String arg0) throws java.rmi.RemoteException;
    public byte[] getTeacherCopy(java.lang.String arg0) throws java.rmi.RemoteException;
    public byte[] getInstructions(java.lang.String arg0) throws java.rmi.RemoteException;
}
