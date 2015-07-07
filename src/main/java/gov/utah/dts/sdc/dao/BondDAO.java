package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.Bond;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BondDAO extends BaseDAO {

    protected static Log log = LogFactory.getLog(BondDAO.class);
    private static final String selectStatement = "bondSelect";
    private static final String beginsWithStatement = "bondSearchBeginsWith";
    private static final String equalsCountStatement = "bondEqualsCount";
    private static final String insertStatement = "bondInsert";
    private static final String updateStatement = "bondUpdate";
    private static final String deleteStatement = "bondDelete";

	public BondDAO() {
	}
	
    public BondDAO(String userId) {
		super.userId = userId;
	}

	public java.lang.Integer getEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(equalsCountStatement, parameterObject);
    }

    public List getBondList(Object parameterObject) throws DaoException {
        return getBaseList(selectStatement, parameterObject);
    }

    public List bondSearchBeginsWith(Object parameterObject) throws DaoException {
        return getBaseList(beginsWithStatement, parameterObject);
    }

    public Bond getBond(Object tryId) throws DaoException {
        List list = getBaseList(selectStatement, tryId);
        if (list.isEmpty()) {
            return new Bond();
        } else {
            return (Bond) list.get(0);
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
}
