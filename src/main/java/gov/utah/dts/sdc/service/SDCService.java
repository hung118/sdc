package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.SDCDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SDCService {
    protected static Log log = LogFactory.getLog(SDCService.class);
    private static SDCDAO dao;
    
    public SDCService() {
        dao = new SDCDAO();
    }
        
    public Integer getLastInsertedId()
    throws DaoException {
        return dao.getLastInsertedId();
    }
    
}
