/**
 * StudentDriverPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.utah.dps.dld.webservice.sdct.gen;

public interface StudentDriverPortType extends java.rmi.Remote {
    public gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType queryByDL(gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType completionByDL(gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameResponseType completionByName(gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameResponseType completeWrittenByName(gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameResponseType completeRoadByName(gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.DeleteByDLResponse deleteByDL(gov.utah.dps.dld.webservice.sdct.gen.DeleteByDLRequest request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
    public gov.utah.dps.dld.webservice.sdct.gen.DeleteByNameResponse deleteByName(gov.utah.dps.dld.webservice.sdct.gen.DeleteByNameRequest request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException;
}
