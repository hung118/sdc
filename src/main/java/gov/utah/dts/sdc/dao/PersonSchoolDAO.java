package gov.utah.dts.sdc.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersonSchoolDAO extends BaseDAO {
    private static Log log = LogFactory.getLog(PersonSchoolDAO.class);
    private static String getPersonSchoolStatement       = "getPersonSchool";
    private static final String insertStatement         = "personSchoolInsert";
    private static final String updateStatement         = "personSchoolUpdate";
    private static final String deleteStatement         = "personSchoolDelete";
    
    public PersonSchoolDAO() {
    	
    }
    
    public PersonSchoolDAO(String userId) {
    	super.userId = userId;
    }
    
    public Integer[] getPersonSchool(Object object) throws DaoException {
        List list = getBaseList(getPersonSchoolStatement, object);
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
