package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.StudentDAO;
import gov.utah.dts.sdc.model.Student;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class StudentService {
    protected static Log log = LogFactory.getLog(StudentService.class);
    private static StudentDAO dao;
    
    public StudentService() {
        dao = new StudentDAO();
    }
    
    public StudentService(String userId) {
    	dao = new StudentDAO(userId);
    }
    
    public Integer getEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }
    
	public List searchEquals(Object parameterObject)
    throws DaoException {
        return dao.searchEquals(parameterObject);
    }
    
    public List getStudentList(Object parameterObject)
    throws DaoException {
        return dao.getStudentList(parameterObject);
    }
    
    public List getCommercialStudentList(Object parameterObject)
    throws DaoException {
        return dao.getCommercialStudentList(parameterObject);
    }
    
    public Student getStudent(Object tryId) throws DaoException {
        return dao.getStudent(tryId);
    }
    public int insert(Object parameterObject)
    throws DaoException {
        return dao.insert(parameterObject);
    }
    public int update(Object parameterObject)
    throws DaoException {
        return dao.update(parameterObject);
    }
    public int fullUpdate(Object parameterObject)
    throws DaoException {
        return dao.fullUpdate(parameterObject);
    }

    public int studentUpdateFileNumber(Object parameterObject)
    throws DaoException {
        return dao.studentUpdateFileNumber(parameterObject);
    }

    public int delete(Object parameterObject)
    throws DaoException {
        return dao.delete(parameterObject);
    }
    
    
    public Integer getRoadTestEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getRoadTestEqualsCount(parameterObject);
    }
    
    public int insertObservation(Object parameterObject)
    throws DaoException {
        return dao.insertObservation(parameterObject);
    }
    
    public int insertBehindTheWheel(Object parameterObject)
    throws DaoException {
        return dao.insertBehindTheWheel(parameterObject);
    }
    
    public int insertTraining(Object parameterObject)
    throws DaoException {
        return dao.insertTraining(parameterObject);
    }
    
    public List getCommercialTraining(Object parameterObject)
    throws DaoException {
        return dao.getCommercialTraining(parameterObject);
    }
    
    public List getCommercialBehindTheWheel(Object parameterObject)
    throws DaoException {
        return dao.getCommercialBehindTheWheel(parameterObject);
    }
    
    public List getCommercialObservation(Object parameterObject)
    throws DaoException {
        return dao.getCommercialObservation(parameterObject);
    }
    public List getCommercialRoad(Object parameterObject)
    throws DaoException {
        return dao.getCommercialRoad(parameterObject);
    }
    
    public int updateObservation(Object parameterObject)
    throws DaoException {
        return dao.updateObservation(parameterObject);
    }
    
    public int updateTraining(Object parameterObject)
    throws DaoException {
        return dao.updateTraining(parameterObject);
    }
    
    public int updateBehindTheWheel(Object parameterObject)
    throws DaoException {
        return dao.updateBehindTheWheel(parameterObject);
    }
    
    public int updateStudentInformation(Object parameterObject)
    throws DaoException {
        return dao.updateStudentInformation(parameterObject);
    }
    
    public int deleteObservation(Object parameterObject)
    throws DaoException {
        return dao.deleteObservation(parameterObject);
    }
    
    public int deleteTraining(Object parameterObject)
    throws DaoException {
        return dao.deleteTraining(parameterObject);
    }
    
    public int deleteBehindTheWheel(Object parameterObject)
    throws DaoException {
        return dao.deleteBehindTheWheel(parameterObject);
    }
       
    public List getThirdPartyTraining(Object parameterObject)
    throws DaoException {
        return dao.getThirdPartyTraining(parameterObject);
    }
    
    public List getThirdPartyBehindTheWheel(Object parameterObject)
    throws DaoException {
        return dao.getThirdPartyBehindTheWheel(parameterObject);
    }
    
    public List getThirdPartyObservation(Object parameterObject)
    throws DaoException {
        return dao.getThirdPartyObservation(parameterObject);
    }
 
    public int updateStudentNote(Object parameterObject)
    throws DaoException {
        return dao.updateStudentNote(parameterObject);
    }

    public int updateStudentOcsScore(Object parameterObject)
    throws DaoException {
        return dao.updateStudentOcsScore(parameterObject);
    }

    public int updateStudentRoadTestCompletionDate(Object parameterObject)
    throws DaoException {
    	return dao.updateStudentRoadTestCompletionDate(parameterObject);
    }
    
    public Integer getStudentAuditCount(Object parameterObject)
    throws DaoException {
    	return dao.getStudentAuditCount(parameterObject);
    }
    
    public int updateStudentAudit(Object parameterObject)
    throws DaoException {
    	return dao.updateStudentAudit(parameterObject);
    }
    
    public int insertStudentAudit(Object parameterObject)
    throws DaoException {
    	return dao.insertStudentAudit(parameterObject);
    }
}
