package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.BondDAO;
import gov.utah.dts.sdc.model.Bond;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BondService {

    protected static Log log = LogFactory.getLog(BondService.class);
    private static BondDAO dao;

    public BondService() {
        dao = new BondDAO();
    }
    
    public BondService(String userId) {
        dao = new BondDAO(userId);
    }

    public Integer getEqualsCount(Object parameterObject) throws DaoException {
        return dao.getEqualsCount(parameterObject);
    }

   public List getBondList(Object parameterObject) throws DaoException {
        return dao.getBondList(parameterObject);
    }

   public List bondSearchBeginsWith(Object parameterObject) throws DaoException {
       return dao.bondSearchBeginsWith(parameterObject);
   }

    public Bond getBond(Object tryId) throws DaoException {
        return dao.getBond(tryId);
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
}
