package gov.utah.dts.sdc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuditDAO extends BaseDAO {
    protected static Log log = LogFactory.getLog(AuditDAO.class);
    private static final String baseEqualsCountStatement        = "baseCompletionCount";
    private static final String roadEqualsCountStatement        = "roadCompletionCount";
    private static final String writtenEqualsCountStatement     = "writtenCompletionCount";
    private static final String roadEligibleCountStatement      = "roadEligibleCount";
    private static final String baseInsertStatement             = "insertBaseCompletion";
    private static final String roadInsertStatement             = "insertRoadCompletion";
    private static final String roadUpdateStatement             = "updateRoadCompletion";
    private static final String roadDeleteStatement             = "deleteRoadCompletion";
    private static final String writtenInsertStatement          = "insertWrittenCompletion";
    
    public AuditDAO() {
	}
    
    public AuditDAO(String userId) {
    	super.userId = userId;
    }
    
public java.lang.Integer getBaseEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(baseEqualsCountStatement, parameterObject);
    }

public java.lang.Integer getRoadEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(roadEqualsCountStatement, parameterObject);
    }

public java.lang.Integer getWrittenEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(writtenEqualsCountStatement, parameterObject);
    }

public java.lang.Integer getRoadEligibleCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(roadEligibleCountStatement, parameterObject);
    }

public int baseCompletionInsert(Object parameterObject)
            throws DaoException {
        return baseUpdate(baseInsertStatement,parameterObject);
    }

public int roadCompletionInsert(Object parameterObject)
            throws DaoException {
        return baseUpdate(roadInsertStatement,parameterObject);
    }

public int roadCompletionUpdate(Object parameterObject) throws DaoException {
	return baseUpdate(roadUpdateStatement, parameterObject);
}

public int roadCompletionDelete(Object parameterObject) throws DaoException {
	return baseDelete(roadDeleteStatement, parameterObject);
}

public int writtenCompletionInsert(Object parameterObject)
            throws DaoException {
        return baseUpdate(writtenInsertStatement,parameterObject);
    }
}
