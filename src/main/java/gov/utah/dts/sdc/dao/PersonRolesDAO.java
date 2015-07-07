package gov.utah.dts.sdc.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersonRolesDAO extends BaseDAO {
    private static Log log = LogFactory.getLog(PersonRolesDAO.class);
    private static String getRolesStatement       = "getPersonRoles";
    private static final String insertStatement         = "personRolesInsert";
    private static final String updateStatement         = "personRolesUpdate";
    private static final String deleteStatement         = "personRolesDelete";
    
    public PersonRolesDAO() {
    	
    }
    
    public PersonRolesDAO(String userId) {
    	super.userId = userId;
    }
    
    public Integer[] getPersonRoles(Object object) throws DaoException {
        List list = getBaseList(getRolesStatement, object);
        Integer[] tempArray = new Integer[list.size()];
        return (Integer[]) list.toArray(tempArray);
    }
    
    public int insert(Object parameterObject)
    throws DaoException {
        log.debug("insert");
        return baseUpdate(insertStatement,parameterObject);
    }
    public int update(Object parameterObject)
    throws DaoException {
        return baseUpdate(updateStatement,parameterObject);
    }
    public int delete(Object parameterObject)
    throws DaoException {
        return baseUpdate(deleteStatement,parameterObject);
    }
}
