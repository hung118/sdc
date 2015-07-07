package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.Person;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersonDAO extends BaseDAO {
    protected static Log log = LogFactory.getLog(PersonDAO.class);
    private static final String selectStatement         = "personSelect";
    private static final String equalsCountStatement    = "personEqualsCount";
    private static final String searchEqualsStatement   = "personSearchEquals";
    private static final String searchBeginsWithStatement = "personSearchBeginsWith";
    private static final String insertStatement         = "personInsert";
    private static final String updateStatement         = "personUpdate";
    private static final String deleteStatement         = "personDelete";
    private static final String getInstructorLabelValueStatement = "instructorLabelValue";
    private static final String getCommercialInstructorStatement = "commercialInstructorSearch";
    private static final String getClassroomInstructorStatement = "classroomInstructorSearch";
    private static final String getRoadTesterInstructorStatement = "roadTesterInstructorSearch";
    
    public PersonDAO() {
    	
    }
    
    public PersonDAO(String userId) {
    	super.userId = userId;
    }
    
    public java.lang.Integer getEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(equalsCountStatement, parameterObject);
    }
    
    public List searchEquals(Object parameterObject) throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }
    
    public List getPersonList(Object parameterObject)  throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }

    public List getPersonBeginsWithList(Object parameterObject)  throws DaoException {
        return getBaseList(searchBeginsWithStatement, parameterObject);
    }

    public Person getPerson(Object tryId) throws DaoException {
        List list = getBaseList(selectStatement, tryId);
        if (list.isEmpty()){
            return new Person();
        }else {
            return (Person) list.get(0);
        }
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

    public List getInstructorLabelValue(Object parameterObject)  throws DaoException {
        return getBaseList(getInstructorLabelValueStatement, parameterObject);
    }    
    
    public List getClassroomInstructorSearch(Object parameterObject)  throws DaoException {
        return getBaseList(getClassroomInstructorStatement, parameterObject);
    }
    
    public List getCommercialInstructorSearch(Object parameterObject)  throws DaoException {
        return getBaseList(getCommercialInstructorStatement, parameterObject);
    } 
    public List getRoadTesterInstructorSearch(Object parameterObject)  throws DaoException {
        return getBaseList(getRoadTesterInstructorStatement, parameterObject);
    } 
     
}
