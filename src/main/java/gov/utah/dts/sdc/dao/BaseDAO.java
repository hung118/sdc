package gov.utah.dts.sdc.dao;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import gov.utah.dts.alm.webservice.client.LoggingClient;
import gov.utah.dts.alm.webservice.client.LoggingClientImpl;
import gov.utah.dts.alm.webservice.model.WriteLogResponse;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.model.*;
import gov.utah.dts.util.SendMail;

import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseDAO {
    private static Log log = LogFactory.getLog(BaseDAO.class);
    private static SqlMapClient sqlMap = null;
	private DataSource dataSource;
	
	protected String userId;
    
    static
    {
        try {
            String resource = "sql/SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            log.info("reader = "+reader );
            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
            log.info("sqlMap = "+sqlMap );
            reader.close();
        } catch (Exception ex) {
            log.error("BaseDAO static block: ", ex);
            throw new RuntimeException("Error Initializing BaseDAO :" + ex);
        }
    }
    
	public List getBaseList(String statementName, Object parameterObject)
    throws DaoException {
        List list = null;
        try {
            try {
                sqlMap.startTransaction();
                list = sqlMap.queryForList(statementName, parameterObject);
                sqlMap.commitTransaction();
            } finally {
                sqlMap.endTransaction();
            }
        } catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(ex.fillInStackTrace());
        }
        return list;
    }
    
    public Object getBaseObject(String statementName, Object parameterObject)
    throws DaoException {
        Object result = null;
        try {
            try {
                sqlMap.startTransaction();
                result = sqlMap.queryForObject(statementName, parameterObject);
                sqlMap.commitTransaction();
            } finally {
                sqlMap.endTransaction();
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e.fillInStackTrace());
        }
        
        return result;
    }
    
    public int baseUpdate(String statementName, Object parameterObject)
    throws DaoException {
        log.debug("baseUpdate");
        int result = 0;
        try {
            try {
                sqlMap.startTransaction();
                result = sqlMap.update(statementName, parameterObject);
                sqlMap.commitTransaction();
            } finally {
                sqlMap.endTransaction();
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e.fillInStackTrace());
        }
            
        // Redmine 4318, 30821 (enhancements) - ALM logging.
        try {
            if (userId != null) {
            	HashMap hm = null;
            	if (parameterObject instanceof HashMap) {
            		hm = (HashMap)parameterObject;
            	} else {
            		hm = getFormData(parameterObject);
            	}
            	if (isAlmLog((String)hm.get("almLog"), statementName)) {
            		almLog(statementName, "RE", hm);
            	}
            }  
        } catch (Exception e) {	// ignore the error, yet send a notification email.        	
        	log.error("** Error in ALM log Web service!" + e.getMessage());
        	try {
        		sendALMErrorEmail(e.getMessage());
        	} catch (Exception e2) {
        		log.error("Error of sending confirmation email for ALM Error.");
        	}        	
        }

        return result;
    }

    public int baseDelete(String statementName, Object parameterObject)
    throws DaoException{
        int result = 0;
        try {
            try {
                sqlMap.startTransaction();
                result = sqlMap.delete(statementName, parameterObject);
                sqlMap.commitTransaction();
            } finally {
                sqlMap.endTransaction();
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e.fillInStackTrace());
        }
            
        // Redmine 4318, 30821 (enhancements) - ALM logging.
        try {
            if (userId != null) {
            	HashMap hm = null;
            	if (parameterObject instanceof HashMap) {
            		hm = (HashMap)parameterObject;
            	} else {
            		hm = getFormData(parameterObject);
            	}
            	if (isAlmLog((String)hm.get("almLog"), statementName)) {
            		almLog(statementName, "RD", hm);
            	}          	
            }        	
        } catch (Exception e) {	// ignore the error, yet send a notification email.        	
        	log.error("** Error in ALM log Web service!" + e.getMessage());
        	try {
        		sendALMErrorEmail(e.getMessage());
        	} catch (Exception e2) {
        		log.error("Error of sending confirmation email for ALM Error.");
        	}        	
        }
        
        return result;
    }
    
    public void setDataSource(DataSource dataSource){
    		this.dataSource = dataSource;
    }
    
    public DataSource getDataSource() {
    	return this.dataSource;
    }
    
    private void almLog(String logEntry, String logTypeCode, HashMap formMap) throws Exception {
    	StringBuffer userComment = new StringBuffer();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	
    	Set<Entry<String, Object>> entries = formMap.entrySet();
    	Iterator<Entry<String, Object>> it = entries.iterator();
    	while (it.hasNext()) {
    		Entry<String, Object> e = it.next();
    		String key = e.getKey();
    		Object val = e.getValue();
    		userComment.append(key + " = ");
    		if (val == null) {
    			userComment.append("null | ");
    		} else if (val instanceof Integer[]) {
    			Integer[] vals = (Integer[]) val;
    			for (int i = 0; i < vals.length; i++) {
    				userComment.append(vals[i].intValue());
    				if (i < vals.length - 1) {
    					userComment.append(", ");
    				}
    			}
    			userComment.append(" | ");
    		} else if (val instanceof Date) {
    			userComment.append(sdf.format((Date)val) + " | ");
    		} else {
        		userComment.append(val.toString() + " | ");    			
    		}
    	}
    	
    	LoggingClient logClient = new LoggingClientImpl();
    	WriteLogResponse writeLogResponse = logClient.writeLog(userId, logTypeCode, logEntry, userComment.toString());
    	log.info("Write log response: " + writeLogResponse.getResponseMessage().getResponseDescription());;
    }
    
    /**
     * Redmine 4318
     * 
     * @param logEntry
     * @param formMap
     * @throws Exception
     */
	@SuppressWarnings("unused")
	private void almLog_old(String logEntry, HashMap formMap) throws Exception {
    	
    	StringBuffer userComment = new StringBuffer();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	
    	Collection<String> col = formMap.values();
    	Iterator it = col.iterator();
    	while (it.hasNext()) {
    		Object val = it.next();
    		if (val == null) {
    			userComment.append("null | ");
    		} else if (val instanceof Integer[]) {
    			Integer[] vals = (Integer[]) val;
    			for (int i = 0; i < vals.length; i++) {
    				userComment.append(vals[i].intValue() + ", ");
    			}
    			userComment.append(" | ");
    		} else if (val instanceof Date) {
    			userComment.append(sdf.format((Date)val) + " | ");
    		} else {
        		userComment.append(val.toString() + " | ");    			
    		}
    	}
    	
    	LoggingClient logClient = new LoggingClientImpl();
    	String logTypeCode = "";	// dummy, it doesn't mean anything in this app. 
    	if ("classroomStudentRemove".equals(logEntry)) {	// See searchAuditLogs.jsp for use.
    		logTypeCode = "RD";
    	} else {
    		logTypeCode = "RE";
    	}
    	WriteLogResponse writeLogResponse = logClient.writeLog(userId, logTypeCode, logEntry, userComment.toString());
    	
    	log.debug("Write log response: " + writeLogResponse.getResponseMessage().getResponseDescription());;
    }
    
    private LinkedHashMap getFormData(Object obj) throws Exception {
    	
    	LinkedHashMap hm = new LinkedHashMap();
    	
    	if (obj instanceof Audit) {
    		Audit bean = (Audit)obj;
    		hm.put("completionDate", bean.getCompletionDate());
    		hm.put("complitionSchoolNumber", bean.getCompletionSchoolNumber());
    		hm.put("roadCompletionSchoolNumber", bean.getRoadCompletionSchoolNumber());
    		hm.put("roadTestCompletionDate", bean.getRoadTestCompletionDate());
    		hm.put("roadTestInstructorFk", bean.getRoadTestInstructorFk());
    		hm.put("roadTestScore", bean.getRoadTestScore());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("schoolNumber", bean.getSchoolNumber());
    		hm.put("studentFk", bean.getStudentFk());
    		hm.put("studentNumber", bean.getStudentNumber());
    		hm.put("writtenCompletionSchoolNumber", bean.getWrittenCompletionSchoolNumber());
    		hm.put("writtenTestCompletionDate", bean.getWrittenTestCompletionDate());
    		hm.put("writtenTestScore", bean.getWrittenTestScore());
    	} else if (obj instanceof Bond) {
    		Bond bean = (Bond)obj;
    		hm.put("agent", bean.getAgent());
    		hm.put("amount", bean.getAmount());
    		hm.put("bondNumber", bean.getBondNumber());
    		hm.put("bondsPk", bean.getBondsPk());
    		hm.put("company", bean.getCompany());
    		hm.put("deleted", bean.getDeleted());
    		hm.put("expireDate", bean.getExpireDate());
    		hm.put("issueDate", bean.getIssueDate());
    		hm.put("phone", bean.getPhone());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("schoolName", bean.getSchoolName());
    	} else if (obj instanceof Branch) {
    		Branch bean = (Branch)obj;
    		hm.put("address1", bean.getAddress1());
    		hm.put("address2", bean.getAddress2());
    		hm.put("branchPk", bean.getBranchPk());
    		hm.put("businessPhone", bean.getBusinessPhone2());
    		hm.put("city", bean.getCity());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("state", bean.getState());
    		hm.put("timestamp", bean.getTimestamp());
    		hm.put("updatedBy", bean.getUpdatedBy());
    		hm.put("zip", bean.getZip());
    	} else if (obj instanceof Classroom) {
    		Classroom bean = (Classroom)obj;
    		hm.put("alias", bean.getAlias());
    		hm.put("classroomClosed", bean.getClassroomClosed());
    		hm.put("classroomNumber", bean.getClassroomNumber());
    		hm.put("classroomPk", bean.getClassroomPk());
    		hm.put("homeStudy", bean.getHomeStudy());
    		hm.put("instructorFirstName", bean.getInstructorFirstName());
    		hm.put("instructorFk", bean.getInstructorFk());
    		hm.put("instructorFullName", bean.getInstructorFullName());
    		hm.put("instructorLastName", bean.getInstructorLastName());
    		hm.put("instructorMiddleName", bean.getInstructorMiddleName());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("studentPk", bean.getStudentPk());
    		hm.put("timestamp", bean.getTimestamp());
    	} else if (obj instanceof CommercialStudent) {
    		CommercialStudent bean = (CommercialStudent)obj;
    		hm.put("address1", bean.getAddress1());
    		hm.put("address2", bean.getAddress2());
    		hm.put("behindWheelCompletionCheck", bean.getBehindWheelCompletionCheck2());
    		hm.put("behindWheelCompletionDate", bean.getBehindWheelCompletionDate());
    		hm.put("city", bean.getCity());
    		hm.put("classroomCompletionCheck", bean.getClassroomCompletionCheck2());
    		hm.put("classroomCompletionDate", bean.getClassroomCompletionDate());
    		hm.put("dob", bean.getDob());
    		hm.put("eligibility", bean.getEligibilityDate());
    		hm.put("email", bean.getEmail());
    		hm.put("fileNumber", bean.getFileNumber());
    		hm.put("firstName", bean.getFirstName());
    		hm.put("homePhone", bean.getHomePhone());
    		hm.put("lastName", bean.getLastName());
    		hm.put("licenseType", bean.getLicenseType());
    		hm.put("middleName", bean.getMiddleName());
    		hm.put("observationCompletionCheck", bean.getObservationCompletionCheck2());
    		hm.put("observationCompletionDate", bean.getObservationCompletionDate());
    		hm.put("roadTestCompletionDate", bean.getRoadTestCompletionDate());
    		hm.put("roadTestInstructorFk", bean.getRoadTestInstructorFk());
    		hm.put("roadTestScore", bean.getRoadTestScore());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("schoolNumber", bean.getSchoolNumber());
    		hm.put("schools", bean.getSchools());
    		hm.put("state", bean.getState());
    		hm.put("studentFullName", bean.getStudentFullName());
    		hm.put("studentNumber", bean.getStudentNum());
    		hm.put("studentPk", bean.getStudentPk());
    		hm.put("suffix", bean.getSuffix());
    		hm.put("timestamp", bean.getTimestamp());
    		hm.put("updatedBy", bean.getUpdatedBy());
    		hm.put("writtenTestCompletionDate", bean.getWrittenTestCompletionDate());
    		hm.put("writtenTestScore", bean.getWrittenTestScore());
    		hm.put("zip", bean.getZip());
    	} else if (obj instanceof CommercialTimes) {
    		CommercialTimes bean = (CommercialTimes)obj;
    		hm.put("behindTheWheelCreatedate", bean.getBehindTheWheelCreateDate());
    		hm.put("behineTheWheelEndTime", bean.getBehindTheWheelEndTime());
    		hm.put("behindTheWheelStartTime", bean.getBehindTheWheelStartTime());
    		hm.put("branchFk", bean.getBranchFk());
    		hm.put("branchName", bean.getBranchName());
    		hm.put("classroomFk", bean.getClassroomFk());
    		hm.put("completionDate", bean.getCompletionDate());
    		hm.put("editable", bean.getEditable());
    		hm.put("endTime", bean.getEndTime());
    		hm.put("instructorFk", bean.getInstructorFk());
    		hm.put("instructorFullName", bean.getInstructorFullName());
    		hm.put("observationCreateDate", bean.getObservationCreateDate());
    		hm.put("observationDate", bean.getObservationDate());
    		hm.put("observationEndTime", bean.getObservationEndTime());
    		hm.put("observationStartTime", bean.getObservationStartTime());
    		hm.put("roadInstructorFk", bean.getRoadInstructorFk());
    		hm.put("roadScore", bean.getRoadScore());
    		hm.put("routeArea", bean.getRouteArea());
    		hm.put("routeNumber", bean.getRouteNumber());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("section", bean.getSection());
    		hm.put("startTime", bean.getStartTime());
    		hm.put("studentFk", bean.getStudentFk());
    		hm.put("timePk", bean.getTimePk());
    		hm.put("trainingCreateDate", bean.getTrainingCreateDate());
    		hm.put("trainingEndTime", bean.getTrainingEndTime());
    		hm.put("trainingStartTime", bean.getTrainingStartTime());
    		hm.put("vehicleFk", bean.getVehicleFk());
    		hm.put("vehicleFullDesc", bean.getVehicleFullDesc());
    	} else if (obj instanceof News) {
    		News bean = (News)obj;
    		hm.put("deleted", bean.getDeleted());
    		hm.put("description", bean.getDescription());
    		hm.put("news", bean.getNews());
    		hm.put("newsPk", bean.getNewsPk());
    		hm.put("roleName", bean.getRoleName());
    		hm.put("rolesList", bean.getRolesList());
    		hm.put("roleTypesFk", bean.getRoleTypesFk());
    		hm.put("timestamp", bean.getTimestamp());
    	} else if (obj instanceof Person) {
    		Person bean = (Person) obj;
    		hm.put("address1", bean.getAddress1());
    		hm.put("address2", bean.getAddress2());
    		hm.put("associatedSchool", bean.getAssociatedSchools());
    		hm.put("backgroundCheckDate", bean.getBackgroundCheckDate());
    		hm.put("businessPhone", bean.getBusinessPhone());
    		hm.put("city", bean.getCity());
    		hm.put("deleted", bean.getDeleted());
    		hm.put("dHistoryDate", bean.getDlHistoryDate());
    		hm.put("dn", bean.getDn());
    		hm.put("dob", bean.getDob());
    		hm.put("driversLicenseNumber", bean.getDriversLicenseNumber());
    		hm.put("driversLicenseState", bean.getDriversLicenseState());
    		hm.put("email", bean.getEmail());
    		hm.put("firstName", bean.getFirstName());
    		hm.put("fullName", bean.getFullName());
    		hm.put("homePhone", bean.getHomePhone());
    		hm.put("instructorNum", bean.getInstructorNum());
    		hm.put("instructorTrainingDate", bean.getInstructorTrainingDate());
    		hm.put("knowledgeTest2", bean.getKnowledgeTest2());
    		hm.put("languages", bean.getLanguages());
    		hm.put("lastName", bean.getLastName());
    		hm.put("licenseExpireDate", bean.getLicenseExpireDate());
    		hm.put("middleName", bean.getMiddleName());
    		hm.put("pdpCheckDate", bean.getPdpCheckDate());
    		hm.put("personPk", bean.getPersonPk());
    		hm.put("roles", bean.getRoles());
    		hm.put("skillsTest2", bean.getSkillsTest2());
    		hm.put("state", bean.getState());
    		hm.put("suffix", bean.getSuffix());
    		hm.put("testType", bean.getTestTypes());
    		hm.put("timeStamp", bean.getTimestamp());
    		hm.put("updatedBy", bean.getUpdatedBy());
    		hm.put("zip", bean.getZip());
    		hm.put("knowledgeTest", bean.isKnowledgeTest());
    		hm.put("skillsTest", bean.isSkillsTest());
    	} else if (obj instanceof PersonRoles) {
    		PersonRoles bean = (PersonRoles)obj;
    		hm.put("email", bean.getEmail());
    		hm.put("personFk", bean.getPersonFk());
    		hm.put("personRolesPk", bean.getPersonRolesPk());
    		hm.put("roleTypesFk", bean.getRoleTypesFk());
    	} else if (obj instanceof PersonSchool) {
    		PersonSchool bean = (PersonSchool)obj;
    		hm.put("personFk", bean.getPersonFk());
    		hm.put("personSchoolPk", bean.getPersonSchoolPk());
    		hm.put("schoolFk", bean.getSchoolFk());
    	} else if (obj instanceof RoleTypes) {
    		RoleTypes bean = (RoleTypes)obj;
    		hm.put("description", bean.getDescription());
    		hm.put("roleTypesPk", bean.getRoleTypesPk());
    	} else if (obj instanceof School) {
    		School bean = (School)obj;
    		hm.put("address1", bean.getAddress1());
    		hm.put("address2", bean.getAddress2());
    		hm.put("businessPhone", bean.getBusinessPhone());
    		hm.put("busniessPhone2", bean.getBusinessPhone2());
    		hm.put("city", bean.getCity());
    		hm.put("deleted", bean.getDeleted());
    		hm.put("expireDate", bean.getExpireDate());
    		hm.put("expireDateStr", bean.getExpireDateStr());
    		hm.put("homeStudy", bean.getHomeStudy());
    		hm.put("lastInspectionDate", bean.getLastInspectionDate());
    		hm.put("reportEndDate", bean.getReportEndDate());
    		hm.put("reportStartDate", bean.getReportStartDate());
    		hm.put("roadTestCount", bean.getRoadTestCount());
    		hm.put("schoolClosureDate", bean.getSchoolClosureDate());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("schoolNumber", bean.getSchoolNumber());
    		hm.put("schoolPk", bean.getSchoolPk());
    		hm.put("schoolType", bean.getSchoolType());
    		hm.put("state", bean.getState());
    		hm.put("timestamp", bean.getTimestamp());
    		hm.put("updatedBy", bean.getUpdatedBy());
    		hm.put("writtenTestCount", bean.getWrittenTestCount());
    		hm.put("zip", bean.getZip());
    	} else if (obj instanceof Student) {
    		Student bean = (Student)obj;
    		hm.put("address1", bean.getAddress1());
    		hm.put("address2", bean.getAddress2());
    		hm.put("behindWheelCompletionCheck", bean.getBehindWheelCompletionCheck2());
    		hm.put("behindWheelCompletionDate", bean.getBehindWheelCompletionDate());
    		hm.put("btwCompletionSchoolNumber", bean.getBtwCompletionSchoolNumber());
    		hm.put("city", bean.getCity());
    		hm.put("classroomCompletionCheck", bean.getClassroomCompletionCheck2());
    		hm.put("classroomCompletionDate", bean.getClassroomCompletionDate());
    		hm.put("classroomCompletionSchoolNumber", bean.getClassroomCompletionSchoolNumber());
    		hm.put("classroomFk", bean.getClassroomFk());
    		hm.put("dob", bean.getDob());
    		hm.put("eligibilityDate", bean.getEligibilityDate());
    		hm.put("email", bean.getEmail());
    		hm.put("fileNumber", bean.getFileNumber());
    		hm.put("firstName", bean.getFirstName());
    		hm.put("hid", bean.getHide());
    		hm.put("homePhone", bean.getHomePhone());
    		hm.put("lastName", bean.getLastName());
    		hm.put("licenseType", bean.getLicenseType());
    		hm.put("middleName", bean.getMiddleName());
    		hm.put("note", bean.getNote());
    		hm.put("observationCompletionCheck", bean.getObservationCompletionCheck2());
    		hm.put("observationCompletionDate", bean.getObservationCompletionDate());
    		hm.put("observationCompletionSchoolNumber", bean.getObservationCompletionSchoolNumber());
    		hm.put("ocsScore", bean.getOcsScore());
    		hm.put("roadCompletionSchoolNumber", bean.getRoadCompletionSchoolNumber());
    		hm.put("roadTestCompletionDate", bean.getRoadTestCompletionDate());
    		hm.put("roadTestInstructorFk", bean.getRoadTestInstructorFk());
    		hm.put("roadTestScore", bean.getRoadTestScore());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("schoolNumber", bean.getSchoolNumber());
    		hm.put("schools", bean.getSchools());
    		hm.put("state", bean.getState());
    		hm.put("studentFullName", bean.getStudentFullName());
    		hm.put("studentNumber", bean.getStudentNumber());
    		hm.put("studentPk", bean.getStudentNumber());
    		hm.put("suffix", bean.getSuffix());
    		hm.put("timestamp", bean.getTimestamp());
    		hm.put("updatedBy", bean.getUpdatedBy());
    		hm.put("writtenCompletionSchoolNumber", bean.getWrittenCompletionSchoolNumber());
    		hm.put("writtenTestCompletionDate", bean.getWrittenTestCompletionDate());
    		hm.put("writtenTestScore", bean.getWrittenTestScore());
    		hm.put("zip", bean.getZip());
    		hm.put("almLog", bean.getAlmLog());
    	} else if (obj instanceof ThirdPartyTimes) {
    		ThirdPartyTimes bean = (ThirdPartyTimes)obj;
    		hm.put("branchName", bean.getBranchName());
    		hm.put("classroomFk", bean.getClassroomFk());
    		hm.put("completionDate", bean.getCompletionDate());
    		hm.put("endTime", bean.getEndTime());
    		hm.put("instructorFullName", bean.getInstructorFullName());
    		hm.put("roadInstructorFk", bean.getRoadInstructorFk());
    		hm.put("roadScore", bean.getRoadScore());
    		hm.put("routeArea", bean.getRouteArea());
    		hm.put("routeNumber", bean.getRouteNumber());
    		hm.put("section", bean.getSection());
    		hm.put("startTime", bean.getStartTime());
    		hm.put("studentFk", bean.getStudentFk());
    		hm.put("timePk", bean.getTimePk());
    		hm.put("vehicleFullDesc", bean.getVehicleFullDesc());
    	} else if (obj instanceof Vehicle) {
    		Vehicle bean = (Vehicle)obj;
    		hm.put("deleted", bean.getDeleted());
    		hm.put("insuranceAgency", bean.getInsuranceAgent());
    		hm.put("insuranceCompany", bean.getInsuranceCompany());
    		hm.put("insuranceExpire", bean.getInsuranceExpire());
    		hm.put("insurancePhone", bean.getInsurancePhone());
    		hm.put("insurancePolicy", bean.getInsurancePolicy());
    		hm.put("schoolFk", bean.getSchoolFk());
    		hm.put("schoolName", bean.getSchoolName());
    		hm.put("vehicleMake", bean.getVehicleMake());
    		hm.put("vehiclePk", bean.getVehiclePk());
    		hm.put("vehiclePlate", bean.getVehiclePlate());
    		hm.put("vehicleState", bean.getVehicleState());
    		hm.put("vehicleVin", bean.getVehicleVin());
    		hm.put("vehicleYear", bean.getVehicleYear());
    	}
    	
    	return hm;
    }
    
	private void sendALMErrorEmail(String errorMsg) throws Exception {
		String alm_endpoint = Constants.Webservice_alm_endpoint;
		StringBuffer msg = new StringBuffer();
		msg.append("There is an error when SDC tries to connect to the Audit Log Management (ALM) Webservice server. The error message is: ")
			.append(System.getProperty("line.separator"))
			.append(errorMsg)
			.append(System.getProperty("line.separator"))
			.append(System.getProperty("line.separator"))
			.append("Please submit a problem ticket by calling DTS helpdesk at 801-538-3440.")
			.append("This ticket should be assigned to the Capitol Production Hosting for bouncing the ALM server at URL: ")
			.append(System.getProperty("line.separator"))
			.append(alm_endpoint);

		SendMail s = new SendMail();
		s.setMailSubject("Audit Log Management Webservice error");
		s.setMailMessage(msg.toString());
		s.send();
	}
	
	private boolean isAlmLog(String almFlag, String action) {
		boolean ret = true;
    	if ("No".equals(almFlag)) {
    		ret = false;
    		return ret;
    	}
    	
    	switch (action) {
    	case "statementName":
    	case "newsUpdate":
    	case "newsDelete":
    	case "newsInsert":
    	case "personRolesDelete":
    	case "personRolesInsert":
    	case "personSchoolDelete":
    	case "personSchoolInsert":
    	case "personUpdate":
    	case "classroomUpdate":
    	case "schoolDelete":
    	case "schoolUpdate":
    	case "schoolInsert":
    	case "vehicleDelete":
    	case "vehicleUpdate":
    	case "vehicleInsert":
    	case "bondDelete":
    	case "bondUpdate":
    	case "bondInsert":
    	case "classroomDelete":
    		
    		ret = false;
    	}
    	
    	return ret;	    // rm 36145
    	//return false;   // rm 30821 student audit was implemented, no need for alm log.
	}
	    
}
