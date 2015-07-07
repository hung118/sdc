package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.CompletionSlip;
import gov.utah.dts.sdc.model.News;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({"rawtypes"})
public class NewsDAO extends BaseDAO {
	protected static Log log = LogFactory.getLog(NewsDAO.class);
	private static final String selectUserStatement = "newsUserSelect";
	private static final String selectAllStatement = "newsAdminSelect";
	private static final String searchContainsStatement = "newsSearchContains";
	private static final String insertStatement = "newsInsert";
	private static final String updateStatement = "newsUpdate";
	private static final String deleteStatement = "newsDelete";
	private static final String completionSlipsSelect = "completionSlipsSelect";
	private static final String insertCompletionSlip = "insertCompletionSlip";
	private static final String updateCompletionSlip = "updateCompletionSlip";
	
	public NewsDAO() {

	}

	public NewsDAO(String userId) {
		super.userId = userId;
	}

	public List getList(Object parameterObject) throws DaoException {
		return getBaseList(selectUserStatement, parameterObject);
	}

	public List getAllList(Object parameterObject) throws DaoException {
		return getBaseList(selectAllStatement, parameterObject);
	}

	public List newsSearchContains(Object parameterObject) throws DaoException {
		return getBaseList(searchContainsStatement, parameterObject);
	}

	public int insert(Object parameterObject) throws DaoException {
		return baseUpdate(insertStatement, parameterObject);
	}

	public int update(Object parameterObject) throws DaoException {
		return baseUpdate(updateStatement, parameterObject);
	}

	public int delete(Object parameterObject) throws DaoException {
		return baseUpdate(deleteStatement, parameterObject);
	}

	public News getNews(Object tryId) throws DaoException {
		List list = getBaseList(selectAllStatement, tryId);
		if (list.isEmpty()) {
			return new News();
		} else {
			return (News) list.get(0);
		}
	}

	public List getCompletionSlips() throws DaoException {
		return getBaseList(completionSlipsSelect, null);
	}
	
	public int insertCompletionSlip(Object parameterObject) throws DaoException {
		return baseUpdate(insertCompletionSlip, parameterObject);
	}
	
	public CompletionSlip getCompletionSlip(Object tryId) throws DaoException {
		List list = getBaseList(completionSlipsSelect, tryId);
		if (list.isEmpty()) {
			return new CompletionSlip();
		} else {
			return (CompletionSlip) list.get(0);
		}
	}
	
	public int updateCompletionSlip(Object parameterObject) throws DaoException {
		return baseUpdate(updateCompletionSlip, parameterObject);
	}
	
}
