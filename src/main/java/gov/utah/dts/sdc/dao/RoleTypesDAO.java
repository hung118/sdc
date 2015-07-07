package gov.utah.dts.sdc.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RoleTypesDAO extends BaseDAO {
   private static String getRolesStatement       = "getRolesList";
   private static String getRoleStatement       = "getRole";
    
    
    public List getRolesList(Object object) throws DaoException {
        return getBaseList(getRolesStatement, object);
    }

    public String getRole(Object object) throws DaoException {
        return (String) getBaseObject(getRoleStatement,object);
    }
}
