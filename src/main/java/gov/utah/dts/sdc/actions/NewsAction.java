package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.News;
import gov.utah.dts.sdc.model.Vehicle;
import gov.utah.dts.sdc.service.NewsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewsAction extends SDCSupport implements Preparable, ModelDriven{
    
    protected static Log log = LogFactory.getLog(NewsAction.class);
    private Integer newsPk;
    private String searchDescription;
    private String searchRoleTypesFk;
    private String activeFlag;
    private String editAction;
    private List<News> availableList;
    private News currentNews;
    private NewsService newsService;
    private String updatedBy;
    
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String save() throws Exception
    {
        log.debug("save");
        if (currentNews.getNewsPk() != null){
            log.debug("a "+  currentNews.getNewsPk() + " "+ currentNews.getDescription());
            int returnCode = getNewsService().update(currentNews);
            if (returnCode > 0){
                log.debug("returnCode "+ returnCode);
                addActionMessage("News Item Updated");
                log.debug("actionMessage set");
            }
        }else {
            return insert();
        }
        return SUCCESS;
    }
    
    public String insert() throws Exception
    {
        log.debug("insert");
        int ret = getNewsService().insert(currentNews);
        if (ret > 0) {
        	Integer id = getSdcService().getLastInsertedId();
        	addActionMessage("News Item Created");
        	log.debug("actionMessage set");
        	currentNews.setNewsPk(id);
        	availableList = null;
        }
        return SUCCESS;
    }
    
    public String delete() throws Exception
    {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("newsPk",getNewsPk());
        hm.put("deleted", "1");
        try {
            int ret = getNewsService().delete(hm);
            if (ret > 0) {
            	addActionMessage("News Item Inactivated");
            	availableList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.news",ex.getMessage() ) );
        }
        return SUCCESS;
    }

    public String undelete() throws Exception
    {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("newsPk",getNewsPk());
        hm.put("deleted", "0");
        try {
            int ret = getNewsService().delete(hm);
            if (ret > 0) {
            	addActionMessage("News Item Activated");
            	availableList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.news",ex.getMessage() ) );
        }
        return SUCCESS;
    }

    public String doSearch() throws Exception
    {
        log.debug("doSearch");
    	if (activeFlag == null) {
    		activeFlag = "0";
    	}
        return SUCCESS;
    }

    public String doList() throws Exception
    {
        log.debug("doList");
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public Collection getAvailableList() throws Exception
    {
		log.debug("getAvailableList");
    	if (availableList == null) {
    		availableList = new ArrayList<News>();
            Map<String,Object> hm = new HashMap<String,Object>();
            if (searchDescription != null && !"".equals(searchDescription.trim())) {
            	hm.put("description", '%'+searchDescription.trim().toUpperCase()+'%');
            }
            if (searchRoleTypesFk != null && !"".equals(searchRoleTypesFk)) {
            	hm.put("roleTypesFk", searchRoleTypesFk);
            }
            if (activeFlag != null && !"".equals(activeFlag)) {
            	hm.put("deleted", activeFlag);
            }
    		try {
    			availableList = getNewsService().newsSearchContains(hm);
    		} catch (DaoException dex) {
    			log.debug("ERR", dex);
    		}
    	}
    	return availableList;
    }
    
    public void prepare() throws Exception
    {
        if(getCurrentNews() == null){
            News preFetched = fetch(getNewsPk());
            if (preFetched != null) {
                setCurrentNews(preFetched);
            }
        }
    }
    
    public News fetch(Integer tryId) throws DaoException
    {
        log.debug("enter fetch for "+tryId);
        News result = null;
        if (tryId != null) {
            Map hm = new HashMap();
            hm.put("newsPk",tryId);
            result = getNewsService().getNews(hm);
        } else {
            result = new News();
        }
        log.debug("exit fetch for "+tryId);
        return result;
    }
    
    public Integer getNewsPk() {
        return newsPk;
    }
    
    public void setNewsPk(Integer newsPk) {
        this.newsPk = newsPk;
    }
    
    public NewsService getNewsService() {
        if(newsService == null){
            setNewsService(new NewsService(getUmdLogonEmail()));
        }
        return newsService;
    }
    
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }
    
    public News getCurrentNews() {
        return currentNews;
    }
    
    public void setCurrentNews(News currentNews) {
        this.currentNews = currentNews;
    }
    
    public Object getModel() {
        return currentNews;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

	public String getSearchDescription() {
		return searchDescription;
	}

	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public String getSearchRoleTypesFk() {
		return searchRoleTypesFk;
	}

	public void setSearchRoleTypesFk(String searchRoleTypesFk) {
		this.searchRoleTypesFk = searchRoleTypesFk;
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
