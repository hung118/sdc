package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.RoleTypesDAO;
import gov.utah.dts.sdc.model.Person;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleTypesService {
    private static RoleTypesDAO dao;
    
    public RoleTypesService() {
        dao = new RoleTypesDAO();
    }
    
    public List getRolesList(Object object) throws DaoException {
        return dao.getRolesList(object);
    }

    public String getRole(Object object) throws DaoException {
        return dao.getRole(object);
    }
    
}
