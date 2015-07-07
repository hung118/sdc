package gov.utah.dps.dld.webservice.sdct.gen;

public class StudentDriverPortTypeProxy implements gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType {
  private String _endpoint = null;
  private gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType studentDriverPortType = null;
  
  public StudentDriverPortTypeProxy() {
    _initStudentDriverPortTypeProxy();
  }
  
  public StudentDriverPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initStudentDriverPortTypeProxy();
  }
  
  private void _initStudentDriverPortTypeProxy() {
    try {
      studentDriverPortType = (new gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortTypePortBindingQSServiceLocator()).getStudentDriverPortTypePortBindingQSPort();
      if (studentDriverPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)studentDriverPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)studentDriverPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (studentDriverPortType != null)
      ((javax.xml.rpc.Stub)studentDriverPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.StudentDriverPortType getStudentDriverPortType() {
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType;
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType queryByDL(gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.queryByDL(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType completionByDL(gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.completionByDL(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameResponseType completionByName(gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.completionByName(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameResponseType completeWrittenByName(gov.utah.dps.dld.webservice.sdct.gen.CompleteWrittenByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.completeWrittenByName(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameResponseType completeRoadByName(gov.utah.dps.dld.webservice.sdct.gen.CompleteRoadByNameRequestType request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.completeRoadByName(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.DeleteByDLResponse deleteByDL(gov.utah.dps.dld.webservice.sdct.gen.DeleteByDLRequest request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.deleteByDL(request);
  }
  
  public gov.utah.dps.dld.webservice.sdct.gen.DeleteByNameResponse deleteByName(gov.utah.dps.dld.webservice.sdct.gen.DeleteByNameRequest request) throws java.rmi.RemoteException, gov.utah.dps.dld.webservice.sdct.gen.DataServiceException{
    if (studentDriverPortType == null)
      _initStudentDriverPortTypeProxy();
    return studentDriverPortType.deleteByName(request);
  }
  
  
}