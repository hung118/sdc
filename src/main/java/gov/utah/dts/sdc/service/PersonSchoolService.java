package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.PersonSchoolDAO;

public class PersonSchoolService {
    private static PersonSchoolDAO dao;
    
    
    public PersonSchoolService() {
        dao = new PersonSchoolDAO();
    }
    
    public PersonSchoolService(String userId) {
    	dao = new PersonSchoolDAO(userId);
    }
    
    public Integer[] getPersonSchool(Object object) throws DaoException {
        return dao.getPersonSchool(object);
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
}
