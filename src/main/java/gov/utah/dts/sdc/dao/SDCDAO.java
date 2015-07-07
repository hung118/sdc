package gov.utah.dts.sdc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SDCDAO extends BaseDAO {
    protected static Log log = LogFactory.getLog(SDCDAO.class);
    private static final String getLastInsertedId      = "getLastInsertedId";
       
    
    public java.lang.Integer getLastInsertedId() throws DaoException {
        return (Integer) getBaseObject(getLastInsertedId, null);
    }
}
