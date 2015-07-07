package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.NewsDAO;
import gov.utah.dts.sdc.model.CompletionSlip;
import gov.utah.dts.sdc.model.News;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class NewsService {
    protected static Log log = LogFactory.getLog(NewsService.class);
    private static NewsDAO dao;
    
    public NewsService() {
        dao = new NewsDAO();
    }
    
    public NewsService(String userId) {
    	dao = new NewsDAO(userId);
    }
    
     public List getList(Object parameterObject)
    throws DaoException {
        return dao.getList(parameterObject);
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

    public List getAllList(Object parameterObject)
    throws DaoException {
        return dao.getAllList(parameterObject);
    }

    public List newsSearchContains(Object parameterObject)
    throws DaoException {
        return dao.newsSearchContains(parameterObject);
    }

    public News getNews(Object tryId) throws DaoException {
        return dao.getNews(tryId);
    }
    
	public  List getCompletionSlips() throws DaoException {
    	return dao.getCompletionSlips();
    }
    
    public int insertCompletionSlip(Object parameterObject)
    throws DaoException {
        return dao.insertCompletionSlip(parameterObject);
    }
    
    public CompletionSlip getCompletionSlip(Object tryId) throws DaoException {
        return dao.getCompletionSlip(tryId);
    }
    
    public int updateCompletionSlip(Object parameterObject)
    throws DaoException {
        return dao.updateCompletionSlip(parameterObject);
    }
    
}
