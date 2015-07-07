package gov.utah.dps.dld.webservice.testgenerator._1_0;

public class TestGeneratorPortTypeProxy implements gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType {
  private String _endpoint = null;
  private gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType testGeneratorPortType = null;
  
  public TestGeneratorPortTypeProxy() {
    _initTestGeneratorPortTypeProxy();
  }
  
  public TestGeneratorPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initTestGeneratorPortTypeProxy();
  }
  
  private void _initTestGeneratorPortTypeProxy() {
    try {
      testGeneratorPortType = (new gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortTypePortBindingQSServiceLocator()).getTestGeneratorPortTypePortBindingQSPort();
      if (testGeneratorPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)testGeneratorPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)testGeneratorPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (testGeneratorPortType != null)
      ((javax.xml.rpc.Stub)testGeneratorPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.utah.dps.dld.webservice.testgenerator._1_0.TestGeneratorPortType getTestGeneratorPortType() {
    if (testGeneratorPortType == null)
      _initTestGeneratorPortTypeProxy();
    return testGeneratorPortType;
  }
  
  public byte[] getStandardTest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (testGeneratorPortType == null)
      _initTestGeneratorPortTypeProxy();
    return testGeneratorPortType.getStandardTest(arg0);
  }
  
  public byte[] getTeacherCopy(java.lang.String arg0) throws java.rmi.RemoteException{
    if (testGeneratorPortType == null)
      _initTestGeneratorPortTypeProxy();
    return testGeneratorPortType.getTeacherCopy(arg0);
  }
  
  public byte[] getInstructions(java.lang.String arg0) throws java.rmi.RemoteException{
    if (testGeneratorPortType == null)
      _initTestGeneratorPortTypeProxy();
    return testGeneratorPortType.getInstructions(arg0);
  }
  
  
}