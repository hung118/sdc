package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.ClassroomDAO;
import gov.utah.dts.sdc.model.Classroom;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class ClassroomService {
    private static ClassroomDAO dao;
    protected static Log log = LogFactory.getLog(ClassroomService.class);
    
    public ClassroomService() {
        dao = new ClassroomDAO();
    }
    
    public ClassroomService(String userId) {
    	dao = new ClassroomDAO(userId);
    }
    
    public Integer getEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }
    
	public List searchEquals(Object parameterObject)
    throws DaoException {
        return dao.searchEquals(parameterObject);
    }
    
    public List getClassroomList(Object parameterObject)
    throws DaoException {
        return dao.getClassroomList(parameterObject);
    }

    public List classroomSearchBeginsWith(Object parameterObject)
    throws DaoException {
        return dao.classroomSearchBeginsWith(parameterObject);
    }

    public List getBranchList(Object parameterObject)
    throws DaoException {
        return dao.getBranchList(parameterObject);
    }
    
    public Classroom getClassroom(Object tryId) throws DaoException {
        return dao.getClassroom(tryId);
    }
    
    public Classroom getSchoolInfo(Object tryId) throws DaoException {
        return dao.getSchoolInfo(tryId);
    }
    
    public int insert(Object parameterObject)
    throws DaoException {
        return dao.insert(parameterObject);
    }
    public int update(Object parameterObject)
    throws DaoException {
        return dao.update(parameterObject);
    }
    public int delete(Object parameterObject)
    throws DaoException {
        return dao.delete(parameterObject);
    }

    public List getUserClassroomLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getUserClassroomLabelValue(parameterObject);
    } 
    public List getAllClassroomLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getAllClassroomLabelValue(parameterObject);
    } 
    
    public int removeStudent(Object parameterObject)
    throws DaoException {
        return dao.removeStudent(parameterObject);
    }
    
    public int addStudent(Object parameterObject)
    throws DaoException {
        return dao.addStudent(parameterObject);
    }
    
    public Long getStudentNumber(Object parameterObject)
    throws DaoException {
        return dao.getStudentNumber(parameterObject);
    }
    public List getSchoolInfoByStudent(Object tryId) throws DaoException {
        return dao.getSchoolInfoByStudent(tryId);
    }
    
    public int hideClassroomStudent(Object parameterObject)
    throws DaoException {
        return dao.hideClassroomStudent(parameterObject);
    }
    
}
