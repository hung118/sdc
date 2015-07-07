package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.AuditDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditService {
    protected static Log log = LogFactory.getLog(AuditService.class);
    private static AuditDAO dao;
    
    public AuditService() {
        dao = new AuditDAO();
    }
    
    public AuditService(String userId) {
    	dao = new AuditDAO(userId);
    }

public Integer getBaseEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getBaseEqualsCount(parameterObject);
    }

public Integer getRoadEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getRoadEqualsCount(parameterObject);
    }

public Integer getWrittenEqualsCount(Object parameterObject)
    throws DaoException {
        return dao.getWrittenEqualsCount(parameterObject);
    }

public Integer getRoadEligibleCount(Object parameterObject)
    throws DaoException {
        return dao.getRoadEligibleCount(parameterObject);
    }

public int baseCompletionInsert(Object parameterObject)
    throws DaoException {
        return dao.baseCompletionInsert(parameterObject);
    }

public int roadCompletionInsert(Object parameterObject)
    throws DaoException {
        return dao.roadCompletionInsert(parameterObject);
    }

public int roadCompletionUpdate(Object parameterObject) throws DaoException {
	return dao.roadCompletionUpdate(parameterObject);
}

public int roadCompletionDelete(Object parameterObject) throws DaoException {
	return dao.roadCompletionDelete(parameterObject);
}

public int writtenCompletionInsert(Object parameterObject)
    throws DaoException {
        return dao.writtenCompletionInsert(parameterObject);
    }
        
}
