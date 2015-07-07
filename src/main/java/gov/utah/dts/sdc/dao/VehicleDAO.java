package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.Vehicle;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VehicleDAO extends BaseDAO {

    protected static Log log = LogFactory.getLog(VehicleDAO.class);
    private static final String selectStatement = "vehicleSelect";
    private static final String searchBeginsWithStatement = "vehicleSearchBeginsWith";
    private static final String equalsCountStatement = "vehicleEqualsCount";
    private static final String insertStatement = "vehicleInsert";
    private static final String updateStatement = "vehicleUpdate";
    private static final String deleteStatement = "vehicleDelete";
    private static final String commercialVehicleStatement = "commercialVehicleSearch";
    private static final String classroomVehicleStatement = "classroomVehicleSearch";

    public VehicleDAO() {
    	
    }
    
    public VehicleDAO(String userId) {
    	super.userId = userId;
    }
    
    public java.lang.Integer getEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(equalsCountStatement, parameterObject);
    }

    public List getVehicleList(Object parameterObject) throws DaoException {
        return getBaseList(selectStatement, parameterObject);
    }

    public List vehicleSearchBeginsWith(Object parameterObject) throws DaoException {
        return getBaseList(searchBeginsWithStatement, parameterObject);
    }

    public Vehicle getVehicle(Object tryId) throws DaoException {
        List list = getBaseList(selectStatement, tryId);
        if (list.isEmpty()) {
            return new Vehicle();
        } else {
            return (Vehicle) list.get(0);
        }
    }

    public int insert(Object parameterObject) throws DaoException {
        log.debug("insert");
        return baseUpdate(insertStatement, parameterObject);
    }

    public int update(Object parameterObject) throws DaoException {
        return baseUpdate(updateStatement, parameterObject);
    }

    public int delete(Object parameterObject) throws DaoException {
        return baseUpdate(deleteStatement, parameterObject);
    }

    public List getClassroomVehicleSearch(Object parameterObject) throws DaoException {
        return getBaseList(classroomVehicleStatement, parameterObject);
    }

    public List getCommercialVehicleSearch(Object parameterObject) throws DaoException {
        return getBaseList(commercialVehicleStatement, parameterObject);
    }
}
