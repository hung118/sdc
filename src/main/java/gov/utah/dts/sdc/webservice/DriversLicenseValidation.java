/*
 * DriversLicenseValidation.java
 *
 * Created on May 13, 2005, 3:45 PM
 */

package gov.utah.dts.sdc.webservice;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import javax.xml.rpc.ParameterMode;
import javax.xml.namespace.QName;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import gov.utah.dts.sdc.Constants;

/**Sends the DL/ID card #.  Returns the name, DOB and permit of the DL holder.
 * Response XML will contain either the ID card or DL info (both are shown in
 * the response XML example) depending on which was searched, along with the
 * respective expiration date.
 *
 * @author  chwardle
 */


public class DriversLicenseValidation {
    protected static Log log = LogFactory.getLog(DriversLicenseValidation.class);
    private Call call;
    private QName qn;
    private boolean requestError; // true if exception occured
    private String requestErrorMessage; // error message text
    public String notValidReason = null;
    public String transactionParameters;
    public String endpoint = Constants.Webservice_EndPoint;
    
    // Test program
    public java.util.Map performSearch( java.util.Map valsEntered ) throws Exception {
        log.debug("PERFORM SEARCH");
        java.util.Map children = new java.util.HashMap();
        try{
            String userName = (String)valsEntered.get("userName");
            String password =(String)valsEntered.get("password");
            String systemID = (String)valsEntered.get("systemID");
            String transactionID = (String)valsEntered.get("transactionID");
            String agency = (String)valsEntered.get("agency");
            String ori = (String)valsEntered.get("ori");
            String state = (String)valsEntered.get("state");
            String descriptorType =(String)valsEntered.get("descriptorType");
            String descriptorClass =(String)valsEntered.get("descriptorClass");
            
            String transactionParameters = getTransactionParameters();
            //So that this can be used as a encrypt/decrypt
            String xmlName = "udps:TransactionParameters";
            
            //build this section first so that it can be sent with encrypt method
            //This block can be used alternately to the above block
            StringBuffer sbDestinations = new StringBuffer("<udps:Destinations><udps:Destination><j:Agency><j:OrganizationID><j:ID>")
                    .append(state)
                    .append("</j:ID></j:OrganizationID></j:Agency></udps:Destination></udps:Destinations>");
            String destinations = sbDestinations.toString();
            
            StringBuffer xmlEncoded = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><udps:UDPSXML xmlns:j=\"http://www.it.ojp.gov/jxdm/3.0\" xmlns:udps=\"http://webservices.ucjis.utah.gov/udpsxml/release/1.0\" >")
                    .append("<udps:DocumentDescriptor type=\""+descriptorType)
                    .append("\" class=\""+ descriptorClass)
                    .append("\" authenticator=\"UCJIS\" routingCode=\"L\"/>")
                    .append("<udps:Header>")
                    .append("<udps:Version>3.0</udps:Version><udps:System><j:ID>")
                    .append(systemID)
                    .append("</j:ID></udps:System><udps:TransactionID><j:ID>")
                    .append(transactionID)
                    .append("</j:ID></udps:TransactionID><udps:Submitter><udps:UDPSAgency><j:ID>")
                    .append(agency)
                    .append("</j:ID></udps:UDPSAgency><udps:UDPSAuthentication><udps:UDPSLogon encrypted=\"true\"")
                    .append(" source=\"" +xmlName+ "\">")
                    .append(encrypt(userName,transactionParameters,xmlName))
                    .append("</udps:UDPSLogon><udps:UDPSPassword encrypted=\"true\"")
                    .append(" source=\"" +xmlName+ "\">")
                    .append(encrypt(password,transactionParameters,xmlName))
                    .append("</udps:UDPSPassword></udps:UDPSAuthentication></udps:Submitter>")
                    .append("<udps:InitiatingAgency><j:Agency><j:OrganizationORIID><j:ID>")
                    .append(ori)
                    .append("</j:ID></j:OrganizationORIID></j:Agency></udps:InitiatingAgency>")
                    .append(destinations)
                    .append("</udps:Header>")
                    .append(transactionParameters)
                    .append("</udps:UDPSXML>");
            
            log.debug("performSearch xml "+ xmlEncoded.toString());
            
            char[] data = Base64.encode(xmlEncoded.toString().getBytes() );
            
            /*debug purposes only
             isXMLHeaderValid(new StringBuffer(xmlEncoded.toString()));
             log.debug("getNotValidReason "+ getNotValidReason());
             */
            
            String str = new String(data);
            String retval = validateDriversLicense(str);
            if(retval != null){
              log.debug("XML response");
              //log.info("Here it is \n"+ decrypt(retval));
              children = gov.utah.dts.sdc.webservice.XMLUtils.getContentsAsMap(decrypt(retval));
              log.debug("XML map results " +children);
            } else {
                log.debug("XML response is null");
            }
           
            //log.debug("Resulting string is "+retval);
            //log.debug("Resulting decryted String " + decrypt(retval));
        }catch(Exception e){
            requestError = true;
            requestErrorMessage="ERROR IN DriversLicenseValidation: "+e.getMessage();
            log.error(e);
            throw e;
        }
        return children;
    }
    
    
    // Initialize the Axis setup
    public DriversLicenseValidation() {
        
        try {
            Service service = new Service();
            call = (Call) service.createCall();
            log.debug("Web Service Endpoint = "+ endpoint);
            call.setTargetEndpointAddress( new java.net.URL(endpoint) );
        } catch (Throwable t) {
            handleError("SOAPRequest (constructor) had an Exception.",t);
        }
    }
    
    // Return true in an error was found
    public boolean error() {
        return requestError;
    }
    
    // Return the error message
    public String getErrorText() {
        String msg;
        if (requestError) {
            msg = requestErrorMessage;
        } else {
            msg = "";
        }
        return msg;
    }
    
    public String validateDriversLicense(String data){
        try {
            log.debug("enter validateDriversLicense");
            call.removeAllParameters();
            call.setOperationName( "processEncodedDocument" );
            //call.setOperationName( "processXMLDocument");
            call.setReturnType( XMLType.XSD_STRING );
            call.addParameter( "String_1", XMLType.XSD_STRING, ParameterMode.IN);
            String ret = (String)call.invoke( new Object [] {data});
            return ret;
        } catch (Throwable t) {
            log.debug("******************** validateDriversLicense ERROR");
            handleError("SOAPRequest (validateDriversLicense) had an Exception.",t);
            return null;
        }
    }
    
    // Set error flags, print stack trace
    public void handleError(String text, Throwable t) {
        requestError = true;
        requestErrorMessage = t.toString();
        //log.debug(text);
        //log.debug(t);
        t.printStackTrace();
        return;
    }
    
/*
    // Test program
    public static void main( String[] args ) throws Exception {
        System.out.println("MAIN GO");
        
        HashMap map = new java.util.HashMap();
        map.put("userName",Constants.Webservice_User);
        map.put("password",Constants.Webservice_Pass);
        map.put("systemID",Constants.Webservice_SystemId);
        map.put("transactionID",Constants.Webservice_TransactionId);
        map.put("agency",Constants.Webservice_Agency);
        map.put("ori",Constants.Webservice_Ori);
        map.put("state",Constants.Webservice_State);
        map.put("descriptorType",Constants.Webservice_DescriptorType);
        map.put("descriptorClass",Constants.Webservice_QueryByDL);
        
        log.debug(Constants.Webservice_User + " Was Here");
        DriversLicenseValidation dlv = new DriversLicenseValidation();
        dlv.setTransactionParameters(getWrittenTestTransactionParam("chwardle@utah.gov","firstName", null, "lastName", "2007-01-01","2007-01-02" ,"00001"));
        log.debug("THE RESULTS " + dlv.performSearch(map));
    }
  */
  
    public String encrypt(String data, String xml, String xmlName){
        try {
            //log.debug( "XML String: " + xml );
            String resultStr = UCJISCipher.encrypt( xml, data, xmlName);
            //log.debug( "Encrypted and Encoded: " + resultStr );
            String de_resultStr = UCJISCipher.decryptString( xml, resultStr, xmlName);
            //log.debug( "Decrypted: " + de_resultStr );
            return resultStr;
        } catch ( Throwable t ) {
            t.printStackTrace();
            return data;
        }
    }
    
    public String decrypt(String data){
        try {
            char[] chars = data.toCharArray();
            byte[] bytes = Base64.decode( chars );
            String resultStr = (new String(bytes));
            //log.debug( "Decrypted: " + resultStr );
            return resultStr;
        } catch ( Throwable t ) {
            t.printStackTrace();
            return data;
        }
    }
    
    public String getWrittenTestTransactionParam(String umdLogon,String firstName, String middleName, String lastName, String dob, String wtDate, String schoolNumber){
        //build this section first so that it can be sent with encrypt method
        StringBuffer sbTransactionParam = new StringBuffer("<udps:TransactionParameters><udps:UMDLogon>")
                .append(umdLogon)
                .append("</udps:UMDLogon>")
                .append("<j:Subject><j:PersonName><j:PersonGivenName>")
                .append(firstName)
                .append("</j:PersonGivenName><j:PersonMiddleName personNameInitialIndicator=\"\">")
                .append(middleName)
                .append("</j:PersonMiddleName><j:PersonSurName>")
                .append(lastName)
                .append("</j:PersonSurName></j:PersonName><j:PersonBirthDate>")
                .append(dob)
                .append("</j:PersonBirthDate></j:Subject>")
                .append("<udps:StudentDriverCertificate><udps:WrittenExamDate>")
                .append(wtDate)
                .append("</udps:WrittenExamDate><udps:SchoolID><j:ID>")
                .append(schoolNumber)
                .append("</j:ID></udps:SchoolID>")
                .append("</udps:StudentDriverCertificate></udps:TransactionParameters>");
        return sbTransactionParam.toString();
    }
    
    public String getCompleteRoadByNameTransactionParam(String umdLogon,String firstName, String middleName, String lastName, String dob, String rtDate, String schoolNumber){
        //build this section first so that it can be sent with encrypt method
        StringBuffer sbTransactionParam = new StringBuffer("<udps:TransactionParameters><udps:UMDLogon>")
                .append(umdLogon)
                .append("</udps:UMDLogon>")
                .append("<j:Subject><j:PersonName><j:PersonGivenName>")
                .append(firstName)
                .append("</j:PersonGivenName><j:PersonMiddleName personNameInitialIndicator=\"\">")
                .append(middleName)
                .append("</j:PersonMiddleName><j:PersonSurName>")
                .append(lastName)
                .append("</j:PersonSurName></j:PersonName><j:PersonBirthDate>")
                .append(dob)
                .append("</j:PersonBirthDate></j:Subject>")
                .append("<udps:StudentDriverCertificate><udps:RoadTestCompletionDate>")
                .append(rtDate)
                .append("</udps:RoadTestCompletionDate><udps:SchoolID><j:ID>")
                .append(schoolNumber)
                .append("</j:ID></udps:SchoolID>")
                .append("</udps:StudentDriverCertificate></udps:TransactionParameters>");
        return sbTransactionParam.toString();
    }
    
    public String getCompletionByNameTransactionParam(String umdLogon,String firstName, String middleName, String lastName, String dob, String classroomDate, String observationDate, String schoolNumber){
        //build this section first so that it can be sent with encrypt method
        StringBuffer sbTransactionParam = new StringBuffer("<udps:TransactionParameters><udps:UMDLogon>")
                .append(umdLogon)
                .append("</udps:UMDLogon>")
                .append("<j:Subject><j:PersonName><j:PersonGivenName>")
                .append(firstName)
                .append("</j:PersonGivenName><j:PersonMiddleName personNameInitialIndicator=\"\">")
                .append(middleName)
                .append("</j:PersonMiddleName><j:PersonSurName>")
                .append(lastName)
                .append("</j:PersonSurName></j:PersonName><j:PersonBirthDate>")
                .append(dob)
                .append("</j:PersonBirthDate></j:Subject>")
                .append("<udps:StudentDriverCertificate>")
                .append("<udps:ClassroomCompletionDate>")
                .append(classroomDate)
                .append("</udps:ClassroomCompletionDate>")
                .append("<udps:ObservationCompletionDate>")
                .append(observationDate)
                .append("</udps:ObservationCompletionDate>")
                .append("<udps:SchoolID><j:ID>")
                .append(schoolNumber)
                .append("</j:ID></udps:SchoolID>")
                .append("</udps:StudentDriverCertificate></udps:TransactionParameters>");
        return sbTransactionParam.toString();
    }
    
    public String getQueryByDLTransactionParam(String umdLogon,String driversLicense){
        StringBuffer sbTransactionParam = new StringBuffer("<udps:TransactionParameters><udps:UMDLogon>")
                .append(umdLogon)
                .append("</udps:UMDLogon>")
                .append("<j:Subject><j:PersonAssignedIDDetails><j:PersonDriverLicenseID><j:ID>")
                .append(driversLicense)
                .append("</j:ID><j:IDJurisdictionCode>UT</j:IDJurisdictionCode>")
                .append("</j:PersonDriverLicenseID></j:PersonAssignedIDDetails></j:Subject></udps:TransactionParameters>");
        return sbTransactionParam.toString();
    }
    
    public  String getCompletionByDLTransactionParam(
            String umdLogon,
            String driversLicense,
            String ccDate,
            String ocDate,
            String rtcDate,
            String btwDate,
            String wtDate,
            String schoolNumber){
        StringBuffer sbTransactionParam = new StringBuffer("<udps:TransactionParameters><udps:UMDLogon>")
                .append(umdLogon)
                .append("</udps:UMDLogon>")
                .append("<j:Subject><j:PersonAssignedIDDetails><j:PersonDriverLicenseID><j:ID>")
                .append(driversLicense)
                .append("</j:ID><j:IDJurisdictionCode>UT</j:IDJurisdictionCode>")
                .append("</j:PersonDriverLicenseID></j:PersonAssignedIDDetails></j:Subject>")
                .append("<udps:StudentDriverCertificate>")
                .append("<udps:ClassroomCompletionDate>")
                .append(ccDate)
                .append("</udps:ClassroomCompletionDate>")
                .append("<udps:ObservationCompletionDate>")
                .append(ocDate)
                .append("</udps:ObservationCompletionDate>")
                .append("<udps:RoadTestCompletionDate>")
                .append(rtcDate)
                .append("</udps:RoadTestCompletionDate>")
                .append("<udps:WheelCompletionDate>")
                .append(btwDate)
                .append("</udps:WheelCompletionDate>")
                .append("<udps:WrittenExamDate>")
                .append(wtDate)
                .append("</udps:WrittenExamDate><udps:SchoolID><j:ID>")
                .append(schoolNumber)
                .append("</j:ID></udps:SchoolID>")
                .append("</udps:StudentDriverCertificate>")
                .append("</udps:TransactionParameters>");
        return sbTransactionParam.toString();
    }
    
    public String getTransactionParameters() {
        return transactionParameters;
    }
    
    public void setTransactionParameters(String transactionParameters) {
        this.transactionParameters = transactionParameters;
    }
    
    
    public java.lang.String getNotValidReason() {
        return notValidReason;
    }
    
    public void setNotValidReason(java.lang.String notValidReason) {
        this.notValidReason = notValidReason;
    }
    
    public java.util.Map getDefaultMap(String descriptorClass){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userName",Constants.Webservice_User);
        map.put("password",Constants.Webservice_Pass);
        map.put("systemID",Constants.Webservice_SystemId);
        map.put("transactionID",Constants.Webservice_TransactionId);
        map.put("agency",Constants.Webservice_Agency);
        map.put("ori",Constants.Webservice_Ori);
        map.put("state",Constants.Webservice_State);
        map.put("descriptorType",Constants.Webservice_DescriptorType);
        map.put("descriptorClass",descriptorClass);
        return map;
    }
    
}
