package gov.utah.dts.sdc.actions;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.CompletionSlip;
import gov.utah.dts.sdc.service.NewsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("unchecked")
public class OthersAction extends SDCSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(OthersAction.class);
	
	private Integer namevaluePk;
	private CompletionSlip namevalue;
	private String editAction;
	private  List<CompletionSlip>  availableList;
	private NewsService service;
	
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String saveCompletionSlip() throws Exception
    {
        log.debug("save");
        if (namevalue.getNamevaluePk() != null) {	// update
            int returnCode = getService().updateCompletionSlip(namevalue);
            if (returnCode > 0){
                addActionMessage("Completion Slip Updated");
                log.debug("actionMessage set");
            }
        } else {	// insert
            log.debug("insert");
            int ret = getService().insertCompletionSlip(namevalue);
            if (ret > 0) {
            	Integer id = getSdcService().getLastInsertedId();
            	addActionMessage("Completion Slip Created");
            	log.debug("actionMessage set");
            	namevalue.setNamevaluePk(id);;
            	availableList = null;
            }
        }
        return SUCCESS;
    }
    
	@Override
    public void prepare() throws Exception {
        if(getNamevalue() == null){
            CompletionSlip preFetched = fetch(getNamevaluePk());
            if (preFetched != null) {
                setNamevalue(preFetched);
            }
        }
    }
    
    public CompletionSlip fetch(Integer tryId) throws DaoException
    {
        log.debug("enter fetch for "+tryId);
        CompletionSlip result = null;
        if (tryId != null) {
            Map<String, Integer> hm = new HashMap<String, Integer>();
            hm.put("namevaluePk",tryId);
            result = getService().getCompletionSlip(hm);
        } else {
            result = new CompletionSlip();
        }
        log.debug("exit fetch for "+tryId);
        return result;
    }
	
	public String doListCompletionSlip() throws Exception {
		log.debug("save");
        return SUCCESS;
    }
	
    public NewsService getService() {
        if(service == null){
            return new NewsService(getUmdLogonEmail());
        }
        return service;
    }
	
	public  List<CompletionSlip> getAvailableList() throws Exception {
		log.debug("getAvailableList");
    	if (availableList == null) {
    		try {
    			availableList = getService().getCompletionSlips();
    		} catch (DaoException dex) {
    			log.debug("ERR", dex);
    		}
    	}
    	return availableList;
    }

	public Integer getNamevaluePk() {
		return namevaluePk;
	}

	public void setNamevaluePk(Integer namevaluePk) {
		this.namevaluePk = namevaluePk;
	}

	public CompletionSlip getNamevalue() {
		return namevalue;
	}

	public void setNamevalue(CompletionSlip namevalue) {
		this.namevalue = namevalue;
	}

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

}
