package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.PersonRolesDAO;

public class PersonRolesService {
    private static PersonRolesDAO dao;
    
    public PersonRolesService() {
        dao = new PersonRolesDAO();
    }
    
    public PersonRolesService(String userId) {
    	dao = new PersonRolesDAO(userId);
    }
    
    public Integer[] getPersonRoles(Object object) throws DaoException {
        return dao.getPersonRoles(object);
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
