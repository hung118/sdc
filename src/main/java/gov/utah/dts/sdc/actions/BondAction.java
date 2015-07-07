package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Bond;
import gov.utah.dts.sdc.service.BondService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({"unchecked", "unused", "rawtypes"})
public class BondAction extends SDCSupport implements Preparable {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(BondAction.class);
    private Integer bondsPk;
    private String searchSchoolFk;
    private String activeFlag;
    private String editAction;
    private List<Bond> availableList;
    private Bond bond;
    private BondService bondService;
    
    public String execute() throws Exception {
        log.debug("execute");
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("save");
        if (bond.getBondsPk() != null) {
            int returnCode = getBondService().update(bond);
            if (returnCode > 0) {
                log.debug("returnCode " + returnCode);
                addActionMessage(bond.getCompany() + " Successfully Updated.");
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
        int ret = getBondService().insert(bond);
        if (ret > 0) {
            Integer id = getSdcService().getLastInsertedId();
            addActionMessage("Bond Successfully Created");
            log.debug("actionMessage set");
            availableList = null;
            return Constants.LIST;
        }
        return INPUT;
    }

    public String delete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("bondsPk", getBondsPk());
        hm.put("deleted", "1");
        try {
            int ret = getBondService().delete(hm);
            addActionMessage("Bond Inactivated");
            availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.bond", ex.getMessage()));
        }
        return SUCCESS;
    }

    public String undelete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("bondsPk", getBondsPk());
        hm.put("deleted", "0");
        try {
            int ret = getBondService().delete(hm);
            addActionMessage("Bond Activated");
            availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.bond", ex.getMessage()));
        }
        return SUCCESS;
    }

    public String doSearch() throws Exception {
        log.debug("doSearch");
    	if (activeFlag == null) {
    		activeFlag = "0";
    	}
        return SUCCESS;
    }

    public String doList() throws Exception {
        log.debug("doList");
        return SUCCESS;
    }

	public Collection getAvailableList() throws Exception {
        log.debug("Bond getAvailableList");
    	if (availableList == null) {
    		availableList = new ArrayList<Bond>();
    		Map<String, Object> hm = new HashMap<String, Object>();
            
    		if (searchSchoolFk != null && !"".equals(searchSchoolFk)) {
    			hm.put("schoolFk", searchSchoolFk);
    		}
    		if (activeFlag != null && !"".equals(activeFlag)) {
    			hm.put("deleted", activeFlag);
    		}
    		try {
    			availableList = getBondService().bondSearchBeginsWith(hm);
    		} catch (DaoException dex) {
    			log.debug("ERR", dex);
    		}
    	}
        return availableList;
    }

    public void prepare() throws Exception {
        log.debug("prepare");
        if (getBond() != null) {
            log.debug("not null");
        } else {
            Bond preFetched = fetch(getBondsPk());
            if (preFetched != null) {
                setBond(preFetched);
            }
        }        
    }

    public Bond fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for " + tryId);
        Bond result = null;
        if (tryId != null) {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("bondsPk", tryId);
            result = getBondService().getBond(hm);
        }
        log.debug("exit fetch for " + tryId);
        return result;
    }
    
    public BondService getBondService() {
    	if (bondService == null) {
    		setBondService(new BondService(getUmdLogonEmail()));
    	}
        return bondService;
    }

    public void setBondService(BondService bondService) {
        this.bondService = bondService;
    }

    public Bond getBond() {
        return bond;
    }

    public void setBond(Bond bond) {
        log.debug("setBond");
        this.bond = bond;
    }

    public Integer getBondsPk() {
        return bondsPk;
    }

    public void setBondsPk(Integer bondsPk) {
        this.bondsPk = bondsPk;
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
