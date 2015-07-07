package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.SchoolDAO;
import gov.utah.dts.sdc.model.School;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class SchoolService {
	protected static Log log = LogFactory.getLog(SchoolService.class);

	private static SchoolDAO dao;
    
    public SchoolService() {
        dao = new SchoolDAO();
    }
    
    public SchoolService(String userId) {
    	dao = new SchoolDAO(userId);
    }
    
    public Integer getEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }
    
    
    public List searchEquals(Object parameterObject)
    throws DaoException {
        return dao.searchEquals(parameterObject);
    }

    public List searchBeginsWith(Object parameterObject)
    throws DaoException {
        return dao.searchBeginsWith(parameterObject);
    }

    public List getSchoolList(Object parameterObject)
    throws DaoException {
        return dao.getSchoolList(parameterObject);
    }
    
    public List getCompletionTests(Object parameterObject)
    throws DaoException {
        return dao.getCompletionTests(parameterObject);
    }
    
    public School getSchool(Object tryId) throws DaoException {
        return dao.getSchool(tryId);
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

    public List getUserSchoolLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getUserSchoolLabelValue(parameterObject);
    } 
    
    public List getUserSchoolLabelValue2(Object parameterObject)
    throws DaoException {
        return dao.getUserSchoolLabelValue2(parameterObject);
    } 

    public List getAllSchoolLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getAllSchoolLabelValue(parameterObject);
    } 
    
    public List getAllSchoolLabelValue2(Object parameterObject)
    throws DaoException {
        return dao.getAllSchoolLabelValue2(parameterObject);
    } 

    public Integer getBranchEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getBranchEqualsCount(parameterObject);
    }
    
    public List selectBranch(Object parameterObject)
    throws DaoException {
        return dao.selectBranch(parameterObject);
    }
    
    public int insertBranch(Object parameterObject)
    throws DaoException {
        return dao.insertBranch(parameterObject);
    }
    public int updateBranch(Object parameterObject)
    throws DaoException {
        return dao.updateBranch(parameterObject);
    }
    public int deleteBranch(Object parameterObject)
    throws DaoException {
        return dao.deleteBranch(parameterObject);
    }
}
