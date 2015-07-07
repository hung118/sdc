package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.PersonDAO;
import gov.utah.dts.sdc.model.Person;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class PersonService {

    protected static Log log = LogFactory.getLog(PersonService.class);
    private static PersonDAO dao;

    public PersonService() {
        dao = new PersonDAO();
    }
    
    public PersonService(String userId) {
    	dao = new PersonDAO(userId);
    }

    public Integer getEqualsCount(Object parameterObject) throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }

	public List searchEquals(Object parameterObject) throws DaoException {
        return dao.searchEquals(parameterObject);
    }

    public List getPersonList(Object parameterObject) throws DaoException {
        return dao.getPersonList(parameterObject);
    }

    public List getPersonBeginsWithList(Object parameterObject) throws DaoException {
        return dao.getPersonBeginsWithList(parameterObject);
    }
    
    public Person getPerson(Object tryId) throws DaoException {
        return dao.getPerson(tryId);
    }

    public int insert(Object parameterObject) throws DaoException {
        return dao.insert(parameterObject);
    }

    public int update(Object parameterObject) throws DaoException {
        return dao.update(parameterObject);
    }

    public int delete(Object parameterObject) throws DaoException {
        return dao.delete(parameterObject);
    }

    public List getInstructorLabelValue(Object parameterObject) throws DaoException {
        return dao.getInstructorLabelValue(parameterObject);
    }

    public List getClassroomInstructorSearch(Object parameterObject) throws DaoException {
        return dao.getClassroomInstructorSearch(parameterObject);
    }
    
    public List getCommercialInstructorSearch(Object parameterObject) throws DaoException {
        return dao.getCommercialInstructorSearch(parameterObject);
    }
    public List getRoadTesterInstructorSearch(Object parameterObject) throws DaoException {
        return dao.getRoadTesterInstructorSearch(parameterObject);
    }
     
}
