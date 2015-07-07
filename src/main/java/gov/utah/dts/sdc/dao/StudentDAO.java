package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.Student;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class StudentDAO extends BaseDAO {

    protected static Log log = LogFactory.getLog(StudentDAO.class);
    private static final String selectStatement = "studentSelect";
    private static final String commercialStudentStatement = "studentCommercialSelect";
    private static final String equalsCountStatement = "studentEqualsCount";
    private static final String searchEqualsStatement = "studentSelect";
    private static final String insertStatement = "studentInsert";
    private static final String updateStatement = "studentUpdate";
    private static final String updateStudentInformation = "updateStudentInformation";
    private static final String fullUpdateStatement = "studentUpdateAbsolute";
    private static final String deleteStatement = "studentDelete";
    private static final String insertObservationStatement = "insertObservation";
    private static final String updateObservationStatement = "updateObservation";
    private static final String deleteObservationStatement = "deleteObservation";
    private static final String insertBtwStatement = "insertBehindTheWheel";
    private static final String updateBtwStatement = "updateBehindTheWheel";
    private static final String deleteBtwStatement = "deleteBehindTheWheel";
    private static final String insertTrainingStatement = "insertTraining";
    private static final String updateTrainingStatement = "updateTraining";
    private static final String deleteTrainingStatement = "deleteTraining";
    private static final String commercialTrainingStatement = "getCommercialTraining";
    private static final String commercialBtwStatement = "getCommercialBehindTheWheel";
    private static final String commercialObservationStatement = "getCommercialObservation";
    private static final String commercialRoadStatement = "getCommercialRoad";
    private static final String thirdPartyTrainingStatement = "getThirdPartyTraining";
    private static final String thirdPartyBtwStatement = "getThirdPartyBehindTheWheel";
    private static final String thirdPartyObservationStatement = "getThirdPartyObservation";
    
    public StudentDAO() {
    	
    }
    
    public StudentDAO(String userId) {
    	super.userId = userId;
    }
    
    public java.lang.Integer getEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(equalsCountStatement, parameterObject);
    }

    public List searchEquals(Object parameterObject) throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }

    public List getStudentList(Object parameterObject) throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }

    public List getCommercialStudentList(Object parameterObject) throws DaoException {
        return getBaseList(commercialStudentStatement, parameterObject);
    }

    public Student getStudent(Object tryId) throws DaoException {
        List list = getBaseList(selectStatement, tryId);
        if (list.isEmpty()) {
            return new Student();
        } else {
            return (Student) list.get(0);
        }
    }

    public int insert(Object parameterObject) throws DaoException {
        log.debug("insert");
        return baseUpdate(insertStatement, parameterObject);
    }

    public int studentUpdateFileNumber(Object parameterObject) throws DaoException {
        return baseUpdate("studentUpdateFileNumber", parameterObject);
    }

    public int update(Object parameterObject) throws DaoException {
    	// don't update student_number if it's not null - duplicate student numbers issues
    	if (parameterObject instanceof Student) {
    		if (getBaseObject("checkNullStudentNumber", (Student)parameterObject) != null) {
    			((Student)parameterObject).setStudentNumber(null);
    		}
    	}
    	
        return baseUpdate(updateStatement, parameterObject);
    }

    public int fullUpdate(Object parameterObject) throws DaoException {
    	// don't update student_number if it's not null - duplicate student numbers issues
    	if (parameterObject instanceof Student) {
    		if (getBaseObject("checkNullStudentNumber", (Student)parameterObject) != null) {
    			((Student)parameterObject).setStudentNumber(null);
    		}
    	}
    	
        return baseUpdate(fullUpdateStatement, parameterObject);
    }

    public int delete(Object parameterObject) throws DaoException {
        return baseUpdate(deleteStatement, parameterObject);
    }

    public java.lang.Integer getRoadTestEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject("getRoadTestEqualsCount", parameterObject);
    }

    public int insertObservation(Object parameterObject) throws DaoException {
        return baseUpdate(insertObservationStatement, parameterObject);
    }
    
    public int updateObservation(Object parameterObject) throws DaoException {
        return baseUpdate(updateObservationStatement, parameterObject);
    }
    
    public int deleteObservation(Object parameterObject) throws DaoException {
        return baseUpdate(deleteObservationStatement, parameterObject);
    }
    
    public int insertTraining(Object parameterObject) throws DaoException {
        return baseUpdate(insertTrainingStatement, parameterObject);
    }
    
    public int updateTraining(Object parameterObject) throws DaoException {
        return baseUpdate(updateTrainingStatement, parameterObject);
    }
    
    public int deleteTraining(Object parameterObject) throws DaoException {
        return baseUpdate(deleteTrainingStatement, parameterObject);
    }
    
    public int insertBehindTheWheel(Object parameterObject) throws DaoException {
        return baseUpdate(insertBtwStatement, parameterObject);
    }
    
    public int updateBehindTheWheel(Object parameterObject) throws DaoException {
         return baseUpdate (updateBtwStatement, parameterObject);
    }
    
    public int deleteBehindTheWheel(Object parameterObject) throws DaoException {
        return baseUpdate(deleteBtwStatement, parameterObject);
    }
    
    public int updateStudentInformation(Object parameterObject) throws DaoException {
         return baseUpdate (updateStudentInformation, parameterObject);
    }
    
    public List getCommercialTraining(Object parameterObject)
    throws DaoException {
        return getBaseList(commercialTrainingStatement, parameterObject);
    }
    
    public List getCommercialBehindTheWheel(Object parameterObject)
    throws DaoException {
         return getBaseList(commercialBtwStatement, parameterObject);
    }
      
	public List getCommercialObservation(Object parameterObject)
    throws DaoException {
         return getBaseList(commercialObservationStatement, parameterObject);
    }
    
    public List getCommercialRoad(Object parameterObject)
    throws DaoException {
        return getBaseList(commercialRoadStatement, parameterObject);
    }
    
    public List getThirdPartyTraining(Object parameterObject)
    throws DaoException {
        return getBaseList(thirdPartyTrainingStatement, parameterObject);
    }
    
    public List getThirdPartyBehindTheWheel(Object parameterObject)
    throws DaoException {
         return getBaseList(thirdPartyBtwStatement, parameterObject);
    }
    
    public List getThirdPartyObservation(Object parameterObject)
    throws DaoException {
         return getBaseList(thirdPartyObservationStatement, parameterObject);
    }
    
    public int updateStudentNote(Object parameterObject) throws DaoException {
        return baseUpdate ("updateStudentNote", parameterObject);
   }

    public int updateStudentOcsScore(Object parameterObject) throws DaoException {
        return baseUpdate ("updateStudentOcsScore", parameterObject);
   }
    
    public int updateStudentRoadTestCompletionDate(Object parameterObject) throws DaoException {
    	return baseUpdate ("updateStudentRoadTestCompletionDate", parameterObject);
    }
    
    public Integer getStudentAuditCount(Object parameterObject) throws DaoException {
    	return (Integer)getBaseObject("getStudentAuditCount", parameterObject);
    }    
    
    public int updateStudentAudit(Object parameterObject) throws DaoException {
    	return baseUpdate ("updateStudentAudit", parameterObject);
    }
    
    public int insertStudentAudit(Object parameterObject) throws DaoException {
    	return baseUpdate ("insertStudentAudit", parameterObject);
    }    
}
