package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.VehicleDAO;
import gov.utah.dts.sdc.model.Vehicle;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VehicleService {

    protected static Log log = LogFactory.getLog(VehicleService.class);
    private static VehicleDAO dao;

    public VehicleService() {
        dao = new VehicleDAO();
    }
    
    public VehicleService(String userId) {
    	dao = new VehicleDAO(userId);
    }

    public Integer getEqualsCount(Object parameterObject) throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }

   public List getVehicleList(Object parameterObject) throws DaoException {
        return dao.getVehicleList(parameterObject);
    }

   public List vehicleSearchBeginsWith(Object parameterObject) throws DaoException {
       return dao.vehicleSearchBeginsWith(parameterObject);
   }

    public Vehicle getVehicle(Object tryId) throws DaoException {
        return dao.getVehicle(tryId);
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

    public List getClassroomVehicleSearch(Object parameterObject) throws DaoException {
        return dao.getClassroomVehicleSearch(parameterObject);
    }
    public List getCommercialVehicleSearch(Object parameterObject) throws DaoException {
        return dao.getCommercialVehicleSearch(parameterObject);
    }
}
