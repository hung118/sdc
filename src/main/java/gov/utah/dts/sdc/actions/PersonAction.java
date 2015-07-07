package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.service.PersonRolesService;
import gov.utah.dts.sdc.service.PersonSchoolService;
import gov.utah.dts.sdc.service.PersonService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class PersonAction extends SDCSupport implements Preparable, ServletRequestAware {

    protected static Log log = LogFactory.getLog(PersonAction.class);
    private Integer personPk;
    private Person currentPerson;
    private String searchInstructor;
    private String searchFirstName;
    private String searchLastName;
    private String searchSchoolName;
    private String searchInstructorNum;
    private String activeFlag;
    private String editAction;
    private List<Person> availableList;
    private PersonService personService;
    private HttpServletRequest servletRequest;
    
	@Override
    public String execute() throws Exception {
        log.debug("execute");
        return SUCCESS;
    }

    public String save() throws Exception {
        log.debug("save");
        try {
            if (currentPerson.getPersonPk() != null) {
                setPersonRoles(currentPerson);
                setAssociatedSchools(currentPerson);
                int returnCode = getPersonService().update(currentPerson);
                if (returnCode > 0) {
                    log.debug("returnCode " + returnCode);
                    //Won't work cause returning to a redirected action and it becomes lost.
                    addActionMessage(currentPerson.getEmail() + " Successfully Updated.");
                    log.debug("actionMessage set");
                    return Constants.LIST;
                }
            } else {
                return insert();
            }
        } catch (DaoException ex) {
            log.error("actionError", ex);
            if (ex.getMessage().contains("email_constraint")) {
            	addActionError("Error! Duplicate email address. Please check your email and try again.");
            } else {
            	addActionError("Error updating record. Please check your fields and try again.");
            }
            return INPUT;
        }
        return SUCCESS;
    }

    public String insert() throws Exception {
        log.debug("insert");
        try {
            int ret = getPersonService().insert(currentPerson);
            if (ret > 0) {
                Integer id = getSdcService().getLastInsertedId();
                setPersonRoles(currentPerson, id);
                setAssociatedSchools(currentPerson, id);
                addActionMessage("User Created");
                log.debug("actionMessage set");
                availableList = null;
                return Constants.LIST;
            }        	
        } catch (DaoException ex) {
            log.error("actionError", ex);
            if (ex.getMessage().contains("email_constraint")) {
            	addActionError("Error! Duplicate email address. Please check your email and try again.");
            } else {
            	addActionError("Error updating record. Please check your fields and try again.");
            }
            return INPUT;
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("personPk", getPersonPk());
        try {
            deletePersonRoles(currentPerson);
            /*
             *  Redmine enhancement 7000.  Do not remove school associations when a student is inactivated.
             */
            //deleteAssociatedSchools(currentPerson);
            
            hm.put("deleted","1");
            int ret = getPersonService().delete(hm);
            if (ret > 0) {
            	addActionMessage("User Inactivated");
            	availableList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.person", ex.getMessage()));
        }
        return SUCCESS;
    }

    public String undelete() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("personPk", getPersonPk());
        hm.put("deleted","0");
        try {
            int ret = getPersonService().delete(hm);
            if (ret > 0) {
            	addActionMessage("User Activated.  Make sure you add roles for this user.");
            	availableList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.person", ex.getMessage()));
            return ERROR;
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

    public Collection<Person> getAvailableList() throws Exception {
    	if (availableList == null) {
    		availableList = new ArrayList<Person>();
    		PersonService ps = new PersonService();
    		Map<String, String> hm = new HashMap<String, String>();

    		if (searchInstructor != null && !"".equals(searchInstructor)) {
    			hm.put("instructor", searchInstructor);
    		}
    		if (searchFirstName != null && !"".equals(searchFirstName)) {
    			hm.put("firstName", searchFirstName.toUpperCase().trim()+'%');
    		}
    		if (searchLastName != null && !"".equals(searchLastName)) {
    			hm.put("lastName", searchLastName.toUpperCase().trim()+'%');
    		}
    		if (searchSchoolName != null && !"".equals(searchSchoolName)) {
    			hm.put("schoolName", searchSchoolName.toUpperCase().trim()+'%');
    		}
    		if (searchInstructorNum != null && !"".equals(searchInstructorNum)) {
    			hm.put("instructorNum", searchInstructorNum.toUpperCase().trim()+'%');
    		}
    		if (activeFlag != null && !"".equals(activeFlag)) {
    			hm.put("deleted", activeFlag);
    		}
    		try {
    			availableList = ps.getPersonBeginsWithList(hm);
    		} catch (DaoException de) {
    			log.error("DAOEXCEPTION",de);
    		}
    	}
    	return availableList;
    }

    public void prepare() throws Exception {
        log.debug("prepare");
        if (getCurrentPerson() != null) {
            log.debug("not null");
        } else {
            Person preFetched = fetch(getPersonPk());
            if (preFetched != null) {
                setCurrentPerson(preFetched);
            }
        }
    }

    public Person fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for " + tryId);
        Person result = null;
        if (tryId != null) {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("personPk", tryId);
            result = getPersonService().getPerson(hm);
            result.setRoles(getPersonRoles(hm));
            result.setAssociatedSchools(getAssociatedSchools(hm));
        }
        log.debug("exit fetch for " + tryId);
        return result;
    }

    public Integer getPersonPk() {
        return personPk;
    }

    public void setPersonPk(Integer personPk) {
        this.personPk = personPk;
    }

    public String getSearchInstructor() {
		return searchInstructor;
	}

	public void setSearchInstructor(String searchInstructor) {
		this.searchInstructor = searchInstructor;
	}

	public String getSearchFirstName() {
		return searchFirstName;
	}

	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}

	public String getSearchLastName() {
		return searchLastName;
	}

	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}

	public String getSearchSchoolName() {
		return searchSchoolName;
	}

	public void setSearchSchoolName(String searchSchoolName) {
		this.searchSchoolName = searchSchoolName;
	}

	public String getSearchInstructorNum() {
		return searchInstructorNum;
	}

	public void setSearchInstructorNum(String searchInstructorNum) {
		this.searchInstructorNum = searchInstructorNum;
	}

	public PersonService getPersonService() {
        if (personService == null) {
            setPersonService(new PersonService(getUmdLogonEmail()));
        }
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        log.debug("setCurrentPerson");
        this.currentPerson = currentPerson;
    }

    private void setPersonRoles(Person currentPerson, Integer personPk) throws DaoException {
        currentPerson.setPersonPk(personPk);
        setPersonRoles(currentPerson);
    }

    private void setAssociatedSchools(Person currentPerson, Integer personPk) throws DaoException {
        currentPerson.setPersonPk(personPk);
        setAssociatedSchools(currentPerson);
    }

    private void setPersonRoles(Person currentPerson) throws DaoException {
        PersonRolesService service = new PersonRolesService(getUmdLogonEmail());

        if (currentPerson.getPersonPk() != null) {	// if person_pk = null, deletePersonRoles will delete all records in person_roles table!!!
        	deletePersonRoles(currentPerson);
        }

        Map<String, Object> hm = new HashMap<String, Object>();
        int length = 0;

        hm.put("personFk", currentPerson.getPersonPk());
        hm.put("email", currentPerson.getEmail());

        if (currentPerson.getRoles() != null) {
            length = currentPerson.getRoles().length;
        }
        for (int i = 0; i < length; i++) {
            hm.put("roleTypesFk", currentPerson.getRoles()[i]);
            service.insert(hm);
        }
    }

    private void setAssociatedSchools(Person currentPerson) throws DaoException {
        PersonSchoolService service = new PersonSchoolService(getUmdLogonEmail());

        deleteAssociatedSchools(currentPerson);

        Map<String, Object> hm = new HashMap<String, Object>();
        int length = 0;
        hm.put("personFk", currentPerson.getPersonPk());
        if (currentPerson.getAssociatedSchools() != null) {
            length = currentPerson.getAssociatedSchools().length;
        }
        for (int i = 0; i < length; i++) {
            hm.put("schoolFk", currentPerson.getAssociatedSchools()[i]);
            service.insert(hm);
        }
    }


    private void deleteAssociatedSchools(Person currentPerson) throws DaoException {
        PersonSchoolService service = new PersonSchoolService(getUmdLogonEmail());
        service.delete(currentPerson);
    }

    private void deletePersonRoles(Person currentPerson) throws DaoException {
        PersonRolesService service = new PersonRolesService(getUmdLogonEmail());
        service.delete(currentPerson);
    }

    private Integer[] getPersonRoles(Map hm) throws DaoException {
        PersonRolesService prs = new PersonRolesService(getUmdLogonEmail());
        Integer[] result = null;
        try {
            result = prs.getPersonRoles(hm);
        } catch (DaoException ex) {
            log.error("ERROR", ex);
        }
        return result;
    }

    private Integer[] getAssociatedSchools(Map hm) {
        PersonSchoolService service = new PersonSchoolService();
        Integer[] result = null;
        try {
            result = service.getPersonSchool(hm);
        } catch (DaoException ex) {
            log.error("ERROR", ex);
        }
        return result;
    }

    @Override
    public String getUpdatedEmail() {
        return super.getUpdatedEmail();
    }

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	public HttpServletRequest getServletRequest() {
		return this.servletRequest;
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
