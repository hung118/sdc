package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Vehicle;
import gov.utah.dts.sdc.service.VehicleService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VehicleAction extends SDCSupport implements Preparable {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(VehicleAction.class);
    private Integer vehiclePk;
    private String searchVehiclePlate;
    private String searchSchoolFk;
    private String activeFlag;
    private String editAction;
    private List<Vehicle> availableList;
    private Vehicle currentVehicle;
    private VehicleService vehicleService;
 

    public String execute() throws Exception {
        log.debug("execute");
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("save");
        if (currentVehicle.getVehiclePk() != null) {
            int returnCode = getVehicleService().update(currentVehicle);
            if (returnCode > 0) {
                log.debug("returnCode " + returnCode);
                addActionMessage(currentVehicle.getVehiclePlate() + " Successfully Updated.");
                log.debug("actionMessage set");
                availableList = null;
                return Constants.LIST;
            }
        } else {
            return insert();
        }
        return SUCCESS;
    }

    public String insert() throws Exception {
        log.debug("insert");

        int ret = getVehicleService().insert(currentVehicle);
        if (ret > 0) {
            Integer id = getSdcService().getLastInsertedId();
            addActionMessage("Vehicle Successfully Created");
            log.debug("actionMessage set");
            availableList = null;
            return Constants.LIST;
        }
        return INPUT;
    }

    public String delete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("vehiclePk", getVehiclePk());
        hm.put("deleted","1");
        try {
            int ret = getVehicleService().delete(hm);
            addActionMessage("Vehicle Inactivated");
            availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.vehicle", ex.getMessage()));
        }
        return SUCCESS;
    }

    public String undelete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("vehiclePk", getVehiclePk());
        hm.put("deleted","0");
        try {
            int ret = getVehicleService().delete(hm);
            addActionMessage("Vehicle Activated");
            availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.vehicle", ex.getMessage()));
        }
        return SUCCESS;
    }

    public String doList() throws Exception {
        log.debug("doList");
        return SUCCESS;
    }

    public String doSearch() throws Exception {
        log.debug("doSearch");
    	if (activeFlag == null) {
    		activeFlag = "0";
    	}
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
	public Collection<Vehicle> getAvailableList() throws Exception {
        log.debug("Vehicle getAvailableList");
    	if (availableList == null) {
    		availableList = new ArrayList<Vehicle>();
    		Map<String, Object> hm = new HashMap<String, Object>();
            
    		if (searchVehiclePlate != null && !"".equals(searchVehiclePlate)) {
    			hm.put("vehiclePlate", searchVehiclePlate.trim().toUpperCase()+'%');
    		}
    		if (searchSchoolFk != null && !"".equals(searchSchoolFk)) {
    			hm.put("schoolFk", searchSchoolFk);
    		}
    		if (activeFlag != null && !"".equals(activeFlag)) {
    			hm.put("deleted", activeFlag);
    		}
    		try {
    			availableList = getVehicleService().vehicleSearchBeginsWith(hm);
    		} catch (DaoException dex) {
    			log.debug("ERR", dex);
    		}
    	}
        return availableList;
    }

    public void prepare() throws Exception {
        log.debug("prepare");
        if (getCurrentVehicle() != null) {
            log.debug("not null");
        } else {
             Vehicle preFetched = fetch(getVehiclePk());
            if (preFetched != null) {
                setCurrentVehicle(preFetched);
            }
        }

    }

    public Vehicle fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for " + tryId);
        Vehicle result = null;
        if (tryId != null) {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("vehiclePk", tryId);
            result = getVehicleService().getVehicle(hm);
        }
        log.debug("exit fetch for " + tryId);
        return result;
    }
    
    public VehicleService getVehicleService() {
    	if (vehicleService == null) {
    		vehicleService = new VehicleService(getUmdLogonEmail());
    	}
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        log.debug("setCurrentVehicle");
        this.currentVehicle = currentVehicle;
    }

    public Integer getVehiclePk() {
        return vehiclePk;
    }

    public void setVehiclePk(Integer vehiclePk) {
        this.vehiclePk = vehiclePk;
    }

	public String getSearchVehiclePlate() {
		return searchVehiclePlate;
	}

	public void setSearchVehiclePlate(String searchVehiclePlate) {
		this.searchVehiclePlate = searchVehiclePlate;
	}

	public String getSearchSchoolFk() {
		return searchSchoolFk;
	}

	public void setSearchSchoolFk(String searchSchoolFk) {
		this.searchSchoolFk = searchSchoolFk;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}
    
}
