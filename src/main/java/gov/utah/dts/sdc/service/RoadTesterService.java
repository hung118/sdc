package gov.utah.dts.sdc.service;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.dao.RoadTesterDAO;
import gov.utah.dts.sdc.model.LabelValue;
import gov.utah.dts.sdc.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RoadTesterService {
	protected static Log log = LogFactory.getLog(RoadTesterService.class);

	private static RoadTesterDAO dao;
    
    public RoadTesterService() {
        dao = new RoadTesterDAO();
    }
    
    public List<LabelValue> getRoadTestersLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getRoadTestersLabelValue(parameterObject);
    }

    @SuppressWarnings("unchecked")
    public List<LabelValue> getSchoolsLabelValue(Object parameterObject)
    throws DaoException {
        return dao.getSchoolsLabelValue(parameterObject);
    } 

	public List<LabelValue> getRoadTestersBySchools(HttpServletRequest request, List<Long> schoolList)
    throws DaoException {
    	//log.error("getRoadTestersBySchools() begins...");
    	List<LabelValue> results = new ArrayList<LabelValue>();
		HttpSession session = request.getSession();
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	if (schoolList.size() > 0) {
    		hm.put("schoolList", schoolList);
    	}
    	if (request.isUserInRole(Constants.Role_Admin)) {
    		results = this.getRoadTestersLabelValue(hm);
    	}else{
    		// User is not an admin so they can only see their self.
        	Person person = new Person();
        	if (session.getAttribute(Constants.USER_KEY) != null) {
        		person = (Person)session.getAttribute(Constants.USER_KEY);
        		hm.put("personPk", person.getPersonPk().toString());
        		results = this.getRoadTestersLabelValue(hm);
        	}
    	}
    	return results;
    }

	public List<LabelValue> getRoadTestersBySchoolType(HttpServletRequest request, String schoolType)
    throws DaoException {
    	//log.error("getRoadTestersBySchoolType() begins...");
    	List<LabelValue> results = new ArrayList<LabelValue>();
		HttpSession session = request.getSession();
    	HashMap<String,String> hm = new HashMap<String,String>();
    	hm.put("schoolType", schoolType);
    	if (request.isUserInRole(Constants.Role_Admin)) {
    		results = this.getRoadTestersLabelValue(hm);
    	}else{
    		// User is not an admin so they can only see schools they have taught at or are currently assigned to.
        	Person person = new Person();
        	if (session.getAttribute(Constants.USER_KEY) != null) {
        		person = (Person)session.getAttribute(Constants.USER_KEY);
        		hm.put("personPk", person.getPersonPk().toString());
        		results = this.getRoadTestersLabelValue(hm);
        	}
    	}
    	return results;
    }

	public List<LabelValue> getSchoolLabelValueList(HttpServletRequest request, String schoolType)
    throws DaoException {
    	log.debug("getSchoolLabelValueList() begins...");
    	List<LabelValue> results = null;
		HttpSession session = request.getSession();
    	HashMap<String,String> hm = new HashMap<String,String>();
    	
    	if ("A".equals(schoolType)) {	// high school (0) and commercial (1)
        	hm.put("schoolType", "0");
        	if (request.isUserInRole(Constants.Role_Admin)) {
        		results = this.getSchoolsLabelValue(hm);
        	} else {
        		// User is not an admin so they can only see schools they have taught at or are currently assigned to.
            	Person person = new Person();
            	if (session.getAttribute(Constants.USER_KEY) != null) {
            		person = (Person)session.getAttribute(Constants.USER_KEY);
            		hm.put("personPk", person.getPersonPk().toString());
            		results = this.getSchoolsLabelValue(hm);
            	}
        	}
        	
        	List<LabelValue> results2 = null;
        	hm.put("schoolType", "1");
        	if (request.isUserInRole(Constants.Role_Admin)) {
        		results2 = this.getSchoolsLabelValue(hm);
        	} else {
        		// User is not an admin so they can only see schools they have taught at or are currently assigned to.
            	Person person = new Person();
            	if (session.getAttribute(Constants.USER_KEY) != null) {
            		person = (Person)session.getAttribute(Constants.USER_KEY);
            		hm.put("personPk", person.getPersonPk().toString());
            		results2 = this.getSchoolsLabelValue(hm);
            	}
        	}

        	results.addAll(results2);
        	
    		Collections.sort(results, new Comparator<LabelValue>() {
    			public int compare(LabelValue a, LabelValue b) {
    				return a.getLabel().compareToIgnoreCase(b.getLabel());
    			}
    		});
    	} else {	// high (0) school or commercial (1)
        	hm.put("schoolType", schoolType);
        	if (request.isUserInRole(Constants.Role_Admin)) {
        		results = this.getSchoolsLabelValue(hm);
        	} else {
        		// User is not an admin so they can only see schools they have taught at or are currently assigned to.
            	Person person = new Person();
            	if (session.getAttribute(Constants.USER_KEY) != null) {
            		person = (Person)session.getAttribute(Constants.USER_KEY);
            		hm.put("personPk", person.getPersonPk().toString());
            		results = this.getSchoolsLabelValue(hm);
            	}
        	}
    	}
    	

    	return results;
    }

}
