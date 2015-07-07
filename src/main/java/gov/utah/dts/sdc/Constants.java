package gov.utah.dts.sdc;

public final class Constants {

    public static final String REGISTER = "register";
    public static final String REGISTER_MERGE = "merge";
    public static String LIST = "list";
    public static final String CANCEL = "cancel";
    public static final String CREATE = "create";
    public static final String REPORT = "report";
    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";
    public static final String MERGE = "merge";
    public static final String EDITSTUDENT = "edit";
    public static final String Forward_NoClassStudent = "noclass";
    public static final String Forward_MultipleClasses = "multipleclass";
    public static final String AGREE = "agree";
    public static final String DISAGREE = "disagree";

    public static final String USER_KEY = "authPerson";
    public static final String USER_EMAIL = "authPersonEmail";
    public static final String USER_DN = "sm_user";
    public static final String USER_FirstName = "authPersonFirstName";
    public static final String USER_LastName = "authPersonLastName";
    public static final String USER_MiddleName = "authPersonMiddleName";
    public static final String USER_MobilePhone = "authPersonMobilePhone";
    public static final String USER_WorkPhone = "authPersonWorkPhone";
    public static final String USER_HomePhone = "authPersonHomePhone";
    public static final String USER_HomeAddress = "authPersonHomeAddress";
    public static final String USER_HomeCity = "authPersonHomeCity";
    public static final String USER_HomeState = "authPersonHomeState";
    public static final String USER_HomeZip = "authPersonHomeZip";
    public static final String STATE_MAP = "stateMap";
    
    public static final String RoadSearch_SchoolFk = "roadSearchSchool";

    public static String Role_HighSchool = "HIGHSCHOOL";
    public static String Role_Commercial = "COMMERCIAL";
    public static String Role_ThirdParty = "THIRDPARTY";
    public static String Role_Admin = "ADMIN";
    public static String Role_Guest = "GUEST";
    public static String Guest_No_Role_Message = "Thank You for Registering, SDC Administrators Will Contact You Shortly To Give You Additional Access to SDC.";

    public static String HIGHSCHOOL = "highschool";
    public static String GUEST = "guest";
    public static String COMMERCIAL = "commercial";
    public static String THIRDPARTY = "thirdparty";
    public static String ADMIN = "admin";

    public static String Report_WrittenCompletionStudent = "writtenCompletionStudent";
    public static String Report_FullCompletionStudent = "fullCompletionStudent";
    public static String Report_CompletionTest = "completionTestsSchool";
    public static String Report_OcsCompletionStudent = "ocsCompletionStudent";

    /* PROD * /
    public static String Webservice_EndPoint = "https://ws-osb.ps.utah.gov/driverlicense-osb/dld-sdct-3.0?WSDL";
    public static String Webservice_User = "SDCTTEST";
    public static String Webservice_Pass = "4SDCTm31";
    public static String Webservice_SystemId = "SDCTPT";
    public static String Webservice_TransactionId = "SDCT";
    public static String Webservice_Agency = "CDLTPT";
    public static String Webservice_Ori = "UXSDCTPT0";
    public static String Webservice_Test_Generator = "https://ws-osb.ps.utah.gov/driverlicense-osb/dld-testgenerator-1.0?WSDL";
    public static String Webservice_Agency_testGen = "CDLTPT";
    public static String Webservice_User_testGen = "SDCTTEST";
    public static String Webservice_Ori_testGen = "UXSDCTPT0";
    public static String Webservice_Pass_testGen = "4SDCTm31";
   	public static String Webservice_alm_endpoint = "http://applog.dts.utah.gov/ApplicationLogging/services/log.wsdl";
   	public static String Webservice_alm_ipAddress = "168.177.223.187";
    public static String Webservice_alm_applicationUserName = "sdc_web";
    public static String Webservice_alm_secureToken = "sdc3st";
    public static String Email_To = "sdcadmin@utah.gov";
    /**/    
    
    /* DEV & AT */
    public static String Webservice_EndPoint = "https://ws-osb-test.ps.utah.gov/driverlicense-osb/dld-sdct-3.0?WSDL";
    public static String Webservice_User = "sdcttest";
    public static String Webservice_Pass = "sdct4321";
    public static String Webservice_SystemId = "CDLTPT"; //"SDCTPT";
    public static String Webservice_TransactionId = "CDLT"; //"SDCT";
    public static String Webservice_Agency = "TEST"; //"SDCTPT"
    public static String Webservice_Ori = "UXCDLTPT0"; //"UXSDCTPT0";
    public static String Webservice_Test_Generator = "https://ws-osb-test.ps.utah.gov/driverlicense-osb/dld-testgenerator-1.0?WSDL";
    public static String Webservice_Agency_testGen = "CDLTPT";
    public static String Webservice_User_testGen = "SDCTTEST";
    public static String Webservice_Ori_testGen = "UXSDCTPT0";
    public static String Webservice_Pass_testGen = "4SDCTm31";
    public static String Webservice_alm_endpoint = "http://applog.at.utah.gov/ApplicationLogging/services/log.wsdl";
    public static String Webservice_alm_ipAddress = "168.177.223.186";
    public static String Webservice_alm_applicationUserName = "sdc_web";
    public static String Webservice_alm_secureToken = "sdc3st";
    public static String Email_To = "hnguyen@utah.gov";
    /**/
    
    public static String Webservice_State = "UT";
    public static String Webservice_DescriptorType = "SDCT";
    public static String Webservice_QueryByDL = "QueryByDL";
    public static String Webservice_CompletionByDL = "CompletionByDL";
    public static String Webservice_SendWrittenTest = "WrittenTest";	// CompleteWrittenByName (new Web service 12/01/2012)
    public static String Webservice_CompleteRoadByName = "CompletRoadByName";
    public static String Webservice_CompletionByName = "CompletionByName";
    public static String Webservice_Error_EligibilityDate = "An eligibility date cannot be created until the learner permit is issued.";

    public static String Email_From = "sdcadmin@utah.gov";
    public static String Email_SMTP = "send.state.ut.us";
    //lowercase true or false to switch this on and off
    public static String Email_Activated = "true";
    public static String Email_Subject = "SDC Registration Request";

    public static String SchoolType_HighSchool = "0";
    public static String SchoolType_Commercial = "1";
    public static String SchoolType_HighSchool_List = "highSchoolList";
    public static String SchoolType_Commercial_List = "commercialSchoolList";

    public static String Commercial_Ajax_Message = "commercialAjaxMessages";

    public static String CompletionType_BehindTheWheel = "BEHIND THE WHEEL";
    public static String CompletionType_Observation = "OBSERVATION";
    public static String CompletionType_ClassroomTraining = "CLASSROOM TRAINING";
    
    public static String ThirdParty_Ajax_Message = "thirdPartyAjaxMessages";
}
